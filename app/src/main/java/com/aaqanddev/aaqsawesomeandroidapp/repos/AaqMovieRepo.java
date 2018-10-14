package com.aaqanddev.aaqsawesomeandroidapp.repos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.aaqanddev.aaqsawesomeandroidapp.AaqMovieAppExecutors;
import com.aaqanddev.aaqsawesomeandroidapp.Db.FavoriteMovieDb;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.FavoriteMoviesDao;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.MovieApiInterface;
import testing.TestingRetroActivity;

import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.ConnectionCheckTask;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieReview;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailerList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqReviewsList;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//source: looking at this arch ex https://developer.android.com/jetpack/docs/guide#addendum
public class AaqMovieRepo {

    private static AaqMovieRepo sInstance;

    private MovieApiInterface movieApiService;
    private AaqMovieAppExecutors mExecutors;
    private final FavoriteMovieDb mFaveDb;
    private final FavoriteMoviesDao mFaveMovieDao;

    private LiveData<Boolean> mHasInternet;

    //mediator for AllFaves (for use with the MoviesActivity call to Faves)
    private static MediatorLiveData<AaqMovieList> mAllObservableFaveMovies;

    private MediatorLiveData<Boolean> mIsFave;
    //mediator for DetailMovie
    private MediatorLiveData<AaqMovie> mDetailMovie;
    //mediator for Trailers and Reviews
    private MediatorLiveData<List<AaqMovieTrailer>> mTrailersList;
    private MediatorLiveData<List<AaqMovieReview>> mReviewsList;

    //idk if i use a membrVar for access, I don't think so...
    private LiveData<List<AaqMovieReview>> mReviewsOutList = new LiveData<List<AaqMovieReview>>() {
    };
    private Context mContext;

    private int mMovieId;

    //I think I need a bunch of Observers to enact MediatorLiveData correctly.
    //just leaving it without Mediating...

    private void instantiateMediators() {
        mDetailMovie = new MediatorLiveData<>();
        mIsFave = new MediatorLiveData<>();
        mAllObservableFaveMovies = new MediatorLiveData<>();
        //could use Transformations here instead of in ViewModel?
        mTrailersList = new MediatorLiveData<>();
        mReviewsList = new MediatorLiveData<>();
    }


    //I  believe this context will end up being the Application context (as passed thru VM)
    private AaqMovieRepo(Context context, AaqMovieAppExecutors executors) {
        mContext  = context;
        mExecutors = executors;
        //set up Db
        mFaveDb = FavoriteMovieDb.getDb(context, executors);
        mFaveMovieDao = mFaveDb.faveMovieDao();
        //Set up Retro Service
        movieApiService = MoviesAPIClient.getClientBuilder().build().create(MovieApiInterface.class);

        instantiateMediators();

        mAllObservableFaveMovies.addSource(mFaveDb.faveMovieDao().getAllFaveMovies(),
                faveMovieEntities -> {
                    if (mFaveDb.getDbCreated().getValue() != null) {
                        //if Db exists, post the list to this MediatorLiveData
                        mAllObservableFaveMovies.postValue(faveMovieEntities);
                    }
                });

    }

    public static AaqMovieRepo getInstance(Context app, AaqMovieAppExecutors executors) {
        if (sInstance == null) {
            synchronized (AaqMovieRepo.class) {
                if (sInstance == null) {
                    sInstance = new AaqMovieRepo(app, executors);
                }
            }
        }
        return sInstance;
    }

    public MutableLiveData<AaqMovie> getDetailMovie(int movieId) {
        mMovieId = movieId;

        movieApiService.doGetMovie(movieId).enqueue(new Callback<AaqMovie>() {
            @Override
            public void onResponse(Call<AaqMovie> call, Response<AaqMovie> response) {
                if (response.isSuccessful()) {
                    mDetailMovie.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AaqMovie> call, Throwable t) {
                mDetailMovie.setValue(null);
            }
        });
        //TODO (u) get trailers and reviews here? (add approp vars to model

        return mDetailMovie;
    }
/*
    public MutableLiveData<AaqMovieList> getAllFaveMovies() {
        return (MutableLiveData<AaqMovieList>) mFaveDb.faveMovieDao().getAllFaveMovies();
    }

    //get FaveMovie by Id
    public MutableLiveData<AaqMovie> getFaveMovieById(int movieId) {
        return (MutableLiveData<AaqMovie>) mFaveDb.faveMovieDao().getItemById(movieId);
    }
*/
    //get isFaveMovie result
    public MutableLiveData<Boolean> getIsFaveMovie(int movieId) {
        return (MutableLiveData<Boolean>) mFaveDb.faveMovieDao()
                .isFaveMovie(movieId);
    }

    public LiveData<List<AaqMovieTrailer>> getMovieTrailers(int id) {

        movieApiService.
                doGetMovieTrailers(id).getValue().enqueue(new Callback<AaqMovieTrailerList>() {
            @Override
            public void onResponse(Call<AaqMovieTrailerList> call, Response<AaqMovieTrailerList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        mTrailersList.setValue(response.body().getMovieTrailers().getValue());

                }
            }

            @Override
            public void onFailure(Call<AaqMovieTrailerList> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
        return mTrailersList;
    }

    public LiveData<List<AaqMovieReview>> getMovieReviews(int id) {
        new ConnectionCheckTask(new ConnectionCheckTask.Consumer() {
            @Override
            public void accept(Boolean internet) throws ConnectException {
                if (internet) {
                    movieApiService.
                            doGetReviewsList(id).getValue().enqueue(new Callback<AaqReviewsList>() {
                        @Override
                        public void onResponse(Call<AaqReviewsList> call, Response<AaqReviewsList> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    mReviewsList.setValue(response.body().getReviewListResults().getValue());

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AaqReviewsList> call, Throwable t) {
                            t.printStackTrace();
                            call.cancel();
                        }
                    });

                } else {
                    Toast.makeText(mContext,
                            mContext.getResources().getString(R.string.error_retry_button_label),
                            Toast.LENGTH_LONG);
                }
                throw new ConnectException("unable to connect to Socket");
            }
        });
        return mReviewsList;

    }
    public void deleteMovie(AaqMovie movie){
        new deleteMovieTask(mFaveDb.faveMovieDao()).execute(movie);
    }

    public void insertMovie(AaqMovie movie) {
        new insertMovieTask(mFaveDb.faveMovieDao()).execute(movie);
    }

    //done run on executor via instantiation, i think DTMS?
    private static class insertMovieTask extends AsyncTask<AaqMovie, Void, Void> {
        private FavoriteMoviesDao mAsyncDao;

        insertMovieTask(FavoriteMoviesDao dao) {
            mAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final AaqMovie... movies) {
            mAsyncDao.addFaveMovie(movies[0]);
            return null;
        }
    }

    private static class  deleteMovieTask extends AsyncTask<AaqMovie, Void, Void> {
        private FavoriteMoviesDao mAsyncDao;

        deleteMovieTask(FavoriteMoviesDao dao){
            mAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(AaqMovie... aaqMovies) {
            mAsyncDao.deleteMovie(aaqMovies[0]);
            return null;
        }
    }
}

