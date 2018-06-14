package com.aaqanddev.aaqsawesomeandroidapp.Interfaces;

import android.content.Context;
import android.content.res.Resources;

import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.MultipleResource;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiInterface {
    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    /*
    @POST("/api/movies")
    Call<AaqMovie> createMovie(@Body AaqMovie aaqMovie);
    */
    /*
    String fetch_rate_key = Resources.getSystem().getString(R.string.fetch_by_rating_JSON_key);
    String fetch_by_pop_JSON_Key = Resources.getSystem().getString(R.string.fetch_by_pop_JSON_key);

    */
    //could use this as var in annotation as well in future
    public static final String movie_JSON_key = "movie";
    //DONE made this a mVar in MainActivity --
    //String queryParam = Resources.getSystem().getStringArray(R.array.pref_fetch_by_vals)[sortSpinner];

    //TODO (add params)
    @GET("/3/movie/{sortParam}")
    Call<AaqMovieList> doGetMovieList(@Path("sortParam") String sort,  @Query("api_key") String apiKey, @Query("language") String language, @Query("page") String page);





}
