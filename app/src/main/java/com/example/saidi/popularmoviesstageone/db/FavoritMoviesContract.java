package com.example.saidi.popularmoviesstageone.db;

import android.provider.BaseColumns;

/**
 * Created by saidi on 05/04/2018.
 */

public class FavoritMoviesContract {

    public static final class FavoritMovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "favoritmovies";
        // this is movie id and not table id
        public static final String COLUMN_MOVIE_ID = "movieID";
        public static final String COLUMN_MOVIE_POSTER_PATH = "moviePosterPath";
        public static final String COLUMN_MOVIE_VOTE_COUNT = "movieVoteCount";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movieVoteAverage";
        public static final String COLUMN_MOVIE_OVERVIEW= "movieOverView";
        public static final String COLUMN_MOVIE_TITLE= "movieTitle";
        public static final String COLUMN_MOVIE_RELEASE_DATE= "movieReleaseDate";
        public static final String COLUMN_MOVIE_ADULT= "movieAdult";

    }

}
