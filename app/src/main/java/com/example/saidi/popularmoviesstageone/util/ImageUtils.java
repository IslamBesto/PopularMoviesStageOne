package com.example.saidi.popularmoviesstageone.util;

import android.net.Uri;

public class ImageUtils {


    public static String buildPosterPath(String posterPath) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185");
        String posterPathUrl = builder.build().toString();
        return posterPathUrl+posterPath;
    }

}
