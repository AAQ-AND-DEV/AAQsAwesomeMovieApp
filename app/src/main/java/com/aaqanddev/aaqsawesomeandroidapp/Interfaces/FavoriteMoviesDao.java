package com.aaqanddev.aaqsawesomeandroidapp.Interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieList;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import java.util.List;

@Dao
public interface FavoriteMoviesDao {
    //IDK maybe this should be LiveData
    @Query("select * from AaqMovie")
    MutableLiveData<AaqMovieList> getAllFaveMovies();

    @Query("select * from AaqMovie where id= :id")
    LiveData<AaqMovie> getItemById(int id);

    @Insert(onConflict = REPLACE)
    void addFaveMovie(AaqMovie movie);

    @Delete
    void deleteMovie(AaqMovie movie);
}