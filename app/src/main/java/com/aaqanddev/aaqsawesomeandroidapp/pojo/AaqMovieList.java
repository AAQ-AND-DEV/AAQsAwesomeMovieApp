package com.aaqanddev.aaqsawesomeandroidapp.pojo;

import android.arch.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

//TODO  (Q) does this make sense to do? extend arraylist?  (may  change to linkedList
//and add some method of sorting the list that practices using linked list (TODO(F))
public class AaqMovieList extends ViewModel {

    @SerializedName("page")
    @Expose
    private Integer page;

    private Integer perPage = 20;

    @SerializedName("total_results")
    @Expose
    private Integer total;

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("results")
    @Expose
    private List<AaqMovie> results;

    public List<AaqMovie> getMovies(){
        return results;
    }

    /*
    //TODO (?) make a setMovies here, for use in the Observer?
    //actually this will be
    //TODO(Q) taken care of by setValue(), via LiveData right?
    public void setMovies(AaqMovieList newMovies){
        this.results = newMovies.toList(newMovies);
    }
    */

//region getters and setters
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
//endregion

    //TODO (Q-R) is this super goofy?
    private List<AaqMovie> toList(AaqMovieList movieList){
        return movieList.results;
    }
}

//}
