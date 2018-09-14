package com.aaqanddev.aaqsawesomeandroidapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.aaqanddev.aaqsawesomeandroidapp.Db.FavoriteMovieDb;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;

import java.util.List;

public class FaveMovieListViewModel extends AndroidViewModel {

    private final MutableLiveData<AaqMovieList> faveMovieList  = new MutableLiveData<>();

    private FavoriteMovieDb faveDb;

    public FaveMovieListViewModel(@NonNull Application application) {
        super(application);
        faveDb = FavoriteMovieDb.getDb(application);
        //done setValue  on MutableLiveData
        //TODO(Q) did postValue instead -- since I think this
        //will end up on background  thread
        faveMovieList.postValue();faveDb.faveMovieDao().getAllFaveMovies();
    }

    public LiveData<AaqMovieList> getFaveMovieList(){
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
            db.faveMovieDao().deleteMovie(aaqMovies[0]);
            return null;
        }
    }

}
