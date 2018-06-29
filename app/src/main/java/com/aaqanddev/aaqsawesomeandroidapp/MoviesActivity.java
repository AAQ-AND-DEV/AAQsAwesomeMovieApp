package com.aaqanddev.aaqsawesomeandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
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
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.ConnectionCheckTask;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.SecretApiConstants;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;

import java.net.ConnectException;
import java.util.ArrayList;
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
    //TODO move spinner to AppBar(is that what topBar is called?
    Spinner sortSpinner;
    AdapterView.OnItemSelectedListener onSortSelectedChangeListener;
    String mSortChoice;
    List<AaqMovie> main_activity_movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_activity_movies = new ArrayList<>();
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
                            , SecretApiConstants.movieApiConstant,
                            getResources().getString(R.string.lang_default), getResources().getString(R.string.page_default));
            movieListCall.enqueue(new Callback<AaqMovieList>(){

                @Override
                public void onResponse(Call<AaqMovieList> call, Response<AaqMovieList> response) {
                    ArrayList<AaqMovie> currList = new ArrayList<AaqMovie>();
                    if (response.isSuccessful()){
                        AaqMovieList jsonReturned  = response.body();

                        //System.out.println("json returned: " + jsonReturned.toString());
                        //int pgs = response.body().getPage();
                        //System.out.println("no. pgs: " + pgs );
                        if (jsonReturned != null){
                            mAdapter.updateData(response.body().getMovies());
                        }
                    }
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
        moviesRv.setLayoutManager(new GridLayoutManager(this, 2));
        moviesRv.setHasFixedSize(true);
        moviesRv.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MovieRVAdapter(main_activity_movies, this);
        moviesRv.setAdapter(mAdapter);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")){
            new ConnectionCheckTask(new ConnectionCheckTask.Consumer() {
                @Override
                public void accept(Boolean internet) throws ConnectException {
                    if (internet){
                        getData();
                    }
                    else {
                        //hmm...this doesn't seem to be reached -- I get an Error: cancelling call
                        //which is generated by the Retrofit OnFailure(), so that should be fine
                        //I can probably delete this stuff?
                        // TODO (add assurance that sortParam reverts to previous state when error?
                        Toast.makeText(moviesRv.getContext(), "sorry, try again when you have internet",Toast.LENGTH_LONG).show();
                        //TODO *u* upgrade to fragment that accesses Wifi setup?
                        throw new ConnectException("unable to connect to Socket");
                        //TODO <r> How do i handle this exception
                        //do I return to the mainActivity?
                        //return to the calling activity somehow?
                    }
                }
            });

        } else {
            main_activity_movies = savedInstanceState.getParcelableArrayList("movies");
            mAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onItemClick(final View v, final int movieId) {
        //TODO(Q) do I want to do anything with this?
        int itemPos = moviesRv.getChildLayoutPosition(v);
        AaqMovie movie = main_activity_movies.get(itemPos);

        //could make an API call here
        //but I think i should be making a call to the RVadapter
        //could filter the list of movies (while small) to find the matching movieId
        //is there a special way to access cache from Retrofit,
        // or better way to store so it's filterable, other than as List<AaqMovie>?
        //Can prbly use this somewhere? contentProvider, maybe?
        //        List<AaqMovie> res = main_activity_movies.stream()
        //                .filter(movie -> movieId == movie.getId()).collect(Collectors.toList());
        //        if (res!=null) {
        //            AaqMovie movie = res.get(0);
        //              ...}
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra("myMovie", movie);
        this.startActivity(detailIntent);
        //Toast.makeText(this, "i can click and get "+movie.getTitle()+" is surely great?", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       //TODO (perhaps check cache? load if null -- taking care of that in getData(), i believe)
        getData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
