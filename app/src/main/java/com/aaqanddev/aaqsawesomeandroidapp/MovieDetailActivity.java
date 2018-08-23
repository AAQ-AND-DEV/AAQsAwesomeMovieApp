package com.aaqanddev.aaqsawesomeandroidapp;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.MovieApiInterface;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.databinding.ActivityMovieDetailBinding;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.Genre;
import com.aaqanddev.aaqsawesomeandroidapp.repos.AaqMovieRepo;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.GregorianCalendar;

import retrofit2.Retrofit;

public class MovieDetailActivity extends AppCompatActivity {

    AaqMovie mMovieModel;
    Selector mFaveButtonSelector;
    Button mFaveButton;
    MovieApiInterface movieApiInterface;
    AaqMovieRepo movieRepo;
    //done -- data binding --


    //TODO i'm assuming I'll have to set that state upon init
    //TODO access isFave (via observer)


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
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
        //summon Retro
        Retrofit movieRetro = MoviesAPIClient.getClientBuilder().build();
        movieApiInterface = movieRetro.create(MovieApiInterface.class);
        //does this return a call?
        movieApiInterface.doGetMovieTrailers(currId);
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

        //access ViewModel (extended by AaqMovie)
        mMovieModel = ViewModelProviders.of(this).get(AaqMovie.class);
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
        mMovieModel.getIsFavorite().observe(this, faveBoolObserver);
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

        MutableLiveData<Boolean> isFave = thisMovie.getIsFavorite();

        try {

            mFaveButtonSelector.close();

        } catch (IOException exception){
            exception.printStackTrace();
        }

        //get linearLayout
        LinearLayout trailersLinLao = viewDataBinding.trailersLinLayout;



    }
}
