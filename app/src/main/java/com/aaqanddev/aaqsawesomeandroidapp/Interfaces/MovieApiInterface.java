package com.aaqanddev.aaqsawesomeandroidapp.Interfaces;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MovieApiInterface {
    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    /*
    @POST("/api/movies")
    Call<AaqMovie> createMovie(@Body AaqMovie aaqMovie);
    */

    @GET("/api/movies?")
    CALL<AaqMovieList> doGetMovieList(@Query("page") String page);


}
