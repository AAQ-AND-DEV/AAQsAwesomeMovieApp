package com.aaqanddev.aaqsawesomeandroidapp.pojo;

//reference:
//      {"author":"Andres Gomez",
////        "content":"Interesting movie with several readings.\r\n\r\nAs with 2001: A Space Odissey, it is needed a reading of the actual explanation for the events to fully understand the original idea ... if you are interested in such explanation ...",
////        "id":"50d321f419c29559d80bf8fc",
////        "url":"https://www.themoviedb.org/review/50d321f419c29559d80bf8fc"}


import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AaqMovieReview {

    @SerializedName("author")
    @Expose
    private String author;

    //region getters and setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    //endregion

    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("url")
    @Expose
    private String url;

}
