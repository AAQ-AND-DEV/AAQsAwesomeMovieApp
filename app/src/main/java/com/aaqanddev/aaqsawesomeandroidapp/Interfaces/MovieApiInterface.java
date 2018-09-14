package com.aaqanddev.aaqsawesomeandroidapp.Interfaces;

import android.arch.lifecycle.MutableLiveData;

import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailerList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqReviewsList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.MultipleResource;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//DTMS? make all these LiveData done just converted to LiveData
//probably need to make sure that I change  the enqueue process?
//idk need to review

//TODO (u) this could actually be using the Observable interface for return type
public interface MovieApiInterface {
    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    /*
    @POST("/api/movies")
    Call<AaqMovie> createMovie(@Body AaqMovie aaqMovie);
    */

    //getMovies by sortParam
    @GET("/3/movie/{sortParam}")
    Call<AaqMovieList> doGetMovieList(@Path("sortParam") String sort, @Query("api_key") String apiKey, @Query("language") String language, @Query("page") String page);
//
    //https://api.themoviedb.org/3/movie/{movie_id}?api_key=<<api_key>>&language=en-US
    //testing wrapper class to accept this Response!
    //but it looks identical to AaqMovie, so maybe this will work?
    @GET("/3/movie/{id}?api_key=" + R.string.api_key+ "&language="+ R.string.lang_default)
    Call<AaqMovie> doGetMovie(@Path("id") int id);


    //url for ref:
    //https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=<<api_key>>&language=en-US
    //default values for queries
    @GET("/3/movie/{id}/videos?api_key="+ R.string.api_key+"&language="+R.string.lang_default)
    Call<AaqMovieTrailerList> doGetMovieTrailers(@Path("id") int  id);

    //GetMovieTrailers by Lang
    @GET("/3/movie/{id}/videos?api_key=" + R.string.api_key+ "&language={lang}")
    Call<AaqMovieTrailerList> doGetMovieTrailers(@Path("id") int id, @Query("language") String lang);

    ///movie/{id}/reviews
    //getReviews with English Default TODO (u) make option to modify lang_default
    @GET("/3/movie/{id}/reviews?api_key="+ R.string.api_key+"&language="+R.string.lang_default)
    Call<AaqMovieTrailerList> doGetReviewsList(@Path("id") int  id);

    //getReviews by lang
    @GET("3/movie/{id}/reviews?api_key=" + R.string.api_key + "&language={lang}")
    Call<AaqReviewsList> doGetReviewsList(@Path("id") int id, @Query("lang") String lang);

    //TODO (Explore) Headers for returning cache-state (is this exclusive to web?
    //TODO (Q) is it the server's cache being informed upon or other?
    //TODO (I?) here are cache options: https://www.tutorialspoint.com/http/http_header_fields.htm
}
