package com.example.saidi.popularmoviesstageone.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public MoviesListAdapter(Context context,
            List<Movie> movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(mContext).inflate(R.layout.item_movie_list, parent,
                false);
        return new MovieHolder(movieView);
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


    public class MovieHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_item_image)
        ImageView mMovieIv;


        MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Movie movie) {
            String posterPathUrl = ImageUtils.buildPosterPath(movie.getPosterPath());
            Picasso.with(mContext).load(posterPathUrl).into(mMovieIv);
        }
    }
}
