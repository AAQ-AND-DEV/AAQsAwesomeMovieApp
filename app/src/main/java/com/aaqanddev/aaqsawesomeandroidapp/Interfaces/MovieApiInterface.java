package com.aaqanddev.aaqsawesomeandroidapp.Interfaces;

import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailerList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqReviewsList;
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

    //url for ref:
    //   https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=<<api_key>>&language=en-US
    //default values for queries
    @GET("/3/movie/{id}/videos?api_key="+ R.string.api_key+"&language="+R.string.lang_default)
    Call<AaqMovieTrailerList> doGetMovieTrailers(@Path("id") int  id);

    @GET("/3/movie/{id}/videos")
    Call<AaqMovieTrailerList> doGetMovieTrailers(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String lang);
    ///movie/{id}/reviews
    //add defaults?
    @GET("3/movie/{id}/reviews")
    Call<AaqReviewsList> doGetReviewsList(@Path("id") int id, @Query("api_key") String apiKey, @Query("lang") String lang);

    //TODO (Explore) Headers for returning cache-state (is this exclusive to web?
    //TODO (Q) is it the server's cache being informed upon or other?
    //TODO (I?) here are cache options: https://www.tutorialspoint.com/http/http_header_fields.htm
}
