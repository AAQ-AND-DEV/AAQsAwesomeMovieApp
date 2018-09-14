package com.aaqanddev.aaqsawesomeandroidapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieReview;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>{
    ReviewRecyclerViewListener mListener;
    //TODO convert this to LIVEDATA
    List<AaqMovieReview> mReviews;

    public ReviewsAdapter(List<AaqMovieReview> reviews, ReviewRecyclerViewListener reviewClickListener){
        mListener = reviewClickListener;
        mReviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        View reviewView = LayoutInflater.from(parentContext).inflate(R.layout.reviews_rv_item, parent, false);
        reviewView.setFocusable(true);
        reviewView.setClickable(true);
        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(reviewView);
        return reviewViewHolder;
    }

    //TODO was going to add updateData method
    //but I think converting to LiveData
    //and using my Repo
    //will help that
    //NEXT STEP: CHEck repo for

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public ReviewViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public List<AaqMovieReview> getReviews(){return mReviews;}
    public interface ReviewRecyclerViewListener {
        void onItemClick(final View view, int reviewSelected);
    }
}
