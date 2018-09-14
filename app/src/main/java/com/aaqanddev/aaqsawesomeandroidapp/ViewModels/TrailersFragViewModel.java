package com.aaqanddev.aaqsawesomeandroidapp.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aaqanddev.aaqsawesomeandroidapp.Adapters.TrailersAdapter;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;
import com.aaqanddev.aaqsawesomeandroidapp.repos.AaqMovieRepo;

import java.util.List;

public class TrailersFragViewModel extends ViewModel {

    //idk is there really any reason to use LiveData here?
    //it would preserve the data on orientation change, yes?
    private MutableLiveData<List<AaqMovieTrailer>> trailers;
    //have a repo?
    private AaqMovieRepo repo;
    //or the Retro service?
    private MoviesAPIClient retro;
    private TrailersAdapter mAdapter;

    public LiveData<List<AaqMovieTrailer>> getTrailers(){
        if (trailers == null){
            trailers = new MutableLiveData<List<AaqMovieTrailer>>();
            loadTrailers;
        }

        public void loadTrailers(){
            AaqMovieRepo repo =
        }
    }
}
