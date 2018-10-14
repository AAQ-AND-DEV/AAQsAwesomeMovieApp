package com.aaqanddev.aaqsawesomeandroidapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
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
import java.util.Objects;
import java.util.concurrent.Executor;

public class DetailMovieViewModel extends AndroidViewModel {

    //TODO (u) incorporate a LiveData<List<int>> with ids stored, to check
    //faveStatus instead of db  check?
    private final LiveData<Boolean> mIsFave;
    public  ObservableField<Boolean> isFave = new ObservableField<>();

    //
    private final LiveData<AaqMovie> mObservableMovie;
    public ObservableField<AaqMovie> movie = new ObservableField<>();
    //is this an ok value (default seems to be random, possibly  3-digit)
    //seen other examples use zero
    private static final int NOT_SET_CONST = -2;
    //is there a point to having this, if I have the ObservableMovie?
    //contemplating setting a default value of -1
    //to connote set up but not altered
    private final MutableLiveData<Integer> mMovieId;

    //should this be in repo?  I think so... or it should make a call to repo to do so...
    public void setMovieId(Integer movieId) {
        //here avoiding unecessary trigger of observers
        if (Objects.equals(this.mMovieId.getValue(), movieId)){
            return;
        }
        this.mMovieId.setValue(movieId);
    }

    //i think this observer approach mirrors what is done in detailActivity
    //only applied to a Retrofit
    //with
    public ObservableField<Integer> movieId = new ObservableField<>();


    private AaqMovieRepo mRepo;
    private FavoriteMoviesDao mFaveMovieDao;

    private final LiveData<List<AaqMovieTrailer>> mObservableTrailersList;
    private final LiveData<List<AaqMovieReview>> mObservableReviewsList;

    //calls pertaining Movie, Trailers, Review,
    //should be triggered by delta to movieID
    private ObservableInt progressBarVisibility;
    private ObservableInt detailsVisibity;

    public DetailMovieViewModel(@NonNull AaqMovieApp app, AaqMovieRepo repo) {
        super(app);

        //done get db dao instance
        this.mFaveMovieDao = FavoriteMovieDb.getDb(app, app.getAppExecutors()).faveMovieDao();

        //per Randy, Repo should not accept this value(movieID)!
        //should it not also accept the bundle?
        //done Repo assignment here
        this.mRepo = repo;
        //at some point, should I have a viewModelGenerator
        //that holds a List<MovieViewModels>?

        this.mMovieId = new MutableLiveData<>();
        //NB: others would have this done in the GetMovie() method (possibly in Repo?)
        //done Mapping isFave, TrailersList, ReviewsList to id
        this.mIsFave = Transformations.switchMap(mMovieId, id ->
            mRepo.getIsFaveMovie(id));
        //need to figure out how to observe this from frag...(i think i already do)
        //linking changes to movieId to the TrailersList value in ViewModel
        this.mObservableTrailersList = Transformations.switchMap(mMovieId, id ->
                mRepo.getMovieTrailers(id));
        //linking changes to movieId to the Reviews value in ViewModel
        this.mObservableReviewsList = Transformations.switchMap(mMovieId, id ->
                mRepo.getMovieReviews(id));
        //linking changes to movieId to the TrailersList value in ViewModel
        this.mObservableMovie = Transformations.switchMap(mMovieId, movieId -> {
            if (movieId <= 0) {
                return new MutableLiveData<AaqMovie>() {
                    @Override
                    public void setValue(AaqMovie value) {
                        super.setValue(null);
                    }
                };
            } else {
                return mRepo.getDetailMovie(movieId);
             }
        });
    }

    public LiveData<AaqMovie> getObservableMovie(int id){

        //null check --
        if (mObservableMovie == null){
            //make a repo call
            return mRepo.getDetailMovie(id);
        }
        //otherwise, return movie
        return mObservableMovie;
    }

    //DTMS? using the mMovieId LiveData for the ViewModel (where can I set this?)
    //first call to repo would be  getObservableMovie, so
    //should set there I think...
    public LiveData<List<AaqMovieReview>> getmObservableReviewsList() {
        if (mObservableReviewsList == null && mMovieId != null)
            if (mMovieId.getValue() > 0)
            return mRepo.getMovieReviews(mMovieId.getValue());

        return mObservableReviewsList;
    }

    //hmmm...but what about if it is not null,
    // but it is not set to the current Id?
    //will be interesting to see what happens!
    public LiveData<List<AaqMovieTrailer>> getmObservableTrailersList() {
        if (mObservableTrailersList== null)
            if (mMovieId.getValue() > 0)
                return mRepo.getMovieTrailers(mMovieId.getValue());
        return mObservableTrailersList;
    }

    public LiveData<Boolean> getIsFave(){
        //I really hope that switchMap() method works to populate this reliably
        if (mIsFave == null)
            if (mMovieId.getValue() > 0)
                return mRepo.getIsFaveMovie(mMovieId.getValue());
        return mIsFave;
    }

    //I think what I planned on doing here
    //i'm actually doing in onClick from
    //moviesActivity RV
    /*
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
*/
    private void loadTrailers(){

    }

}
