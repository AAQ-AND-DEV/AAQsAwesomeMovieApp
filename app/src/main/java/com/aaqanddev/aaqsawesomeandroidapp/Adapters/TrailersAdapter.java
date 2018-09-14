package com.aaqanddev.aaqsawesomeandroidapp.Adapters;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder{

    //TODO use liveData here?
    private LiveData<List<AaqMovieTrailer>> mTrailers;

    TrailersRecyclerViewClickListener mTrailerClickListener;

    public TrailersAdapter(){}

    public TrailersAdapter(List<AaqMovieTrailer> trailersList){
        mTrailers = trailersList;
    }
    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        View trailerView = LayoutInflater.from(parentContext).inflate(R.layout.trailers_rv_item, parent, false);
        trailerView.setFocusable(true);
        trailerView.setClickable(true);
        TrailersAdapter.TrailerViewHolder trailerViewHolder = new TrailersAdapter.TrailerViewHolder(trailerView);
        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
   {
       final TrailerItemBinding binding;

       TrailerViewHolder(TrailerItemBinding binding){
           super(binding.getRoot());
           this.binding = binding;
       }

       public void bind(int pos){

       }

       @Override
       public void onClick(View v) {
           mTrailerClickListener.onItemClick(v, this.getLayoutPosition();
       }
   }

   public interface TrailersRecyclerViewClickListener {
        //TODO (q) pass in the target url instead?
       //TODO (r) access via repo? wherever implementing this?
        void onItemClick(final View view, int movieId);
   }

}
