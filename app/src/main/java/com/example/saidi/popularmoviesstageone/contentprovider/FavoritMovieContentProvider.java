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
        final SQLiteDatabase db = mFavoritMovieDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor;
        switch (match) {
            case FAVORITS_MOVIE:
                returnCursor = db.query(FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVORITS_MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                returnCursor = db.query(FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
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
        // Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mFavoritMovieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int tasksDeleted;
        switch (match) {
            case FAVORITS_MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME,
                        "movieID=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
            @Nullable String[] selectionArgs) {
        return 0;
    }
}
