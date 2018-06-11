package com.aaqanddev.aaqsawesomeandroidapp.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultipleResource {
    @SerializedName("page")
    public Integer page;
    @SerializedName("per_page")
    public  Integer perPage;
    @SerializedName("total")
    public Integer total;
    @SerializedName("total_pages")
    public Integer totalPages;
    @SerializedName("data")
    public List<Datum> data = null;

    public class Datum{
        @SerializedName("id")
        public Integer id;
        @SerializedName("title")
        public String title;
        @SerializedName("release_date")
        public String relaseDate;
        @SerializedName("poster_path")
        public String posterPath;
        @SerializedName("overview")
        public String overview;
        @SerializedName("vote_average")
        public Double voteAverage;
    }
}
