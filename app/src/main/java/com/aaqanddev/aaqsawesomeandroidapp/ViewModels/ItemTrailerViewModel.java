package com.aaqanddev.aaqsawesomeandroidapp.ViewModels;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Parcelable;

import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;

public class ItemTrailerViewModel extends ViewModel implements Parcelable{

    private AaqMovieTrailer mTrailer;

    private static final String TRAILER_IMG_BASE_URL = "https:/img.youtube.com/vi/";
    private static final String TRAILER_IMG_TYPE = "/hqdefault.jpg";
    private static final String YT_URL = "http://www.youtube.com/watch?v=";

    public ItemTrailerViewModel(AaqMovieTrailer trailer){
        this.mTrailer = trailer;
    }



    ItemTrailerViewModel createViewModel(){
        //TODO (should be adding a factory?
        //TODO  (context not present here)
        //idk extend AndroidViewModel?
        //i think this should be in another
        //this needs to be a fragment
        //so I call it from there?
        return ViewModelProviders.of(this).get(ItemTrailerViewModel.class);
    }
}
