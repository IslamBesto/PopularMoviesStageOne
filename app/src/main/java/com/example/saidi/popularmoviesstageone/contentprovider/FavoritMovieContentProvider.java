package com.example.saidi.popularmoviesstageone.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.saidi.popularmoviesstageone.db.FavoritMovieDbHelper;
import com.example.saidi.popularmoviesstageone.db.FavoritMoviesContract;

/**
 * Created by saidi on 11/04/2018.
 */

public class FavoritMovieContentProvider extends ContentProvider {
    public static final int FAVORITS_MOVIE = 100;
    public static final int FAVORITS_MOVIE_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavoritMovieDbHelper mFavoritMovieDbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoritMoviesContract.AUTHORITY, FavoritMoviesContract.PATH_FAVORIT,
                FAVORITS_MOVIE);
        uriMatcher.addURI(FavoritMoviesContract.AUTHORITY,
                FavoritMoviesContract.PATH_FAVORIT + "/#", FAVORITS_MOVIE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mFavoritMovieDbHelper = new FavoritMovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
            @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mFavoritMovieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case FAVORITS_MOVIE:
                long id = db.insert(FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME, null,
                        values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(
                            FavoritMoviesContract.FavoritMovieEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
            @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
            @Nullable String[] selectionArgs) {
        return 0;
    }
}
