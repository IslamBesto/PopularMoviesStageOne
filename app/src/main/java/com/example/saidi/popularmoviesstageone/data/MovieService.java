package com.example.saidi.popularmoviesstageone.data;

import com.example.saidi.popularmoviesstageone.data.model.Movie;
import com.example.saidi.popularmoviesstageone.data.model.MovieList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by saidi on 22/02/2018.
 */

public interface MovieService {

    @GET("movie/popular")
    Call<MovieList> getPopularMovies();

    @GET("movie/top_rated")
    Call<MovieList> getTopRatedMovies();
}
