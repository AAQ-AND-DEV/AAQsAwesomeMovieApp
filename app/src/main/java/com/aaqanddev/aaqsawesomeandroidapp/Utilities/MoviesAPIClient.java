package com.aaqanddev.aaqsawesomeandroidapp.Utilities;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.aaqanddev.aaqsawesomeandroidapp.BuildConfig;
import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class MoviesAPIClient {
    private static Retrofit retro = null;
    public static final String base_URL_movieDB = "https://api.themoviedb.org/3/";
    public static final Uri buildBaseUri = Uri.parse(base_URL_movieDB);

    public static final String base_Image_Url = "https://image.tmdb.org/t/p/";
    public static final Uri buildBasePosterUri = Uri.parse(base_Image_Url);



    public static Retrofit.Builder getClientBuilder(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG){
            okClientBuilder.addInterceptor(interceptor);
        }

        //putting in to ignore the isFavorite variable
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Retrofit.Builder retroMovieBuilder = new Retrofit.Builder()
                .baseUrl(base_URL_movieDB)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okClientBuilder.build());

        return retroMovieBuilder;
    }


    public static URL buildMoviePosterUrl(String posterSize, String posterPath){
        Uri fetchUri = buildBasePosterUri.buildUpon()
                .path(posterSize)
                .path(posterPath)
                .build();

        URL fetch_url = null;

        try{
            fetch_url = new URL(fetchUri.toString());
            Log.d("API ", fetch_url.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return fetch_url;
    }
}
