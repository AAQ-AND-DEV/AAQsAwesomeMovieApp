package com.aaqanddev.aaqsawesomeandroidapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaqanddev.aaqsawesomeandroidapp.Adapters.ReviewsAdapter;
import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.DetailMovieViewModel;
import com.aaqanddev.aaqsawesomeandroidapp.databinding.FragmentReviewsBinding;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieReview;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ReviewsFragment extends Fragment {

    private RecyclerView trailers_rv;
    //binding worth doing for just one Var? is it necessary?
    private FragmentReviewsBinding mBinding;
    private ReviewsAdapter mReviewsAdapter;
    private ReviewsAdapter.ReviewRecyclerViewListener mListener;
    private DetailMovieViewModel vm;

    private static final String BUNDLE_MOVIE_ID = "movieId";
    private static final String BUNDLE_VM_ID = "viewModel";

    //should I also pass in the viewModel? that might be interesting
    //I wonder if I am allowed.
    public static ReviewsFragment newInstance(int id) {
        ReviewsFragment f = new ReviewsFragment();

        Bundle args = new Bundle();
        args.putInt(BUNDLE_MOVIE_ID, id);
        f.setArguments(args);
        return f;
    }

    //setViewModel

    //initViewModel? pass in?

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

    //idk what to do here? or how about onViewCreated()?
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //idk should I do a savedInstanceState check here:
        //or does dataBinding do it?
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_reviews, container, false);
        //should I bind viewModel here?

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //so...this adapter is getting set in detailActivity
        mReviewsAdapter = new ReviewsAdapter();
        if (savedInstanceState == null) {
            int movieId = getArguments().getInt(BUNDLE_MOVIE_ID, -1);
            if (movieId != -1) {
                vm = ViewModelProviders.of(this)
                        .get(DetailMovieViewModel.class);
                vm.getmObservableReviewsList().removeObserver(mReviewsObserver);
                vm.getmObservableReviewsList().observe(this, mReviewsObserver);
            }
        } else {
            vm = savedInstanceState.getParcelable(BUNDLE_VM_ID);
            if (vm.getmObservableReviewsList().getValue().size() > 0) {
                mReviewsAdapter.setmReviews(vm.getmObservableReviewsList().getValue());
            }
        }
        //TODO  (rf) update the viewModel with the reviewsAdapter?
        mBinding.setViewModel(vm);
        initRV();

    }

    private void initRV() {
        mBinding.reviewsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.reviewsRV.setHasFixedSize(true);
        mBinding.reviewsRV.setNestedScrollingEnabled(true);
        mBinding.reviewsRV.setAdapter(mReviewsAdapter);
    }

    //hmm...does this mean it will begin to do these things
    //when activityCreated...
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: DOES THIS EVEN GET CALLED FOR FRAGS?");
    }

    //taking in viewModel
    private void subscribeToModel(final DetailMovieViewModel viewModel) {
        //TODO (rf) the observe method stuff in here?
        //viewModel.get
    }
}
