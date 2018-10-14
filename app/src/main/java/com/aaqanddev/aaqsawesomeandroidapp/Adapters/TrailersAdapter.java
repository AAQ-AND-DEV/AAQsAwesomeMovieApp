package com.aaqanddev.aaqsawesomeandroidapp.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.databinding.TrailersRvItemBinding;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder>{

    private List<AaqMovieTrailer> mTrailers;
    private TrailersRvItemBinding binding;

    TrailersRVClickListener mTrailerClickListener;

    public TrailersAdapter(){}

    public TrailersAdapter(List<AaqMovieTrailer> trailersList){
        mTrailers = trailersList;
    }

    //should some of this stuff be liveData?
    public List<AaqMovieTrailer> getmTrailers() {
        return mTrailers;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.trailers_rv_item,
                parent, false);

        //trailerView.setFocusable(true);
        //trailerView.setClickable(true);
        return new TrailerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, final int pos) {
        //I reach into Repo and grab data?
        //no...this member variable should suffice
        if (mTrailers != null){
            AaqMovieTrailer trailer = mTrailers.get(pos);
            holder.binding.setTrailer(trailer);
            //DTMS?
            holder.binding.executePendingBindings();

        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        if (mTrailers != null){
            return mTrailers.size();
        }
            return 0;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder
   {
       private TrailersRvItemBinding binding;

       TrailerViewHolder(TrailersRvItemBinding binding){
           super(binding.getRoot());
           this.binding = binding;
       }

       public void bind(int pos){
           binding.setTrailer(mTrailers.get(pos));
           binding.trailerItemButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (mTrailerClickListener != null) {
                       mTrailerClickListener.onItemClick(v, pos);
                   }
               }
           });
       }


   }
   public void setmTrailerClickListener(TrailersRVClickListener listener){
        mTrailerClickListener = listener;
   }

   public interface TrailersRVClickListener {
        //TODO (q) pass in the target url instead?
       //TODO (r) access via repo? wherever implementing this?
        public void onItemClick(final View view, int pos);
   }

}
