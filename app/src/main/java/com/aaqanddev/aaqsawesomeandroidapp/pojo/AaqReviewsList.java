package com.aaqanddev.aaqsawesomeandroidapp.pojo;

//sample return{"id":141,"page":1,
//        "results":
//        [{"author":"Andres Gomez",
//        "content":"Interesting movie with several readings.\r\n\r\nAs with 2001: A Space Odissey, it is needed a reading of the actual explanation for the events to fully understand the original idea ... if you are interested in such explanation ...",
//        "id":"50d321f419c29559d80bf8fc",
//        "url":"https://www.themoviedb.org/review/50d321f419c29559d80bf8fc"}]
//          ,"total_pages":1,"total_results":1}

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AaqReviewsList {

    //region getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<AaqMovieReview> getResults() {
        return results;
    }

    public void setResults(List<AaqMovieReview> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private List<AaqMovieReview> results;

    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    @SerializedName("total_results")
    @Expose
    private int total_results;

}
