package com.example.saidi.popularmoviesstageone.ui;

import android.view.View;

import com.example.saidi.popularmoviesstageone.data.model.Movie;

/**
 * Created by saidi on 01/03/2018.
 */

public interface MovieListClickLisener {

    void onMovieClick(View view, int position, Movie movie);
}
