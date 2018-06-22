package com.aaqanddev.aaqsawesomeandroidapp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aaqanddev.aaqsawesomeandroidapp.databinding.ActivityMovieDetailBinding;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;

public class MovieDetailActivity extends AppCompatActivity {

    //need to hook up data binding -- this would be too tedious


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        AaqMovie thisMovie = getIntent().getParcelableExtra("myMovie");
        viewDataBinding.setMovie(thisMovie);
        viewDataBinding.movieDetailTitle.setContentDescription(getString(R.string.ally_movie_title));
        viewDataBinding.movieDetailReleaseDate.setContentDescription(getString(R.string.ally_movie_date));
        viewDataBinding.movieDetailSummary.setContentDescription(getString(R.string.ally_movie_desc));
        viewDataBinding.movieDetailVoteAverage.setContentDescription(getString(R.string.ally_movie_vote_ave));
        viewDataBinding.movieDetailPosterIv.setContentDescription(getString(R.string.ally_movie_poster_image));
    }
}
