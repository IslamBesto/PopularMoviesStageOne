package com.example.saidi.popularmoviesstageone.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.saidi.popularmoviesstageone.R;
import com.example.saidi.popularmoviesstageone.data.model.Movie;
import com.example.saidi.popularmoviesstageone.db.FavoritMoviesContract;
import com.example.saidi.popularmoviesstageone.util.ImageUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saidi on 15/04/2018.
 */

public class FavoritMovieListAdapter extends RecyclerView.Adapter {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;
    private MovieListClickLisener mListener;


    /**
     * Constructor for the CustomCursorAdapter that initializes the Context.
     *
     * @param context the current Context
     */
    public FavoritMovieListAdapter(Context context, MovieListClickLisener listener) {
        mContext = context;
        mListener = listener;
    }


    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View movieView = LayoutInflater.from(mContext).inflate(R.layout.item_movie_list, parent,
                false);

        return new MovieHolder(movieView, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean isAdulteBoolean = true;

        int movieIdIndex = mCursor.getColumnIndex(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ID);
        int isAdultIndex = mCursor.getColumnIndex(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ADULT);
        int movieBackDropIndex = mCursor.getColumnIndex(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_BACKDROP);
        int movieOverviewIndex = mCursor.getColumnIndex(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_OVERVIEW);
        int moviePosterIndex = mCursor.getColumnIndex(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_POSTER_PATH);
        int releaseDateIndex = mCursor.getColumnIndex(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_RELEASE_DATE);
        int movieAvreageVoteIndex = mCursor.getColumnIndex(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE);
        int movieVoteCountIndex = mCursor.getColumnIndex(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_COUNT);
        int movieTitleIndex = mCursor.getColumnIndex(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_TITLE);

        mCursor.moveToPosition(position);

        String movieId = mCursor.getString(movieIdIndex);
        String isAdulte = mCursor.getString(isAdultIndex);
        String movieBackDrop = mCursor.getString(movieBackDropIndex);
        String movieOverviw = mCursor.getString(movieOverviewIndex);
        String moviePoster = mCursor.getString(moviePosterIndex);
        String movieReleaseDate = mCursor.getString(releaseDateIndex);
        String movieAvreageVote = mCursor.getString(movieAvreageVoteIndex);
        String movieVoteCount = mCursor.getString(movieVoteCountIndex);
        String movieTitle = mCursor.getString(movieTitleIndex);

        if (isAdulte.equals("true")) {
            isAdulteBoolean = true;
        } else if (isAdulte.equals("false")) {
            isAdulteBoolean = false;
        }

        Movie movie = new Movie(movieId, moviePoster, movieVoteCount, movieAvreageVote,
                movieBackDrop, movieOverviw, movieTitle, movieReleaseDate, isAdulteBoolean);

        MoviesListAdapter.MovieHolder movieHolder = (MoviesListAdapter.MovieHolder) holder;
        movieHolder.setData(movie);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
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
