package com.aaqanddev.aaqsawesomeandroidapp;

import android.content.res.Resources;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.aaqanddev.aaqsawesomeandroidapp.Adapters.MovieRVAdapter;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.MovieApiInterface;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.SecretApiConstant;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoviesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MovieRVAdapter.MainRecyclerViewClickListener {

    RecyclerView moviesRv;
    private MovieRVAdapter mAdapter;
    MovieApiInterface movieApiInterface;
    MovieRVAdapter.MainRecyclerViewClickListener mMainRVlistener;
    Spinner sortSpinner;
    AdapterView.OnItemSelectedListener onSortSelectedChangeListener;
    String mSortChoice;
    List<AaqMovie> main_activity_movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main_activity_movies = new LinkedList<>();
        setContentView(R.layout.activity_movies);
        moviesRv = (RecyclerView) findViewById(R.id.recyclerview_main_movies);
        mAdapter = new MovieRVAdapter(main_activity_movies, MoviesActivity.this);
        moviesRv.setLayoutManager(new GridLayoutManager(this, 2));
        moviesRv.setItemAnimator(new DefaultItemAnimator());
        moviesRv.setAdapter(mAdapter);

        sortSpinner = (Spinner) findViewById(R.id.sort_pref_spinner);
        ArrayAdapter<CharSequence> sortSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.pref_fetch_by_labels, android.R.layout.simple_spinner_item);
        sortSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortSpinnerAdapter);
        sortSpinner.setOnItemSelectedListener(this);

        Retrofit movieRetro = MoviesAPIClient.getClientBuilder().build();
        movieApiInterface = movieRetro.create(MovieApiInterface.class);
        getData();


    }

    //Must implement parcelable -- should I check on additional variables like the favoritesList?
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void getData(){
        Call<List<AaqMovie>> movieListCall = movieApiInterface
                .doGetMovieList(getResources().getStringArray(R.array.pref_fetch_by_vals)[sortSpinner.getSelectedItemPosition()]
                        , SecretApiConstant.movieApiConstant,
                        getResources().getString(R.string.lang_default), getResources().getString(R.string.page_default));
        movieListCall.enqueue(new Callback<List<AaqMovie>>(){

            @Override
            public void onResponse(Call<List<AaqMovie>> call, Response<List<AaqMovie>> response) {
                List<AaqMovie> movieList = response.body();

/*                Integer pageText = movieList.page;
                Integer total = movieList.total;
                Integer totalPages = movieList.totalPages;
  */
                //List<AaqMovieList.Datum> datumList = movieList.data;
                //had toasted: */pageText + "page\n" + total + "total\n" + totalPages + "totalPages\n"*/
                //Toast.makeText(getApplicationContext(), "something", Toast.LENGTH_SHORT).show();

                List<AaqMovie> movies = new LinkedList<>(movieList);
/*
                for (AaqMovie aaqMovie: movieList){

                    movies.add(new AaqMovie(datum.id, datum.overview, datum.posterPath, datum.title, datum.voteAverage));
                }
  */
                //TODO(Q) I also need to
                main_activity_movies = movies;

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AaqMovie>> call, Throwable t) {
                Toast.makeText(MoviesActivity.this, "error: cancelling call", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        } );
    }

    @Override
    public void onClick(int movieId) {
        //Direct to Detail_Activity
        Toast.makeText(this, "i can click", Toast.LENGTH_SHORT);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       //TODO (must do something with this?...perhaps check cache? load if null)
        getData();

        //maybe set preference, and implement onPreferenceChange to trigger fetch
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
