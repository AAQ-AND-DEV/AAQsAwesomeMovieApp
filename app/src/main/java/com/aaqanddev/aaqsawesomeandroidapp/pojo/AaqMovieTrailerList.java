package com.aaqanddev.aaqsawesomeandroidapp.pojo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//Sample return
//{"id":110,
//        "results":
//        [{"id":"533ec652c3a36854480000e0",
//        "iso_639_1":"en","iso_3166_1":"US",
//        "key":"ZXNjdrYzxys",
//        "name":"Three Colors: Red  - Miramax trailer",
//        "site":"YouTube","size":480,
//        "type":"Trailer"},
//        {"id":"533ec652c3a36854480000e1",
//        "iso_639_1":"en",
//        "iso_3166_1":"US",
//        "key":"h8NU3EYTbFg",
//        "name":"Three Reasons: Three Colors: Red",
//        "site":"YouTube",
//        "size":720,
//        "type":"Featurette"},]}
//

public class AaqMovieTrailerList extends ViewModel {

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("results")
        @Expose
        private MutableLiveData<List<AaqMovieTrailer>> results;

        public LiveData<List<AaqMovieTrailer>> getMovieTrailers(){
            return results;
        }

        //for testing purposes
        public String trailersToString(List<AaqMovieTrailer> trailers){
            StringBuilder res = new StringBuilder();
            for (AaqMovieTrailer trailer: trailers) {
                res.append(trailer.getId());
                res.append("\t" + trailer.getKey());
                res.append("\t" + trailer.getName());

            }
            return res.toString();
        }
}
