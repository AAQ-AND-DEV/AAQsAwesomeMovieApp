package com.aaqanddev.aaqsawesomeandroidapp.Db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.aaqanddev.aaqsawesomeandroidapp.AaqMovieAppExecutors;
import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.FavoriteMoviesDao;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.DataConverter;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.converters.GenreConverter;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.converters.SpokenLangConverter;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;

@Database(entities = {AaqMovie.class}, version = 1)
//I read here: https://mobikul.com/add-typeconverters-room-database-android/
//this may be done in POJO (that's what I did)
@TypeConverters({GenreConverter.class, SpokenLangConverter.class})
public abstract class FavoriteMovieDb extends RoomDatabase {

    private static FavoriteMovieDb sINSTANCE;

    private final MutableLiveData<Boolean> mIsDbCreated = new MutableLiveData<>();

    public static FavoriteMovieDb getDb(final Context context, final AaqMovieAppExecutors executors){
        if(sINSTANCE == null) {
            synchronized (FavoriteMovieDb.class){
                if (sINSTANCE == null){
                    sINSTANCE = buildDatabase(context, executors);
                }
                    }
        }
        return sINSTANCE;
    }

    public static void destroyInstance(){
        sINSTANCE=  null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    private static FavoriteMovieDb buildDatabase(final Context appContext, final AaqMovieAppExecutors executors){
        return Room.databaseBuilder(appContext.getApplicationContext(),
                FavoriteMovieDb.class,
                "fave_db")
                //done Callback to run on executor  (seen: https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/db/AppDatabase.java)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.getmDiskIO().execute(() ->{
                            FavoriteMovieDb faveDb = FavoriteMovieDb.getDb(appContext, executors);
                            faveDb.setDbCreated();
                        });
                    }
                })
                .build();
    }

    private void setDbCreated(){
        mIsDbCreated.postValue(true);
    }
    public LiveData<Boolean> getDbCreated(){
        return mIsDbCreated;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
    //TODO(U) Swap tables method necc. w/out cursor? make update Table method?

    public abstract FavoriteMoviesDao faveMovieDao();
}
