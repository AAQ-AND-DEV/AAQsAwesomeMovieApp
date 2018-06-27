package com.aaqanddev.aaqsawesomeandroidapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.databinding.ActivityMovieDetailBinding;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.Genre;
import com.squareup.picasso.Picasso;

import java.util.GregorianCalendar;

public class MovieDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        AaqMovie thisMovie = getIntent().getParcelableExtra("myMovie");
        //TODO debug -- seems like perhaps adding the ImageButton without doing a databinding call is causing a problem in setMovie?
        viewDataBinding.setMovie(thisMovie);
        //int movieId = thisMovie.getId();
        Picasso.with(this)
                .load(                                      //this should depend on calling activity, right?
                        MoviesAPIClient.buildMoviePosterUrl(this.getResources().getString(R.string.size_poster_large), thisMovie.getPosterPath()).toString())
                .fit()
                .placeholder(R.drawable.ic_movie_poster_paceholder)
                .error(R.drawable.ic_error_face)
                .into(viewDataBinding.movieDetailPosterIv);
        viewDataBinding.movieDetailTitle.setContentDescription(getString(R.string.ally_movie_title));
        viewDataBinding.faveImgButton.setContentDescription(getString(R.string.ally_fave_button));
        viewDataBinding.movieDetailReleaseDate.setContentDescription(getString(R.string.ally_movie_date));
        viewDataBinding.movieDetailSummary.setContentDescription(getString(R.string.ally_movie_desc));
        viewDataBinding.movieDetailVoteAverage.setContentDescription(getString(R.string.ally_movie_vote_ave));
        viewDataBinding.movieDetailPosterIv.setContentDescription(getString(R.string.ally_movie_poster_image));

    }
}
