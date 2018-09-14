package com.aaqanddev.aaqsawesomeandroidapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.aaqanddev.aaqsawesomeandroidapp.AaqMovieApp;
import com.aaqanddev.aaqsawesomeandroidapp.AaqMovieAppExecutors;
import com.aaqanddev.aaqsawesomeandroidapp.Db.FavoriteMovieDb;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.FavoriteMoviesDao;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieReview;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;
import com.aaqanddev.aaqsawesomeandroidapp.repos.AaqMovieRepo;

import java.util.List;
import java.util.concurrent.Executor;

public class DetailMovieViewModel extends AndroidViewModel {


    private MutableLiveData<Boolean> mIsFave;
    private MutableLiveData<AaqMovie> mObservableMovie;
    //I'm not sure what this movie field is doing anymore
    //public ObservableField<AaqMovie> movie = new ObservableField<>();
    private int mMovieId;

    private AaqMovieRepo mRepo;

    private MutableLiveData<List<AaqMovieTrailer>> mObservableTrailersList;
    private MutableLiveData<List<AaqMovieReview>> mObservableReviewsList;

    //DTMS? using Application, with this default ctor?
    //should this actually be AaqMovieApp instead of Application?
    //TODO (Q)  ok, so I think I want to make methods
    //that contain calls pertaining to movieID
    //so removing that per Randy's advice (I think)
    //TODO (Q) could add ViewModel that accepts a bundle?
    //see some1 passed the Repo into the ViewModel?
    public DetailMovieViewModel(@NonNull AaqMovieApp app, AaqMovieAppExecutors executors){
        super(app);

        //done get db dao instance
        this.mFaveMovieDao = FavoriteMovieDb.getDb(app, executors).faveMovieDao();

        //per Randy, Repo should not accept this value(movieID)!
        //should it not also accept the bundle?
        //done Repo instantiation here
        this.mRepo = AaqMovieRepo.getInstance(app, faveDb);

        this.mObservableMovie = new MutableLiveData<>();
        this.mObservableTrailersList = new MutableLiveData<>();
        this.mObservableReviewsList = new MutableLiveData<>();
    }

    public LiveData<AaqMovie> getObservableMovie(){
        //ah...but i have already instantiated it
        //how will I tell if it's empty?
        if (mObservableMovie == null){
            mObservableMovie = new MutableLiveData<AaqMovie>();
            loadMovie();
        }

        return movie;
    }

    public LiveData<List<AaqMovieReview>> getmObservableReviewsList() {

        return mObservableReviewsList;
    }

    public LiveData<List<AaqMovieTrailer>> getmObservableTrailersList() {
        return mObservableTrailersList;
    }

    public LiveData<Boolean> getIsFave(){
        return isFave;
    }

    private void loadMovie(int movieId){
        //TODO loadMovie how? I should be able to access
        //the moviesList via AaqMovieRepo?
        faveDb.
        //check if fave (should this be done in constructor? NO!

        mRepo = AaqMovieRepo.getInstance(faveDb);
        //I need to run this off main thread
        //enable executors --
        Executor executor = mExecutors.getmNetworkIO();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mObservableMovie.postValue(mRepo.getDetailMovie(movieId));
            }
        });

    }

    private void loadTrailers(){

    }
    //TODO relocate to fragment/activity
    private void subscribeToModel(final DetailMovieViewModel viewModel){
        viewModel.get
    }
}
