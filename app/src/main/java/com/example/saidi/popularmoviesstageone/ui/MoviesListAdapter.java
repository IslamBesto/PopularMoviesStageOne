package com.example.saidi.popularmoviesstageone.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.saidi.popularmoviesstageone.R;
import com.example.saidi.popularmoviesstageone.data.model.Movie;
import com.example.saidi.popularmoviesstageone.util.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoviesListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Movie> mMovies;
    private MovieListClickLisener mListener;

    public MoviesListAdapter(Context context,
            List<Movie> movies, MovieListClickLisener listener) {
        mContext = context;
        mMovies = movies;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(mContext).inflate(R.layout.item_movie_list, parent,
                false);
        return new MovieHolder(movieView, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieHolder movieHolder = (MovieHolder) holder;
        movieHolder.setData(mMovies.get(position));
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }


    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_item_image)
        ImageView mMovieIv;

        private Movie mMovie;

        private MovieListClickLisener mListener;

        MovieHolder(View itemView, MovieListClickLisener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Movie movie) {
            String posterPathUrl = ImageUtils.buildPosterPath(movie.getPosterPath(), "w185");
            Picasso.with(mContext).load(posterPathUrl).into(mMovieIv);
            mMovie = movie;
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mListener.onMovieClick(v, getAdapterPosition(), mMovie);
            }
        }
    }
}
