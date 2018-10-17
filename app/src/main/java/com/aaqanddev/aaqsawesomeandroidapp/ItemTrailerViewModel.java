package com.aaqanddev.aaqsawesomeandroidapp;

import android.arch.lifecycle.ViewModel;
import android.os.Parcel;
import android.os.Parcelable;

import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovieTrailer;

public class ItemTrailerViewModel extends ViewModel implements Parcelable{

    private AaqMovieTrailer mTrailer;

    //to use when populating RV with pics from YT
    private static final String TRAILER_IMG_BASE_URL = "https:/img.youtube.com/vi/";
    private static final String TRAILER_IMG_TYPE = "/hqdefault.jpg";

    private static final String YT_URL = "http://www.youtube.com/watch?v=";

    public ItemTrailerViewModel(AaqMovieTrailer trailer){
        this.mTrailer = trailer;
    }

    public AaqMovieTrailer getTrailer(){
        return mTrailer;
    }




/* this sort of thing for factory approach
    ItemTrailerViewModel createViewModel(){
        //TODO (should be adding a factory?
        //TODO  (context not present here)
        //idk extend AndroidViewModel?
        //i think this should be in another
        //this needs to be a fragment
        //so I call it from there?
        return ViewModelProviders.of(this).get(ItemTrailerViewModel.class);
    }
    */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mTrailer, flags);
    }

    protected ItemTrailerViewModel(Parcel in) {
        this.mTrailer = in.readParcelable(AaqMovieTrailer.class.getClassLoader());
    }

    public static final Creator<ItemTrailerViewModel> CREATOR = new Creator<ItemTrailerViewModel>() {
        @Override
        public ItemTrailerViewModel createFromParcel(Parcel source) {
            return new ItemTrailerViewModel(source);
        }

        @Override
        public ItemTrailerViewModel[] newArray(int size) {
            return new ItemTrailerViewModel[size];
        }
    };
}
