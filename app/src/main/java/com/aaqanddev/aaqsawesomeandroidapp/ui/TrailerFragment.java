package com.aaqanddev.aaqsawesomeandroidapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.VideoFetchIntentHelper;
import com.aaqanddev.aaqsawesomeandroidapp.ViewModels.DetailMovieViewModel;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;

import java.util.List;

//TODO implement  parcelable for Trailer and Reviews?
//so it can be passed here?
//implement View.onClickListener?
//TODO (immed. rf) put the observation of viewModels and vars here:

public class TrailerFragment extends Fragment{

    TrailersAdapter adapter;
    RecyclerView trailersRV;
    //TODO  I should have this LiveData (I believe)
    //OR not here at all, so it's not holding data?
    List<AaqMovieTrailer> mTrailers;
    DetailMovieViewModel mDetailModel;

    public TrailerFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Do I need to explicitly       //deploy the mMainThread
        //for View stuff?  I'm going to go with no...and assume Android runs fragments always main.
        //actually, I think this will be the binding

        View rootView = inflater.inflate(R.layout.fragment_trailers, container, false);
        //get RecyclerView
        RecyclerView trailers_rv = (RecyclerView) rootView.findViewById(R.id.trailers_r_v);
        //i dont think this stuff should be in here?
        mDetailModel = ViewModelProviders.of(getActivity()).get(DetailMovieViewModel.class);
        mDetailModel.getmObservableTrailersList().observe(this, new Observer<List<AaqMovieTrailer>>() {
            @Override
            public void onChanged(@Nullable List<AaqMovieTrailer> aaqMovieTrailers) {
                if (aaqMovieTrailers != null){
                    //instantiate a new Adapter and all
                    adapter = new TrailersAdapter(aaqMovieTrailers);
                    //Should I actually be making a swapAdapter() method?
                    //
                    adapter.setmTrailerClickListener(new TrailersAdapter.TrailersRVClickListener() {
                        @Override
                        public void onItemClick(View view, int pos) {
                            //TODO (finally so close...)
                            //create intent to launch Url in video player
                            YtVidFetchIntentHelperImpl ytLaunchHelper = new YtVidFetchIntentHelperImpl();
                            ytLaunchHelper.watchVideo(view.getContext(), pos);
                        }
                    });
                    trailers_rv.setAdapter(adapter);
                    //idk this will enact new View creation, right?
                    adapter.notifyDataSetChanged();
                }

            }
        });
        return rootView;
        //TODO probly grab the Bundle for accessing trailer info
        //TODO (u) but this should be done DTMS? via repository,right?
        //idk set Adapter check Rakesh and ryan's repos to see if fragments should have globals
    }
    private class YtVidFetchIntentHelperImpl implements VideoFetchIntentHelper{

        @Override
        public void watchVideo(Context context, int pos) {
            Intent appYtIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+ getKeyFromPos(pos)));
            Intent webYtIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=" + getKeyFromPos(pos)));
            try {
                context.startActivity(appYtIntent);
            }
            catch(ActivityNotFoundException ex){
                context.startActivity(webYtIntent);
            }

        }

        private String getKeyFromPos(int pos){

            String ytUrlKey = (mTrailers.get(pos)).getKey();

            return ytUrlKey;
        }
    }


}
