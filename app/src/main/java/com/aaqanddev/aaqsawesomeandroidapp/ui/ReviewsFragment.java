package com.aaqanddev.aaqsawesomeandroidapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaqanddev.aaqsawesomeandroidapp.R;

import View.OnClickListener;

public class ReviewsFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews_rv, container, false);

        //idk should I grab the scrollView?  I don't think so...
        //get RecyclerView
        RecyclerView trailers_rv = (RecyclerView) rootView.findViewById(R.id.reviews_r_v);

        //should  I grab trailers from savedInstanceState?
        //ALSO add to onSavedInstanceState trailers and reviews!

    }

    @Override
    public void onClick(View v) {

    }
}
