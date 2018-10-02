package com.aaqanddev.aaqsawesomeandroidapp.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.ViewModels.DetailMovieViewModel;
import com.aaqanddev.aaqsawesomeandroidapp.databinding.ReviewsRvItemBinding;
import com.aaqanddev.aaqsawesomeandroidapp.databinding.TrailersRvItemBinding;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieReview;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>{

    private ReviewRecyclerViewListener mListener;
    List<AaqMovieReview> mReviews;
    //TODO (u) add binding also add
    //quoting from the review in db: "seiht wosx oi"
    //interactivity with reviews

    public ReviewsAdapter(List<AaqMovieReview> reviews, ReviewRecyclerViewListener reviewClickListener){
        mListener = reviewClickListener;
        mReviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewsRvItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.reviews_rv_item,
                        parent, false);
        //binding.authorReviewTv
        //do i need to do anything here?
        //reviewView.setFocusable(true);
        //reviewView.setClickable(true);
         return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        if (mReviews != null) {
            AaqMovieReview review = mReviews.get(position);
            holder.binding.setReview(review);
            holder.binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        if (mReviews != null) {
            return mReviews.size();
        }
        else return 0;
        //alternative syntax: is this called ternary
        //return mReviews == null ? 0 : mReviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        final ReviewsRvItemBinding binding;

        public ReviewViewHolder(ReviewsRvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void setmReviews(List<AaqMovieReview> newReviews) {
        this.mReviews = newReviews;
        notifyDataSetChanged();
    }

   public interface ReviewRecyclerViewListener {
        void onItemClick(final View view, int reviewSelected);
    }
}
