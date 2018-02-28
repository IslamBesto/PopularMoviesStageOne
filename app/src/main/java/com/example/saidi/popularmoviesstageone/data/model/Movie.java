package com.example.saidi.popularmoviesstageone.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by saidi on 22/02/2018.
 */

public class Movie implements Serializable{

    @SerializedName("id")
    private String mMovieId;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("vote_count")
    private String mVoteCount;

    @SerializedName("vote_average")
    private String mVoteAverage;

    @SerializedName("backdrop_path")
    private String mBackdropPath;

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("adult")
    private boolean mAdult;

    public Movie(String movieId, String posterPath, String voteCount, String voteAverage,
            String backdropPath, String overview, String title, String releaseDate, boolean adult) {
        mMovieId = movieId;
        mPosterPath = posterPath;
        mVoteCount = voteCount;
        mVoteAverage = voteAverage;
        mBackdropPath = backdropPath;
        mOverview = overview;
        mTitle = title;
        mReleaseDate = releaseDate;
        mAdult = adult;
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

    public String getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(String voteCount) {
        mVoteCount = voteCount;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public boolean isAdult() {
        return mAdult;
    }

    public void setAdult(boolean adult) {
        mAdult = adult;
    }
}
