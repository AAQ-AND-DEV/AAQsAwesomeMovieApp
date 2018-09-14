package com.aaqanddev.aaqsawesomeandroidapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaqanddev.aaqsawesomeandroidapp.Adapters.TrailersAdapter;
import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.ViewModels.DetailMovieViewModel;
import com.aaqanddev.aaqsawesomeandroidapp.ViewModels.TrailersFragViewModel;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;

import java.util.List;

//TODO implement  parcelable for Trailer and Reviews?
//so it can be passed here?
//implement View.onClickListener?
//TODO (immed. rf) put the observation of viewModels and vars here:

public class TrailerFragment extends Fragment implements View.OnClickListener{

    TrailersAdapter adapter;
    RecyclerView trailersRV;
    //TODO  I should have this LiveData (I believe)
    List<AaqMovieTrailer> mTrailers;
    DetailMovieViewModel mDetailModel;

    public TrailerFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Do I need to explicitly
        //deploy the mMainThread
        //for View stuff?
        View rootView = inflater.inflate(R.layout.fragment_trailers_rv, container, false);
        //idk should I grab the scrollView?  I don't think so...
        //get RecyclerView
        RecyclerView trailers_rv = (RecyclerView) rootView.findViewById(R.id.trailers_r_v);
        mDetailModel = ViewModelProviders.of(getActivity()).get(DetailMovieViewModel.class);
        mDetailModel.getmObservableTrailersList().observe(this, new Observer<List<AaqMovieTrailer>>() {
            @Override
            public void onChanged(@Nullable List<AaqMovieTrailer> aaqMovieTrailers) {
                //let the RV know its been updated?

            }
        });


        //TODO probly grab the Bundle for accessing trailer info
        //TODO (u) but this should be done DTMS? via repository,right?



        //idk set Adapter check Rakesh and ryan's repos to see if fragments should have globals



    }

    @Override
    public void onClick(View v) {

    }
}
