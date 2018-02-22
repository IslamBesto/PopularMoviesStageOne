package com.example.saidi.popularmoviesstageone.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by saidi on 22/02/2018.
 *
 */

public class Movie {
    @SerializedName("id")
    private String mMovieId;

    @SerializedName("poster_path")
    private String mPosterPath;

    public Movie(String movieId, String posterPath) {

        mMovieId = movieId;
        mPosterPath = posterPath;
    }

    public String getMovieId() {
        return mMovieId;
    }

    public void setMovieId(String movieId) {
        mMovieId = movieId;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }
}
