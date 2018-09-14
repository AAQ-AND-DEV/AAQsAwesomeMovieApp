package com.aaqanddev.aaqsawesomeandroidapp;

import android.app.Application;

import com.aaqanddev.aaqsawesomeandroidapp.Db.FavoriteMovieDb;
import com.aaqanddev.aaqsawesomeandroidapp.repos.AaqMovieRepo;

public class AaqMovieApp extends Application {

    private AaqMovieAppExecutors aAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        aAppExecutors = new AaqMovieAppExecutors();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    // DTMS? I don't have a problem keeping this in memory I don't think
    public FavoriteMovieDb getDatabase() {
        return FavoriteMovieDb.getDb(this , aAppExecutors);
    }

    //in this case, I think I want to accept a context, so the Repo does not
    //stay in mem
    //but I think this is a problem (accepting a context into the Application class)
    //IDK using this done here ok?
    public AaqMovieRepo getRepository() {
        return AaqMovieRepo.getInstance(this, aAppExecutors);
    }

    public AaqMovieAppExecutors getAppExecutors() {
        return aAppExecutors;
    }
}
