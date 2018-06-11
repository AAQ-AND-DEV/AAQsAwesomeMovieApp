package com.aaqanddev.aaqsawesomeandroidapp.Utilities;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesAPIClient {
    private static Retrofit retro = null;

    static Retrofit getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retro = new Retrofit.Builder()
                .baseUrl()//TODO insert url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retro;
    }
}
