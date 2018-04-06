package com.example.saidi.popularmoviesstageone.util;

import android.net.Uri;

public class ImageUtils {


    public static final String HTTPS = "https";
    public static final String AUTHORITY = "image.tmdb.org";
    public static final String T = "t";
    public static final String P = "p";

    public static String buildPosterPath(String posterPath, String size) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTPS)
                .authority(AUTHORITY)
                .appendPath(T)
                .appendPath(P)
                .appendPath(size);
        String posterPathUrl = builder.build().toString();
        return posterPathUrl + posterPath;
    }
}
