package com.aaqanddev.aaqsawesomeandroidapp.repos;

import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.IAaqMovieRepository;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailerList;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqReviewsList;

import java.util.List;

public class AaqRepositoryFactory {
    private static IAaqMovieRepository<List<AaqMovie>> _moviesListRepo = null;
    private static IAaqMovieRepository<AaqMovie> _detailMovieRepo = null;
    private static IAaqMovieRepository<AaqMovieTrailerList> _trailersListRepo = null;
    private static  IAaqMovieRepository<AaqReviewsList> _reviewsListRepo = null;

    public static synchronized IAaqMovieRepository<AaqMovieTrailerList> getTrailersRepo(Context context){
        if(_trailersListRepo== null){
            _trailersListRepo= new
        }
    }
}
