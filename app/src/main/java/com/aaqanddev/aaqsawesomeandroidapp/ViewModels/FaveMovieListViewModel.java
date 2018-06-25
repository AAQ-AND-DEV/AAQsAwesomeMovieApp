package com.aaqanddev.aaqsawesomeandroidapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.aaqanddev.aaqsawesomeandroidapp.Db.FavoriteMovieDb;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;

import java.util.List;

public class FaveMovieListViewModel extends AndroidViewModel {

    private final LiveData<List<AaqMovie>> faveMovieList;

    private FavoriteMovieDb faveDb;

    public FaveMovieListViewModel(@NonNull Application application) {
        super(application);
        faveDb = FavoriteMovieDb.getDb(this.getApplication());
        faveMovieList = faveDb.faveMovieModel().getAllFaveMovies();
    }

    public LiveData<List<AaqMovie>> getFaveMovieList(){
        return faveMovieList;
    }

    public void deleteItem(AaqMovie movie){
        new deleteAsyncTask(faveDb).execute(movie);
    }

    private static class deleteAsyncTask extends AsyncTask<AaqMovie,Void, Void>{

        private FavoriteMovieDb db;

        deleteAsyncTask(FavoriteMovieDb appDb){
            db = appDb;
        }

        @Override
        protected Void doInBackground(final AaqMovie... aaqMovies) {
            db.faveMovieModel().deleteMovie(aaqMovies[0]);
            return null;
        }
    }

}
