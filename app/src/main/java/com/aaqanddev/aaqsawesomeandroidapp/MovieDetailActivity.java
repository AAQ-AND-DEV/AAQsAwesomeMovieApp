package com.aaqanddev.aaqsawesomeandroidapp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.databinding.ActivityMovieDetailBinding;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    //need to hook up data binding -- this would be too tedious



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        AaqMovie thisMovie = getIntent().getParcelableExtra("myMovie");
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
        viewDataBinding.movieDetailReleaseDate.setContentDescription(getString(R.string.ally_movie_date));
        viewDataBinding.movieDetailSummary.setContentDescription(getString(R.string.ally_movie_desc));
        viewDataBinding.movieDetailVoteAverage.setContentDescription(getString(R.string.ally_movie_vote_ave));
        viewDataBinding.movieDetailPosterIv.setContentDescription(getString(R.string.ally_movie_poster_image));

    }
}
