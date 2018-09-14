package com.aaqanddev.aaqsawesomeandroidapp;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aaqanddev.aaqsawesomeandroidapp.Adapters.ReviewsAdapter;
import com.aaqanddev.aaqsawesomeandroidapp.Adapters.TrailersAdapter;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.MovieApiInterface;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.ViewModels.DetailMovieViewModel;
import com.aaqanddev.aaqsawesomeandroidapp.databinding.ActivityMovieDetailBinding;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieReview;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailerList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqReviewsList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.Genre;
import com.aaqanddev.aaqsawesomeandroidapp.repos.AaqMovieRepo;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Retrofit;

public class MovieDetailActivity extends AppCompatActivity {

    DetailMovieViewModel mMovieModel;
    //The Subject of the Observer Pattern
    Selector mFaveButtonSelector;
    Button mFaveButton;
    //PRETTY SURE I DON'T WANT TO BE DOING ANY API calls anymore
    //TODO  (U) convert away any View data access code
    //MovieApiInterface movieApiInterface;
    AaqMovieRepo movieRepo;
    //viewModels for the Reviews and trailers?
    //does that make sense?
    AaqReviewsList mReviewsListViewModel;
    AaqMovieTrailerList mTrailersListViewModel;
    //idk should these be liveData?
    //or perhaps these are better done anonymously?
    //or lambda?
    Observer<List<AaqMovieTrailer>> mTrailersObserver;
    Observer<List<AaqMovieReview>> mReviewsObserver;
    //TODO (e) expand for landscape layouts to include multiple movies?
    //or TODO (e) the list of movies and the detail for one.
    //done -- data binding --

    //TODO (u) databinding to the fragmentViewModel?
    //TODO (i) declare recyclerViews for trailers/reviews
    RecyclerView trailersRV;
    TrailersAdapter trailersAdapter;
    RecyclerView reviewsRV;
    ReviewsAdapter reviewsAdapter;


    //TODO i'm assuming I'll have to set that state upon init
    //TODO access isFave (via observer)

    //testing CoordinatorLayout
    //CoordinatorLayout coordLO = findViewById(R.id.activity_detail_coordinator);
    //TODO (u) add a feature via context Menu
    //coordLO.OnCreateContextMenuListener

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //TODO do stuff to receive data on rotation/etc.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        AaqMovie thisMovie = getIntent().getParcelableExtra("myMovie");
        //TODO debug -- seems like perhaps adding the ImageButton without doing a databinding call is causing a problem in setMovie?
        viewDataBinding.setMovie(thisMovie);
        int currId = thisMovie.getId();
        //summon Retro TODO (u) create RetroClient in the application layer?
        //DTMS actually should i do this
        //
        Retrofit movieRetro = MoviesAPIClient.getClientBuilder().build();
        movieApiInterface = movieRetro.create(MovieApiInterface.class);

        //Set up the ViewModels for trailers and reviews
        // TODO (rf) idk I actually will intend to combine all on this view)
        //could do sub-views//fragment-based approach next
        //and create the viewModel common to all models
        mReviewsListViewModel = ViewModelProviders.of(this).get(AaqReviewsList.class);
        mTrailersListViewModel = ViewModelProviders.of(this).get(AaqMovieTrailerList.class);

        //observations
        //for Reviews:
        mReviewsObserver = new Observer<List<AaqMovieReview>>(){
            @Override
            public void onChanged(@Nullable List<AaqMovieReview> aaqMovieReviews) {
                if (aaqMovieReviews !=  null){
                    //TODO (i) indicate loaded for UI management
                    //TODO (i) setReviews in RVadapter
                    //DTMS ?! need to set up TODO (a)
                }
                else {
                    //TODO indicate StillLoading
                }
            }

        };
        //I suppose in my viewModel class, I could return via call to the Repo or from viewModel, right?
        // done initiate observation -- TODO (rf) put in helper method
        // for reviews
        //observing Review ViewModel
        mReviewsListViewModel.getResults().observe(this, mReviewsObserver);

        //for Trailers:
        //TODO (rf) put in trailer frag?
        //IDK how does this work with a repo?
        //I believe all this should be there!
        //would that mean passing
        mTrailersObserver = new Observer<List<AaqMovieTrailer>>() {
            @Override
            public void onChanged(@Nullable List<AaqMovieTrailer> aaqMovieTrailers) {
                if (aaqMovieTrailers != null){
                    //TODO (i) indicate Loaded
                    //DTMS? is there such thing as a placeholder fragment?
                    //one that temporarily shows while Trailers and REviews are loading?
                    //TODO (I) setTrailers in RVadapter (or idk should this be an updateList() call)
                    trailersRV.setAdapter(new TrailersAdapter(aaqMovieTrailers));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetailActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    trailersRV.setLayoutManager(layoutManager);
                }
                else {
                    //TODO show still loading
                }
            }
        };
        //set up observation of ViewModel -- thereby potentially allowing
        //decoupling of the loading of Details and loading of fragments
        //TODO but I need to make sure the initial view is there first
        mTrailersListViewModel.getMovieTrailers().observe(this, mTrailersObserver);

        //TODO (rf) put into LoadMovie method
        //how should I grab my interface (pass in bundle to repo?)
        //
        movieApiInterface =
                .doGetMovieTrailers(currId);
        //int movieId = thisMovie.getId();

        Picasso.with(this)
                .load(                                      //this should depend on calling activity, right?
                        MoviesAPIClient.buildMoviePosterUrl(this.getResources().getString(R.string.size_poster_large), thisMovie.getPosterPath()).toString())
                .fit()
                .placeholder(R.drawable.ic_movie_poster_paceholder)
                .error(R.drawable.ic_error_face)
                .into(viewDataBinding.movieDetailPosterIv);
        viewDataBinding.movieDetailTitle.setContentDescription(getString(R.string.ally_movie_title));
        mFaveButton = findViewById(R.id.fave_img_button);
        mFaveButton.setContentDescription(getString(R.string.ally_fave_button));
        viewDataBinding.movieDetailReleaseDate.setContentDescription(getString(R.string.ally_movie_date));
        viewDataBinding.movieDetailSummary.setContentDescription(getString(R.string.ally_movie_desc));
        viewDataBinding.movieDetailVoteAverage.setContentDescription(getString(R.string.ally_movie_vote_ave));
        viewDataBinding.movieDetailPosterIv.setContentDescription(getString(R.string.ally_movie_poster_image));

        //TODO (uncomment!) access detail ViewModel (could do this for bindings above) probly
        //but will limit it simply to monitoring the isFave bool, i think
        mMovieModel = ViewModelProviders.of(this).get(DetailMovieViewModel.class);
        //TODO (i) add observer pattern for isFavorite
        //idk for observer of isFave open()? or close()? selector
        //is that relevant for multiple-threaded functionality
        //Here: i think just SetEnabled(T/F) is appropriate
        final Observer<Boolean> faveBoolObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean fave) {

                //TODO delta view based on that state
                if (fave){
                    //TODO (q) will this automatically be reflected in view?
                    //idk also will this cause a circular logic?  does setEnabled itself not constitute a change?
                    //i don't thinks so, since it's the button, not the LiveData in the VeiwModel that's changing?
                    mFaveButton.setEnabled(true);
                }
            }
        };
        //Link observer with LD in VM
        //TODO testing it
        mMovieModel.getIsFave().observe(this, faveBoolObserver);
        //TODO (i) set listener to update/ postValue() to model
        mFaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  conditional to do inverse if...
                if(mFaveButton.isSelected()==false) {
                    mFaveButton.setEnabled(true);
                }
                else {
                    mFaveButton.setEnabled(false);
                }

            }
        });
        //ahhhhh TODO(i) need to change so that I'm accessing
        //aaqmovie via repo, so that I can leave aaqmovie as LiveData
        //but -- will I be able to get
        //also idk wtf this is about either?
        MutableLiveData<Boolean> isFave = thisMovie.getIsFavorite();

        //idk wtf this is or where it came from.
        try {

            mFaveButtonSelector.close();

        } catch (IOException exception){
            exception.printStackTrace();
        }

        //TODO set up the recyclerView and add?
        //but won't it
        loadTrailers();
        //TODO write loadReviews()
        loadReviews();


    }

    private void loadTrailers(){
        trailersRV = findViewById(R.id.trailers_r_v);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        trailersAdapter = new TrailersAdapter(getTrailers());
    }
    //TODO  (i) make call to repo...or rather
    //DTMS? make this observe the repo call?
    private List<AaqMovieTrailer> getTrailers(){

    }

    private void loadReviews(){
        reviewsRV = findViewById(R.id.reviews_r_v);
    }
}
