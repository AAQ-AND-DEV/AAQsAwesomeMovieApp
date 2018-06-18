package com.aaqanddev.aaqsawesomeandroidapp;

import android.content.res.Resources;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoviesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MovieRVAdapter.MainRecyclerViewClickListener {

    RecyclerView moviesRv;
    private MovieRVAdapter mAdapter;
    MovieApiInterface movieApiInterface;
    MovieRVAdapter.MainRecyclerViewClickListener mMainRVlistener;
    //TODO move spinner to AppBar(is that what topBar is called?
    Spinner sortSpinner;
    AdapterView.OnItemSelectedListener onSortSelectedChangeListener;
    String mSortChoice;
    List<AaqMovie> main_activity_movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //main_activity_movies = new ArrayList<>(20);
        setContentView(R.layout.activity_movies);



        sortSpinner = (Spinner) findViewById(R.id.sort_pref_spinner);
        ArrayAdapter<CharSequence> sortSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.pref_fetch_by_labels, android.R.layout.simple_spinner_item);
        sortSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sortSpinner.setAdapter(sortSpinnerAdapter);
        sortSpinner.setSelection(0);
        sortSpinner.setOnItemSelectedListener(this);

        initViews(savedInstanceState);

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList("movies", new ArrayList<AaqMovie>(mAdapter.getMovies()));
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")){
            getData();
        } else {
            main_activity_movies = savedInstanceState.getParcelableArrayList("movies");

        }

    }

    private void getData(){
           //etc.
            Retrofit movieRetro = MoviesAPIClient.getClientBuilder().build();
            movieApiInterface = movieRetro.create(MovieApiInterface.class);
            Call<AaqMovieList> movieListCall = movieApiInterface
                    .doGetMovieList(getResources().getStringArray(R.array.pref_fetch_by_vals)[sortSpinner.getSelectedItemPosition()]
                            , SecretApiConstant.movieApiConstant,
                            getResources().getString(R.string.lang_default), getResources().getString(R.string.page_default));
            movieListCall.enqueue(new Callback<AaqMovieList>(){

                @Override
                public void onResponse(Call<AaqMovieList> call, Response<AaqMovieList> response) {
                    ArrayList<AaqMovie> currList = new ArrayList<AaqMovie>();
                    if (response.isSuccessful()){
                        AaqMovieList res = response.body();
                        currList = new ArrayList<AaqMovie>(res.getMovies());

                    }
                    main_activity_movies = currList;

/*                Integer pageText = movieList.page;
                Integer total = movieList.total;
                Integer totalPages = movieList.totalPages;
  */
                    //List<AaqMovieList.Datum> datumList = movieList.data;
                    //had toasted: */pageText + "page\n" + total + "total\n" + totalPages + "totalPages\n"*/
                    //Toast.makeText(getApplicationContext(), "something", Toast.LENGTH_SHORT).show();
                    // List<AaqMovie> movies = new ArrayList<AaqMovie>(movieList);

                    //TODO(Q) doI also need to do this?
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<AaqMovieList> call, Throwable t) {
                    Toast.makeText(MoviesActivity.this, "error: cancelling call", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            } );
        }


    private void initViews( Bundle savedInstanceState){
        moviesRv = (RecyclerView) findViewById(R.id.recyclerview_main_movies);
        //look closely here -- I must figure out how to use  onItemClickListener here or elsewhere
        //mAdapter = new MovieRVAdapter( main_activity_movies, this);
        moviesRv.setLayoutManager(new GridLayoutManager(this, 2));
        moviesRv.setHasFixedSize(true);
        moviesRv.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MovieRVAdapter(main_activity_movies, this);
        moviesRv.setAdapter(mAdapter);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")){
            getData();
        } else {
            main_activity_movies = savedInstanceState.getParcelableArrayList("movies");
        }
    }
    @Override
    public void onClick(final int movieId) {
        //TODO(Q) do I want to do anything with this?
        //this is getting called by line 124 in MovieRVAdapter mOnItemClickListener.onClick();
        //Direct to Detail_Activity? Already doing that from onClick handle in ViewHolder

        //could make an API call here
        //but I think i should be making a call to the RVadapter
        //could filter the list of movies (while small) to find the matching movieId
        //is there a special way to access cache from Retrofit,
        // or better way to store so it's filterable, other than as List<AaqMovie>?
        // do I need to do anything here at all, perhaps this is where I should launchActivity?
        List<AaqMovie> res = main_activity_movies.stream()
                .filter(movie -> movieId == movie.getId()).collect(Collectors.toList());
        AaqMovie movie = res.get(0);
        Toast.makeText(this, "i can click and get "+movie.getTitle()+" is surely great?", Toast.LENGTH_SHORT);

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
