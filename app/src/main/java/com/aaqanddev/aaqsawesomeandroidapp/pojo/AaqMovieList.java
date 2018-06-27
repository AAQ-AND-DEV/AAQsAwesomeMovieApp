package com.aaqanddev.aaqsawesomeandroidapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AaqMovieList  {

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


}

//}
