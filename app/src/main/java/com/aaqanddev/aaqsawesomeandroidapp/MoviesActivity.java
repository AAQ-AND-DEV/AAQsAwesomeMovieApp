package com.aaqanddev.aaqsawesomeandroidapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.content.res.Resources;

import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
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
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.FavoriteMoviesDao;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.MovieApiInterface;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.ConnectionCheckTask;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.ViewModels.DetailMovieViewModel;
import com.aaqanddev.aaqsawesomeandroidapp.ViewModels.FaveMovieListViewModel;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieReview;
import com.aaqanddev.aaqsawesomeandroidapp.repos.AaqMovieRepo;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoviesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MovieRVAdapter.MainRecyclerViewClickListener {

    //TODO (I)
    RecyclerView moviesRv;
    private MovieRVAdapter mAdapter;
    GridLayoutManager mGridLayoutManager;
    MovieApiInterface movieApiInterface;
    //TODO (f) move spinner to AppBar(is that what topBar is called?
    Spinner sortSpinner;
    AdapterView.OnItemSelectedListener onSortSelectedChangeListener;
    String mSortChoice;
    Parcelable mListState;
    //If I convert this to LiveData, I don't see any payout right now
    List<AaqMovie> main_activity_movies;

    FaveMovieListViewModel mFavesMovieModel;
    DetailMovieViewModel detailViewModel;
    AaqMovieAppExecutors mExecutors;
    AaqMovieRepo mRepo;
    //TODO adding the FaveDb...prbly want to route everything through the repo, eventually



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //grab executors
        AaqMovieApp app = new AaqMovieApp();
        mExecutors = app.getAppExecutors();
        mRepo = AaqMovieRepo.getInstance(app, mExecutors);

        detailViewModel = ViewModelProviders.of(this).get(DetailMovieViewModel.class);
        if (savedInstanceState != null){
            //TODO (U) upgrade/convert this to retrieving this data from SharedViewModel?
            //when
            main_activity_movies = savedInstanceState.getParcelableArrayList(getString(R.string.bundle_movies_key));
            mAdapter.updateData(main_activity_movies);
            mGridLayoutManager.onRestoreInstanceState(mListState);
        } else {
            main_activity_movies = new ArrayList<>();
            //TODO inside getData() I should invoke the DataManager/repo
            //via Application?
            getData();
        }

        setContentView(R.layout.activity_movies);

        //TODO (Q) should all this FaveMoviesSetup be in initViews() or getData()?
        // Is the viewModel helpful for each movie?
        //or just faveMovieList? --

        //TODO get ViewModel [this is for faves] -- tho I wouldn't want
        //the view to update or anything, unless it's active
        //which LiveData and VM will handle, I believe...

        mFavesMovieModel = ViewModelProviders.of(this).get(FaveMovieListViewModel.class);
        //TODO set up observer --
        /*final Observer<AaqMovieList> mainMovieListObserver =
                new Observer<AaqMovieList>() {
                    @Override
                    public void onChanged(@Nullable List<AaqMovie> aaqMovies) {
                        //TODO(?) create setMovies() method?!?
                        mFaveMovies.setMovies(aaqMovies);
                    }
                };
*/
        final Observer<AaqMovieList> faveMovieListObserver =
                new Observer<AaqMovieList>() {
                    @Override
                    public void onChanged(@Nullable final AaqMovieList aaqMovieList) {
                        //ah -- this  should be grabbing a layout item
                        //TODO (figure out the proper target -- probly the RV!)
                        //I don't want it to automatically display the
                        //faves -- this will do so, yes? and it would doso in a way
                        //that doesn't make clear it's a change in the display type
                        //moviesRv.setMovies(aaqMovieList);
                        //TODO (sol?) create method that updates this variable
                        //believe I'll need to make a helper method in AaqMovieList
                        //i don't think I want to set the View actually
                        //this will be good
                        //
                        //TODO (i) two things a) update member var of faveList (mFaveMovies)
                        //                    b)
                        //mFaveMovies.setValue(aaqMovieList);
                        //oh wait...the whole point of  LiveData is
                        //to avoid storing  that state in the view!!!!!!
                        //TODO(duh)

                    }
                };
        //connect observer with view
        mFavesMovieModel.getFaveMovieList().observe(this, faveMovieListObserver);


        //TODO connect to Db (also  add memberVars applicable)

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
        //DONE () pass in the Adapter -- to  maintain adapterPosition
        mListState = mGridLayoutManager.onSaveInstanceState();
        outState.putParcelable(getString(R.string.bundle_layout_manager_key), mListState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")){
            getData();
        } else {

            main_activity_movies = savedInstanceState.getParcelableArrayList("movies");
            mListState = savedInstanceState.getParcelable(getString(R.string.bundle_layout_manager_key));

        }
    }

    private void getData(){
           //etc.
            Retrofit movieRetro = MoviesAPIClient.getClientBuilder().build();
            movieApiInterface = movieRetro.create(MovieApiInterface.class);
            //TODO move this stuff to another class...

            // done conditional -- if sortSpinner.getSelectedItemPosition()==2, doGetFaves
            if (sortSpinner.getSelectedItemPosition()==2){
                //TODO do the Db fetch...via FaveMoviesDao

            }
            else {
                //TODO do the retroCall

            }
            Call<AaqMovieList> movieListCall = movieApiInterface
                    .doGetMovieList(getResources().getStringArray(R.array.pref_fetch_by_vals)[sortSpinner.getSelectedItemPosition()]
                            , R.string.api_key,
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

        //TODO I change this to making a Repo and getting the movies that way
        //i believe moving all this to Repo? maybe --> ?
        //TODO and convert it from Call to a LiveData deal (watch Anuja's vid again)
        /*
        AaqMovieRepo repo = new AaqMovieRepo(this.getApplication());
        //this needs to be done in a particular fashion
        //idk enqueue these?
        //alright so this repo will be calling the
        LiveData<List<AaqMovie>> faveMovies = repo.getAllFaveMovies();
        */
        }


    private void initViews( Bundle savedInstanceState){
        moviesRv = (RecyclerView) findViewById(R.id.recyclerview_main_movies);
        mGridLayoutManager  = new GridLayoutManager(this, 2);
        moviesRv.setLayoutManager(mGridLayoutManager);
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
        //TODO (u) could be doing this via a REPO, ?
        //idk but how? accept the main_movies and itemPos into method?
        AaqMovie movie = main_activity_movies.get(itemPos);

        //TODO (?) I think I want to make a ViewModel interaction...
        //but viewModel is not supposed to know of the activity
        //so the correct approach
        //would be to pass that data
        //update the ViewModel when movie first grabbed, right?
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
        //TODO (Q) Make network calls to attain trailers and reviews while activity being
        //started?
        //so -- TODO (it) I want to prbly just instantiate the ViewModel  here
        // TODO (?) set a toggleState for the ViewModel to know which activity is accessing it?
        // thereby doing detailViewModel.onDetail();
        //with a click...
        //i want to initiate the call...idk I may be able to leave an empty observer here?
        detailViewModel.getmObservableReviewsList().observe(this, new Observer<List<AaqMovieReview>>() {
            @Override
            public void onChanged(@Nullable List<AaqMovieReview> aaqMovieReviews) {
                //TODO I don't actually want the ViewModel to do anything here
                //idk I just wanted it to start getting the data for trailers/reviews
                //will this do so?
            }
        });
        // idk is there a way to make that delivered to the activity below? Service?
        //idk let's look at the Retrofit call (which I will be making from the Repo, yes?)

        //suppose that would get complicated, (unless i added viewModel to trailers/reviews
        // and LiveData perhaps?)
        this.startActivity(detailIntent);
        //Toast.makeText(this, "i can click and get "+movie.getTitle()+" is surely great?", Toast.LENGTH_SHORT).show();

    }
//Method pertains to Sort preferences
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
