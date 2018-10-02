package com.aaqanddev.aaqsawesomeandroidapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaqanddev.aaqsawesomeandroidapp.Adapters.ReviewsAdapter;
import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.ViewModels.DetailMovieViewModel;
import com.aaqanddev.aaqsawesomeandroidapp.databinding.FragmentReviewsBinding;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieReview;

import java.util.List;

import View.OnClickListener;

public class ReviewsFragment extends Fragment implements View.OnClickListener {

    private RecyclerView trailers_rv;
    //binding worth doing for just one Var? is it necessary?
    private FragmentReviewsBinding mBinding;
    private ReviewsAdapter mReviewsAdapter;
    private ReviewsAdapter.ReviewRecyclerViewListener mListener;
    private DetailMovieViewModel vm;

    {
        //should I subscribe here?
        subscribeToModel();
    }

    //should this Observer be private?
    private final Observer<List<AaqMovieReview>> mReviewsObserver = new Observer<List<AaqMovieReview>>() {
        @Override
        public void onChanged(@Nullable List<AaqMovieReview> aaqMovieReviews) {
            //onChange in Fragment Observer
            //TODO 1) set boolean (as yet undeclared) ViewLoading to true

            //instantiate Listener
            mListener = new ReviewsAdapter.ReviewRecyclerViewListener() {
                @Override
                public void onItemClick(View view, int reviewSelected) {
                //TODO (u) launch Reviews Focus to expand text
                // or (for now) do nothing?

                }
            };
            //if I make ReviewsAdapter static in its class?
            mReviewsAdapter = new ReviewsAdapter(aaqMovieReviews, mListener);
            //TODO assign adapter //DTMS? or should I have a swapView or a SetMovies method in adapter
            //TODO ? make a bind method?
            trailers_rv.setAdapter(mReviewsAdapter);

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //should I do a savedInstanceState check here:
        //or does dataBinding do it?
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews, container, false);

        //get RecyclerView
        trailers_rv = mBinding.reviewsRV;
        //get reviews from savedInstanceState?
        //ALSO add to onSavedInstanceState trailers and reviews!
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = ViewModelProviders.of(this)
                .get(DetailMovieViewModel.class);
        vm.getmObservableReviewsList().removeObserver(mReviewsObserver);
        vm.getmObservableReviewsList().observe(this, mReviewsObserver);
    }

    //taking in viewModel
    private void subscribeToModel(final DetailMovieViewModel viewModel){
        //TODO (rf) the observe method stuff in onActivityCreated here?
        //viewModel.get
    }

    @Override
    public void onClick(View v) {

    }
}
