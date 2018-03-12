package com.example.saidi.popularmoviesstageone;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.saidi.popularmoviesstageone.data.MovieService;
import com.example.saidi.popularmoviesstageone.data.ServiceManager;
import com.example.saidi.popularmoviesstageone.data.model.Movie;
import com.example.saidi.popularmoviesstageone.data.model.MovieList;
import com.example.saidi.popularmoviesstageone.ui.MovieGridSpacingItemDecoration;
import com.example.saidi.popularmoviesstageone.ui.MovieListClickLisener;
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

    MovieListClickLisener mMovieListClickLisener;

    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_home);
        ButterKnife.bind(this);

        mGridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,
                false);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);

        mMovieRecyclerView.addItemDecoration(
                new MovieGridSpacingItemDecoration(2, spacingInPixels, true, 0));
        mMovieListClickLisener = new MovieListClickLisener() {
            @Override
            public void onMovieClick(View view, int position, Movie movie) {
                onMovieItemClickBehavior(view, movie);
            }
        };
        getMostPopularMovies();

    }

    private void onMovieItemClickBehavior(View view, Movie movie) {
        ActivityOptions options;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(MoviesHomeActivity.this, view,
                    getBaseContext().getString(


                            R.string.picture_transition_name));
            Intent detailMovieIntent = new Intent(MoviesHomeActivity.this,
                    MovieDetailActivity.class);
            detailMovieIntent.putExtra(Constants.MOVIE, movie);
            startActivity(detailMovieIntent, options.toBundle());
        } else {
            Intent detailMovieIntent = new Intent(MoviesHomeActivity.this,
                    MovieDetailActivity.class);
            detailMovieIntent.putExtra(Constants.MOVIE, movie);
            startActivity(detailMovieIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_rated:
                getTopRatedMovies();
                return true;
            case R.id.most_popular:
                getMostPopularMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void setData(List<Movie> movies) {
        MoviesListAdapter moviesListAdapter = new MoviesListAdapter(this, movies,
                mMovieListClickLisener);
        mMovieRecyclerView.setAdapter(moviesListAdapter);
        mMovieRecyclerView.setLayoutManager(mGridLayoutManager);

    }

    private void startRecyclerViewAnimation() {
        mMovieRecyclerView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        mMovieRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i < mMovieRecyclerView.getChildCount(); i++) {
                            View v = mMovieRecyclerView.getChildAt(i);
                            v.setAlpha(0.0f);
                            v.animate().alpha(1.0f)
                                    .setDuration(300)
                                    .setStartDelay(i * 50)
                                    .start();
                        }

                        return true;
                    }
                });
    }

    private List<Movie> getMostPopularMovies() {
        final List<Movie> moviesList = new ArrayList<>();
        ServiceManager.createService(MovieService.class).getPopularMovies().enqueue(
                new Callback<MovieList>() {
                    @Override
                    public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                        setData(response.body().getMovies());
                    }

                    @Override
                    public void onFailure(Call<MovieList> call, Throwable t) {
                        onErrorMessage();
                    }
                });

        return moviesList;
    }

    private List<Movie> getTopRatedMovies() {
        final List<Movie> moviesList = new ArrayList<>();
        ServiceManager.createService(MovieService.class).getTopRatedMovies().enqueue(
                new Callback<MovieList>() {
                    @Override
                    public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                        setData(response.body().getMovies());
                    }

                    @Override
                    public void onFailure(Call<MovieList> call, Throwable t) {
                        onErrorMessage();
                    }
                });

        return moviesList;
    }

    private void onErrorMessage() {
        Snackbar errorSnackBar = Snackbar.make(findViewById(R.id.container),
                R.string.message_error, Snackbar.LENGTH_SHORT);
        errorSnackBar.setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        errorSnackBar.show();
    }


}
