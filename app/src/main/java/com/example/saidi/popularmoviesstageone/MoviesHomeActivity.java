package com.example.saidi.popularmoviesstageone;

import static com.example.saidi.popularmoviesstageone.db.FavoritMoviesContract.FavoritMovieEntry
        .CONTENT_URI;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.saidi.popularmoviesstageone.data.MovieService;
import com.example.saidi.popularmoviesstageone.data.ServiceManager;
import com.example.saidi.popularmoviesstageone.data.model.Movie;
import com.example.saidi.popularmoviesstageone.data.model.MovieList;
import com.example.saidi.popularmoviesstageone.ui.FavoritMovieListAdapter;
import com.example.saidi.popularmoviesstageone.ui.MovieGridSpacingItemDecoration;
import com.example.saidi.popularmoviesstageone.ui.MovieListClickLisener;
import com.example.saidi.popularmoviesstageone.ui.MoviesListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesHomeActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {


    private static final int FAVORIT_LOADER_ID = 0;
    @BindView(R.id.movies_rv)
    RecyclerView mMovieRecyclerView;

    MovieListClickLisener mMovieListClickLisener;

    private GridLayoutManager mGridLayoutManager;

    public static int index = -1;
    public static int top = -1;
    private String TAG = MoviesHomeActivity.class.getCanonicalName();

    private FavoritMovieListAdapter mFavoritMovieListAdapter;

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

    @Override
    protected void onPause() {
        super.onPause();
        index = mGridLayoutManager.findFirstVisibleItemPosition();
        View v = mMovieRecyclerView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - mMovieRecyclerView.getPaddingTop());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //set recyclerview position
        if (index != -1) {
            mGridLayoutManager.scrollToPositionWithOffset(index, top);
        }
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
            case R.id.favorit:
                getFavoritMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void getFavoritMovies() {
        mFavoritMovieListAdapter = new FavoritMovieListAdapter(getBaseContext(),
                mMovieListClickLisener);
        getSupportLoaderManager().initLoader(FAVORIT_LOADER_ID, null, this);
        mMovieRecyclerView.setAdapter(mFavoritMovieListAdapter);
        mMovieRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    private void setData(List<Movie> movies) {
        MoviesListAdapter moviesListAdapter = new MoviesListAdapter(this, movies,
                mMovieListClickLisener);
        mMovieRecyclerView.setAdapter(moviesListAdapter);
        mMovieRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    private void getMostPopularMovies() {
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
    }

    private void getTopRatedMovies() {
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {


            Cursor mFavoritMovies = null;

            @Override
            protected void onStartLoading() {
                if (mFavoritMovies != null) {
                    deliverResult(mFavoritMovies);
                } else {
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mFavoritMovies = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFavoritMovieListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        {
            mFavoritMovieListAdapter.swapCursor(null);
        }
    }
}
