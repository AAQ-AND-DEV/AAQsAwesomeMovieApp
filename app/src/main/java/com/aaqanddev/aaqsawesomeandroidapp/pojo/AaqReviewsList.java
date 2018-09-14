package com.aaqanddev.aaqsawesomeandroidapp.pojo;

//sample return{"id":141,"page":1,
//        "results":
//        [{"author":"Andres Gomez",
//        "content":"Interesting movie with several readings.\r\n\r\nAs with 2001: A Space Odissey, it is needed a reading of the actual explanation for the events to fully understand the original idea ... if you are interested in such explanation ...",
//        "id":"50d321f419c29559d80bf8fc",
//        "url":"https://www.themoviedb.org/review/50d321f419c29559d80bf8fc"}]
//          ,"total_pages":1,"total_results":1}

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aaqanddev.aaqsawesomeandroidapp.AaqMovieApp;
import com.aaqanddev.aaqsawesomeandroidapp.repos.AaqMovieRepo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AaqReviewsList extends ViewModel {

    AaqMovieRepo repo;

    public AaqReviewsList (){
        repo = AaqMovieApp.getRepo()
    }
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

    //I have this labeled getResults now
    public LiveData<List<AaqMovieReview>> getResults() {

        if (results != null) {
            return results;
        }
        //DTMS?
        else {

        }
    }

    public void setResults(MutableLiveData<List<AaqMovieReview>> results) {
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
    private MutableLiveData<List<AaqMovieReview>> results;

    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    @SerializedName("total_results")
    @Expose
    private int total_results;

}
