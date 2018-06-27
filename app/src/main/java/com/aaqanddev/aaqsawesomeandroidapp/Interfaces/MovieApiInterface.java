package com.aaqanddev.aaqsawesomeandroidapp.Interfaces;

import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.MultipleResource;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiInterface {
    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    /*
    @POST("/api/movies")
    Call<AaqMovie> createMovie(@Body AaqMovie aaqMovie);
    */

    //TODO (add params)
    @GET("/3/movie/{sortParam}")
    Call<AaqMovieList> doGetMovieList(@Path("sortParam") String sort,  @Query("api_key") String apiKey, @Query("language") String language, @Query("page") String page);





}
