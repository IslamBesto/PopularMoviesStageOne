package com.example.saidi.popularmoviesstageone.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by saidi on 06/04/2018.
 */

public class FavoritMovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorit.db";
    private static final int DATABASE_VERSION = 1;

    public FavoritMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORIT_MOVIE_TABLE = "CREATE TABLE " +
                FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME + "(" +
                FavoritMoviesContract.FavoritMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE + " TEXT NOT NULL, " +
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_COUNT + "  TEXT NOT NULL, " +
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ADULT + " TEXT NOT NULL, " +
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_BACKDROP + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_FAVORIT_MOVIE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
