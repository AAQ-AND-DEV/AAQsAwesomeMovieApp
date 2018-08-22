package com.aaqanddev.aaqsawesomeandroidapp.Db;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.FavoriteMoviesDao;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;

@Database(entities = {AaqMovie.class}, version = 1)

public abstract class FavoriteMovieDb extends RoomDatabase {

    private static FavoriteMovieDb INSTANCE;

    public static FavoriteMovieDb getDb(Context context){
        if(INSTANCE == null) {
            synchronized (FavoriteMovieDb.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteMovieDb.class,
                            "fave_db")
                            //TODO(A) addCallbacks
                            .build();
                }
                    }
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE=  null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
    //TODO(A) Swap tables method necc. w/out cursor?

    public abstract FavoriteMoviesDao faveMovieDao();
}
