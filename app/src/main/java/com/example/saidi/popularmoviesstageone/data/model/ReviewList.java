package com.example.saidi.popularmoviesstageone.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by saidi on 18/03/2018.
 */

public class ReviewList {


    @SerializedName("results")
    private List<Review> mReviewList = null;

    public List<Review> getReviewList() {
        return mReviewList;
    }

    public void setReviewList(List<Review> mReviewList) {
        this.mReviewList = mReviewList;
    }
}
