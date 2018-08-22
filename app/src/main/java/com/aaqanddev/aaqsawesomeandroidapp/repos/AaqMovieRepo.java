package com.aaqanddev.aaqsawesomeandroidapp.repos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import com.aaqanddev.aaqsawesomeandroidapp.Db.FavoriteMovieDb;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.FavoriteMoviesDao;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.MovieApiInterface;
import com.aaqanddev.aaqsawesomeandroidapp.MoviesActivity;
import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.SecretApiConstant;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AaqMovieRepo {
    //private MovieApiInterface movieApiService;

    private FavoriteMoviesDao mFaveDao;
    private MutableLiveData<AaqMovieList> mAllFaveMovies;

    public AaqMovieRepo(Application application) {
        FavoriteMovieDb db = FavoriteMovieDb.getDb(application);
        mFaveDao = db.faveMovieDao();
        mAllFaveMovies = mFaveDao.getAllFaveMovies();
    }


    //TODO (Q) would i ideally be able to get my detail Movie
    //via this repo -- thereby allowing the
    public MutableLiveData<AaqMovieList> getAllFaveMovies() {
        return mAllFaveMovies;
    }

    //IDK if this makes sense
    //TODO (F) actually this is only going to grab the AaqMovie
    //from the faveDB --
    //so what will I do instead?
    public  LiveData<AaqMovie> getDetailMovie(int id){
        //TODO (Do I want to make a retroCall from here?)
        //TODO (or set up a Db with all movies)
        //return mFaveDao.getItemById(id);
    }

    public void insert(AaqMovie movie) { new insertMovieTask(mFaveDao).execute(movie);}

    private static class insertMovieTask extends AsyncTask<AaqMovie, Void,Void>{
        private FavoriteMoviesDao mAsyncDao;

        insertMovieTask(FavoriteMoviesDao dao){ mAsyncDao = dao;}

        @Override
        protected Void doInBackground(final AaqMovie ... movies){
            mAsyncDao.addFaveMovie(movies[0]);
            return null;
        }
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

