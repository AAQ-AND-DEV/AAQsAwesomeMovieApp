package com.aaqanddev.aaqsawesomeandroidapp.pojo;

import android.arch.persistence.room.PrimaryKey;
import android.graphics.Region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Sample trailer:{"id":"533ec652c3a36854480000e0",
//        "iso_639_1":"en","iso_3166_1":"US",
//        "key":"ZXNjdrYzxys",
//        "name":"Three Colors: Red  - Miramax trailer",
//        "site":"YouTube","size":480,
//        "type":"Trailer"}

//TODO(?) do i want to implement parcelable?  I would just be passing url string,
// so i don't think so. could just use savedInstance?
public class AaqMovieTrailer {

    public AaqMovieTrailer(){}



    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;

    @SerializedName("iso_639_1")
    @Expose
    private String iso_639_1;

    @SerializedName("iso_3166_1")
    @Expose
    private String iso_3166_1;

    //key is actually the url
    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("site")
    @Expose
    private String site;

    @SerializedName("size")
    @Expose
    private int size;

    @SerializedName("type")
    @Expose
    private String type;

//region getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
//endregion

}
