package com.aaqanddev.aaqsawesomeandroidapp.Interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import java.util.List;

@Dao
public interface FavoriteMoviesDao {
    @Query("select * from AaqMovie")
    LiveData<List<AaqMovie>> getAllFaveMovies();

    @Query("select * from AaqMovie where id= :id")
    AaqMovie getItemById(String id);

    @Insert(onConflict = REPLACE)
    void addFaveMovie(AaqMovie movie);

    @Delete
    void deleteMovie(AaqMovie movie);
}
