package com.aaqanddev.aaqsawesomeandroidapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaqanddev.aaqsawesomeandroidapp.R;
import com.aaqanddev.aaqsawesomeandroidapp.Utilities.MoviesAPIClient;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRVAdapter extends  android.support.v7.widget.RecyclerView.Adapter<MovieRVAdapter.MovieViewHolder>{

        private List<AaqMovie> mMovies;
        private LayoutInflater mInflater;
        private MainRecyclerViewClickListener mOnItemClickListener;
        //private Cursor mCursor;
        private Context mContext;
        private int mId;

    public MovieRVAdapter( List<AaqMovie> movies, MainRecyclerViewClickListener onItemClickListener){

            mOnItemClickListener = onItemClickListener;

            mMovies = movies;
        }


        @NonNull
        @Override
        public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            Context parentContext = parent.getContext();
            mInflater = LayoutInflater.from(parentContext);
            View movieView = mInflater.inflate(R.layout.movies_rv_item, parent, false);
            movieView.setFocusable(true);
            movieView.setClickable(true);
            MovieViewHolder movieViewHolder = new MovieViewHolder(movieView);
            return movieViewHolder;
        }

        public  void  updateData(@NonNull List<AaqMovie> newData){
            if (mMovies!= null){
                mMovies.clear();

            }
            mMovies.addAll(newData);
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
            //mCursor.moveToPosition(position);
            //final AaqMovie movie = mMovies.get(position);
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            if (mMovies == null){
                return  0;
            }
            return mMovies.size();
        }

        //TODO (d) should I add a getItemViewType(int) override?
        //I think when I add landscape layouts, this is important

        public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            public TextView title_tv;
            public ImageView poster_iv;
            //public View layout;
            private AaqMovie currMovie;


            public MovieViewHolder(View itemView) {
                super(itemView);

                title_tv = (TextView) itemView.findViewById(R.id.item_movie_name);
                poster_iv = (ImageView) itemView.findViewById(R.id.item_iv_movie_poster);

            }

            public void bind( int pos) {
                currMovie = mMovies.get(pos);
                title_tv.setText(currMovie.getTitle());
                title_tv.setContentDescription(itemView.getResources().getString(R.string.ally_movie_title));
                //I predict this code compiles to the one following -- want to check that out
                Picasso.with(poster_iv.getContext())
                        .load(                                      //this should depend on calling activity, right?
                                MoviesAPIClient.buildMoviePosterUrl(title_tv.getContext().getResources().getString(R.string.size_poster_rv_default), currMovie.getPosterPath()).toString())
                        .fit()
                        .centerInside()
                        .placeholder(R.drawable.ic_movie_poster_paceholder)
                        .error(R.drawable.ic_error_face)
                        .into(poster_iv);
                poster_iv.setContentDescription(itemView.getResources().getString(R.string.ally_movie_poster_image));
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

                //Toast.makeText(v.getContext(),"what is this for anyway?!?entering mOnItemClickListener call",Toast.LENGTH_SHORT);
                mOnItemClickListener.onItemClick(v, this.getLayoutPosition());
            }

        }
    public List<AaqMovie> getMovies(){
        return mMovies;
    }


        public interface MainRecyclerViewClickListener {

            void onItemClick(final View view, int movieId);
        }
    }


