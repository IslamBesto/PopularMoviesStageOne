package com.example.saidi.popularmoviesstageone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.saidi.popularmoviesstageone.data.MovieService;
import com.example.saidi.popularmoviesstageone.data.ServiceManager;
import com.example.saidi.popularmoviesstageone.data.model.Movie;
import com.example.saidi.popularmoviesstageone.data.model.MovieList;
import com.example.saidi.popularmoviesstageone.ui.MovieGridSpacingItemDecoration;
import com.example.saidi.popularmoviesstageone.ui.MoviesListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesHomeActivity extends AppCompatActivity {

    @BindView(R.id.movies_rv)
    RecyclerView mMovieRecyclerView;

    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_home);
        ButterKnife.bind(this);

         mGridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,
                false);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);

         mMovieRecyclerView.addItemDecoration(new MovieGridSpacingItemDecoration(2, spacingInPixels, true, 0));
        loadData();

    }

    private void setData(List<Movie> movies) {
        MoviesListAdapter moviesListAdapter = new MoviesListAdapter(this, movies);
        mMovieRecyclerView.setAdapter(moviesListAdapter);

        mMovieRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    private List<Movie> loadData() {
        final List<Movie> moviesList = new ArrayList<>();
        ServiceManager.createService(MovieService.class).getPopularMovies().enqueue(
                new Callback<MovieList>() {
                    @Override
                    public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                        setData(response.body().getMovies());
                    }

                    @Override
                    public void onFailure(Call<MovieList> call, Throwable t) {

                    }
                });

        return moviesList;
    }


}
