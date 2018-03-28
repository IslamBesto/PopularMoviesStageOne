package com.example.saidi.popularmoviesstageone.data;

import com.example.saidi.popularmoviesstageone.data.model.MovieList;
import com.example.saidi.popularmoviesstageone.data.model.Review;
import com.example.saidi.popularmoviesstageone.data.model.ReviewList;
import com.example.saidi.popularmoviesstageone.data.model.Trailer;
import com.example.saidi.popularmoviesstageone.data.model.TrailerList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface MovieService {

    @GET("movie/popular")
    Call<MovieList> getPopularMovies();

    @GET("movie/top_rated")
    Call<MovieList> getTopRatedMovies();

    @GET("movie/{id}/videos")
    Call<TrailerList> getTrailerVideos(@Path("id") String movieId);

    @GET("movie/{id}/reviews")
    Call<ReviewList> getMovieReview(@Path("id") String movieId);
}
