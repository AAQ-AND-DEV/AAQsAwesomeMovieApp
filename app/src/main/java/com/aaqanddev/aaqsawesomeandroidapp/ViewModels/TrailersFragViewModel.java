package com.aaqanddev.aaqsawesomeandroidapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.aaqanddev.aaqsawesomeandroidapp.AaqMovieApp;
import com.aaqanddev.aaqsawesomeandroidapp.Adapters.TrailersAdapter;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;
import com.aaqanddev.aaqsawesomeandroidapp.repos.AaqMovieRepo;

import java.util.List;

public class TrailersFragViewModel extends AndroidViewModel {

    //must be Application context!
    private final Context mContext;

    public TrailersFragViewModel(
            @NonNull Application application,
            AaqMovieRepo repo) {

        super(application);

    }
    public final LiveData<Boolean> trailersLoading =
            new MutableLiveData<Boolean>(){
                @Override
                public void postValue(Boolean value) {
                    //default instantiation
                    super.postValue(false);
                }
            };
    //idk is there really any reason to use LiveData here?
    //Observable is probably enough...?
    //I also wonder whether setValue may be more appropriate
    //it would preserve the data on orientation change, yes?
    //but...
    public final LiveData<Boolean> trailersEmpty =
            new MutableLiveData<Boolean>(){
                @Override
                public void postValue(Boolean value) {
                    super.postValue(false);
                }
            };
    private MutableLiveData<List<AaqMovieTrailer>> trailers;
    //have a repo?

    private AaqMovieRepo repo;
    //or the Retro service?
    private MoviesAPIClient retro;
    private TrailersAdapter mAdapter;



    public final


    public LiveData<List<AaqMovieTrailer>> getTrailers(int movieId){
        if (trailers == null){
            trailers = new MutableLiveData<List<AaqMovieTrailer>>();
            loadTrailers(movieId);
        }
        return trailers;
    }

    public void loadTrailers(int movieId){
        //forget about using a repo for this?
        //getting non-static context error
        //repo = AaqMovieApp.getRepository();
        repo = new AaqMovieRepo();

    }
}
