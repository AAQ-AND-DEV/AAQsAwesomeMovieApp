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

    //http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    public static final String base_Image_auth = "image.tmdb.org";
    //public static final Uri buildBasePosterUri = ;
    public static final String sub_path_poster_1 = "t";
    public static final String sub_path_poster_2 ="p";



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

    //should this return a url or a string?
    public static URL buildMoviePosterUrl(String posterSize, String posterPath){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(base_Image_auth)
                .appendPath(sub_path_poster_1)
                .appendPath(sub_path_poster_2)
                .appendPath(posterSize)
                .appendEncodedPath(posterPath);

        Uri fetchUri = builder.build();

        URL fetch_url = null;
        Log.d("poster call uri: ", fetchUri.toString());
        try{
            fetch_url = new URL(fetchUri.toString());
            Log.d("API ", fetch_url.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return fetch_url;
    }
}
