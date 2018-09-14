package com.aaqanddev.aaqsawesomeandroidapp.repos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.aaqanddev.aaqsawesomeandroidapp.AaqMovieApp;
import com.aaqanddev.aaqsawesomeandroidapp.AaqMovieAppExecutors;
import com.aaqanddev.aaqsawesomeandroidapp.Db.FavoriteMovieDb;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.FavoriteMoviesDao;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.MovieApiInterface;
import com.aaqanddev.aaqsawesomeandroidapp.MoviesActivity;
import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.TestingRetroActivity;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.SecretApiConstant;
import com.aaqanddev.aaqsawesomeandroidapp.ViewModels.DetailMovieViewModel;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieReview;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailerList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//source: looking at this arch ex https://developer.android.com/jetpack/docs/guide#addendum
public class AaqMovieRepo {

    private static AaqMovieRepo sInstance;

    private MovieApiInterface movieApiService;
    private AaqMovieAppExecutors mExecutors;
    private final FavoriteMovieDb mFaveDb;
    private final FavoriteMoviesDao mFaveMovieDao;

    //mediator for AllFaves (for use with the MoviesActivity call to Faves)
    private MediatorLiveData<AaqMovieList> mAllObservableFaveMovies;

    //Are these NOT a good idea as private vars?
    //mediator for detailMovie (when to set?), I think this can only be
    //done upon request
    //is there another way to do that?
    private MediatorLiveData<AaqMovie> mDetailMovie;
    //mediator for Trailers and Reviews
    private MediatorLiveData<List<AaqMovieTrailer>> mTrailersList;
    private MediatorLiveData<List<AaqMovieReview>> mReviewsList;

    private AaqMovieRepo(Context context, AaqMovieAppExecutors executors){
        //this used to accept the Db -- since been removed to class level
        //mFaveMovieDb = faveDb;
        //setting up Retro
        //do I need to close Retrofit somewhere?
        mExecutors = executors;
        //set up Db
        mFaveDb = FavoriteMovieDb.getDb(context, executors);
        mFaveMovieDao = mFaveDb.faveMovieDao();
        //Set up Retro Service
        movieApiService = MoviesAPIClient.getClientBuilder().build().create(MovieApiInterface.class);
        instantiateMediators();


        //how can I access the current detailMovie?
        //it's got to be passed in (for sure, right? DTMS?
        mAllObservableFaveMovies.addSource(mFaveMovieDb.faveMovieDao().getAllFaveMovies(),
                faveMovieEntities -> {
                    if (mFaveMovieDb.getDbCreated().getValue() != null){
                        //if Db exists, post the list to this MediatorLiveData
                        mAllObservableFaveMovies.postValue(faveMovieEntities);
                    }
                });


        //TODO (i) make declareObservers() helper method
       //I guess this is an advantage of just one viewModel
        mDetailMovie.addSource(movieApiService.doGetMovie(movieId), );
    }


    public static AaqMovieRepo getInstance(Context app, AaqMovieAppExecutors executors) {
       if (sInstance == null){
           synchronized (AaqMovieRepo.class){
               if (sInstance == null){
                   sInstance = new AaqMovieRepo(app, faveDb, executors);
               }
           }
       }
       return sInstance;
    }
    private void instantiateMediators(){
        mDetailMovie = new MediatorLiveData<>();
        mAllObservableFaveMovies = new MediatorLiveData<>();
        mTrailersList = new MediatorLiveData<>();
        mReviewsList = new MediatorLiveData<>();
    }

    public  MutableLiveData<AaqMovie> getDetailMovie(int id) {


        mDetailMovie.observe(this, movie ->  );
        //done created an anonymous Observer class with a override onChanged()
        mDetailMovie.addSource(movieApiService.doGetMovie(id), res -> {
            res.removeSource
        })
        return mDetailMovie;
        /*
         */
    }
    /*
    public static AaqMovieRepo getInstance(final FavoriteMovieDb faveDb, final int movieId ){
        if (sDetailInstance == null){
            //this may cause a problem?
            //DTMS? I am synchornizing this class in two methods, that's ok, right?
            synchronized (AaqMovieRepo.class){
                if (sDetailInstance == null){
                    sDetailInstance = new AaqMovieRepo(faveDb, movieId);
                }
            }
        }
        return sDetailInstance;
    }
    */
    public MutableLiveData<AaqMovieList> getAllFaveMovies() {
        return mAllObservableFaveMovies;
    }

    //done grab the Fave AaqMovie from the faveDB by movieId --
    public MutableLiveData<AaqMovie> getFaveMovieById(int movieId){
        return  mFaveMovieDb.faveMovieDao().getItemById(movieId);
    }

    public MutableLiveData<Boolean> getIsFaveMovie(int movieId){
        return mFaveMovieDb.faveMovieDao().isFaveMovie(movieId);
    }
    //so what will I do instead?
    //should I try to access the bundle via the repo?
    //would require passing context, prbly --
    //DTMS? ahhh...i could pass in the bundle to the getDetailMovie Repo method?
    //idk but it's actually grabbed from the Intent (via parcelable)
//
//    public LiveData<AaqMovie> getDetailMovie(Bundle bundle){
//
//    }



    public void insertMovie(AaqMovie movie) {
        new insertMovieTask(mFaveMovieDb.faveMovieDao()).execute(movie);
    }

    //TODO (u) run on executor -- or is that done above?
    private static class insertMovieTask extends AsyncTask<AaqMovie, Void,Void>{
        private FavoriteMoviesDao mAsyncDao;

        insertMovieTask(FavoriteMoviesDao dao){ mAsyncDao = dao;}

        @Override
        protected Void doInBackground(final AaqMovie ... movies){
            mAsyncDao.addFaveMovie(movies[0]);
            return null;
        }
    }
    //TODO add trailers/reviews fetch stuff too?
    //can we put the
    //get context? for the Toast msg?
    //IDK any other reason to grab context?
    public LiveData<AaqMovieTrailerList> getMovieTrailers(int id){
        //TODO (U) incorporate Executors!
        mTrailersList.observe();

                movieApiService.
                doGetMovieTrailers(id);
        trailersCall.enqueue(new Callback<AaqMovieTrailerList>() {
            @Override
            public void onResponse(Call<AaqMovieTrailerList> call, Response<AaqMovieTrailerList> response) {
                List<AaqMovieTrailer> currTrailers = new ArrayList<>();
                if (response.isSuccessful()){
                    currTrailers = response.body().getMovieTrailers();
                    //TODO i think i made a mistake here (?need explicit conversion to List<T>?
                    if (currTrailers != null){
                        //TODO to convert Retro response to a LiveDataObject
                        mTrailersList.addSource();
                        //this was testing stuff (from elsewhere)
                        //sampleTrailers.append(jsonReturned.trailersToString(response.body().getMovieTrailers()));
                        //testTrailers.setText(sampleTrailers);
                    }
                }
            }

            @Override
            public void onFailure(Call<AaqMovieTrailerList> call, Throwable t) {
                Toast.makeText(TestingRetroActivity.this, "error: cancelling call", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

}

        /* TODO (?) maybe this is needed elsewhere? in detailActivity?
        final MutableLiveData<List<AaqMovie>> data = new MutableLiveData<>();
        //TODO here need to pass in parameters --
        movieApiService.doGetMovieList()
        Call<AaqMovieList> movieListCall = movieApiInterface
                .doGetMovieList(getResources().getStringArray(R.array.pref_fetch_by_vals)[sortSpinner.getSelectedItemPosition()]
                        , SecretApiConstant.movieApiConstant,
                        getResources().getString(R.string.lang_default), getResources().getString(R.string.page_default));
        movieListCall.enqueue(new Callback<AaqMovieList>(){

            @Override
            public void onResponse(Call<AaqMovieList> call, Response<AaqMovieList> response) {
                ArrayList<AaqMovie> currList = new ArrayList<AaqMovie>();
                if (response.isSuccessful()){
                    AaqMovieList jsonReturned  = response.body();

                    //System.out.println("json returned: " + jsonReturned.toString());
                    //int pgs = response.body().getPage();
                    //System.out.println("no. pgs: " + pgs );
                    if (jsonReturned != null){
                        mAdapter.updateData(response.body().getMovies());
                    }
                }
            }

            @Override
            public void onFailure(Call<AaqMovieList> call, Throwable t) {
                Toast.makeText(MoviesActivity.this, "error: cancelling call", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        } );
    */

