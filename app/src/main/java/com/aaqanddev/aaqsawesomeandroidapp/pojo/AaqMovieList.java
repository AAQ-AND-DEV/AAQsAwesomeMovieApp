package com.aaqanddev.aaqsawesomeandroidapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AaqMovieList  {

    private List<AaqMovie> results = new ArrayList<>();
    /*
    @SerializedName("page")
    public Integer page;
    @SerializedName("per_page")
    public  Integer perPage;
    @SerializedName("total")
    public Integer total;
    @SerializedName("total_pages")
    public Integer totalPages;
    */

    public List<AaqMovie> getMovies(){
        return results;
    }

}
//
//    @SerializedName("data")
//    public List<Datum> data = null;
//
//    public class Datum{
//
//        @SerializedName("adult")
//        public Boolean adult;
//        @SerializedName("backdrop_path")
//        public String backdropPath;
//        /*
//        @SerializedName("belongs_to_collection")
//        @Expose
//        private Object belongsToCollection;
//        */
//        @SerializedName("budget")
//        public Integer budget;
//        @SerializedName("genres")
//        public List<Genre> genres = null;
//        /*
//        @SerializedName("homepage")
//        @Expose
//        private Object homepage;
//        */
//        @SerializedName("id")
//        public Integer id;
//        @SerializedName("imdb_id")
//        public String imdbId;
//        @SerializedName("original_language")
//        public String originalLanguage;
//        @SerializedName("original_title")
//        public String originalTitle;
//        @SerializedName("overview")
//        public String overview;
//        @SerializedName("popularity")
//        public Double popularity;
//        @SerializedName("poster_path")
//        public String posterPath;
//        /*
//        @SerializedName("production_companies")
//        @Expose
//        private List<ProductionCompany> productionCompanies = null;
//        @SerializedName("production_countries")
//        @Expose
//        private List<ProductionCountry> productionCountries = null;
//        */
//        @SerializedName("release_date")
//        public String releaseDate;
//        @SerializedName("revenue")
//        public Integer revenue;
//        @SerializedName("runtime")
//        public Integer runtime;
//        @SerializedName("spoken_languages")
//        public List<SpokenLanguage> spokenLanguages = null;
//        @SerializedName("status")
//        public String status;
//        @SerializedName("tagline")
//        public String tagline;
//        @SerializedName("title")
//        public String title;
//        @SerializedName("video")
//        public Boolean video;
//        @SerializedName("vote_average")
//        public Double voteAverage;
//        @SerializedName("vote_count")
//        public Integer voteCount;
//
//    }
//}
