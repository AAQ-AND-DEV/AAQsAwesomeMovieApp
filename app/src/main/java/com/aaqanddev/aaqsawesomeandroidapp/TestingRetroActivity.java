package com.aaqanddev.aaqsawesomeandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.MovieApiInterface;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailerList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TestingRetroActivity extends AppCompatActivity {
    Retrofit movieRetro = MoviesAPIClient.getClientBuilder().build();
    MovieApiInterface movieApiInterface = movieRetro.create(MovieApiInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_retro);

        TextView testMovie  = findViewById(R.id.testGetMovie);
        final StringBuilder sampleMovie = new StringBuilder();

        TextView  testTrailers = findViewById(R.id.testTrailers);
        final StringBuilder sampleTrailers =  new StringBuilder();

        TextView testReviews = findViewById(R.id.testReviews);
        final StringBuilder sampleReviews = new StringBuilder();

        Call<AaqMovieTrailerList> trailersCall = movieApiInterface.
                doGetMovieTrailers(470);
        trailersCall.enqueue(new Callback<AaqMovieTrailerList>() {
            @Override
            public void onResponse(Call<AaqMovieTrailerList> call, Response<AaqMovieTrailerList> response) {
                ArrayList<AaqMovieTrailer> currTrailers = new ArrayList<>();
                if (response.isSuccessful()){
                    AaqMovieTrailerList jsonReturned = response.body();
                    if (jsonReturned != null){
                        sampleTrailers.append(jsonReturned.trailersToString(response.body().getMovieTrailers()));
                        testTrailers.setText(sampleTrailers);
                    }
                }
            }

            @Override
            public void onFailure(Call<AaqMovieTrailerList> call, Throwable t) {
                Toast.makeText(TestingRetroActivity.this, "error: cancelling call", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
}
