package com.aaqanddev.aaqsawesomeandroidapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aaqanddev.aaqsawesomeandroidapp.MovieDetailActivity;
import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieRVAdapter extends  android.support.v7.widget.RecyclerView.Adapter<MovieRVAdapter.MovieViewHolder>{
        private Context mContext;
        private List<AaqMovie> mMovies;
        private LayoutInflater mInflater;

        //private AdapterView.OnItemClickListener mOnClickListener;
        private final MainRecyclerViewClickListener mOnItemClickListener;
        private Cursor mCursor;
        private int mId;
    //TODO()need to change this to be set where called
    public static final String size_poster_small = Resources.getSystem().getString(R.string.size_poster_small);
    public static final String size_poster_default = size_poster_small ;



    public MovieRVAdapter(Context context, List<AaqMovie> movies, MainRecyclerViewClickListener onItemClickListener){

            mContext = context;
            mInflater = LayoutInflater.from(context);
            mOnItemClickListener = onItemClickListener;

            mMovies = movies;

        }

        @NonNull
        @Override
        public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            mContext = parent.getContext();
            View movieView = mInflater.inflate(R.layout.movies_rv_item, parent, false);
            movieView.setFocusable(true);
            MovieViewHolder movieViewHolder = new MovieViewHolder(movieView);
            return movieViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {


            //mCursor.moveToPosition(position);
            //final AaqMovie movie = mMovies.get(position);

            holder.bind(position);



        }

        @Override
        public int getItemCount() {
            return mMovies.size();
        }

        public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            public TextView title_tv;
            public ImageView poster_iv;
            //public View layout;
            private AaqMovie currMovie;


            public MovieViewHolder(View itemView) {
                super(itemView);

                title_tv = (TextView) itemView.findViewById(R.id.item_movie_name);
                poster_iv = (ImageView) itemView.findViewById(R.id.item_iv_movie_poster);
                //itemView.setOnClickListener(this);
            }

            public void bind( int pos) {
                currMovie = mMovies.get(pos);
                title_tv.setText(currMovie.getTitle());
//I predict this code compiles to the following
                Picasso.with(poster_iv.getContext())
                        .load(                                      //this should depend on calling activity, right?
                                MoviesAPIClient.buildMoviePosterUrl(size_poster_default, currMovie.getPosterPath()).toString())
                        .fit().centerCrop()
                        .placeholder(R.drawable.ic_movie_poster_paceholder)
                        .error(R.drawable.ic_error_face)
                        .into(poster_iv);
  /*
                Picasso.Builder builder = new Picasso.Builder(mContext);
                builder.downloader(new OkHttpDownloader(mContext));
                builder.build().load(mMovie.getPosterPath())
                        .placeholder(R.drawable.ic_movie_poster_paceholder)
                        .error(R.drawable.ic_error_face)
                        .into(poster_iv);
    */
                itemView.setOnClickListener(this);


            }
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"this works!",Toast.LENGTH_SHORT);
                //TODO (I) direct to detailActivity
                Intent detailIntent = new Intent(v.getContext(), MovieDetailActivity.class);
               //TODO not sure what this is for? -- isn't this recursive?
                //mOnItemClickListener.onClick(currMovie.getId());
                detailIntent.putExtra("myMovie", currMovie);


            }
        }

        public interface MainRecyclerViewClickListener {

            void onClick(int movieId);
        }
    }


