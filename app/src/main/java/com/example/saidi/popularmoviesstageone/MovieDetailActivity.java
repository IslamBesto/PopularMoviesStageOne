package com.example.saidi.popularmoviesstageone;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saidi.popularmoviesstageone.data.MovieService;
import com.example.saidi.popularmoviesstageone.data.ServiceManager;
import com.example.saidi.popularmoviesstageone.data.model.Movie;
import com.example.saidi.popularmoviesstageone.data.model.Review;
import com.example.saidi.popularmoviesstageone.data.model.ReviewList;
import com.example.saidi.popularmoviesstageone.data.model.Trailer;
import com.example.saidi.popularmoviesstageone.data.model.TrailerList;
import com.example.saidi.popularmoviesstageone.db.FavoritMoviesContract;
import com.example.saidi.popularmoviesstageone.ui.custom.ReviewView;
import com.example.saidi.popularmoviesstageone.ui.viewpager.CardTrailerAdapter;
import com.example.saidi.popularmoviesstageone.ui.viewpager.ShadowTransformer;
import com.example.saidi.popularmoviesstageone.util.ImageUtils;
import com.example.saidi.popularmoviesstageone.util.PaletteUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.saidi.popularmoviesstageone.db.FavoritMoviesContract.FavoritMovieEntry.CONTENT_URI;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.backdrop_iv)
    ImageView mBackdropIv;

    @BindView(R.id.poster_iv)
    ImageView mPosterIv;

    @BindView(R.id.title_tv)
    TextView mTitleTv;

    @BindView(R.id.release_date_tv)
    TextView mReleaseDateTv;

    @BindView(R.id.vote_count_tv)
    TextView mVoteCountTv;

    @BindView(R.id.vote_average_tv)
    TextView mVoteAverageTv;

    @BindView(R.id.overview_tv)
    TextView mOverviewTv;

    @BindView(R.id.adult_tv)
    TextView mAdultTv;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.background_view)
    View mBackgroundView;

    @BindView(R.id.backdrop_container)
    FrameLayout mBackdropContainer;

    @BindView(R.id.tv_detail_gradient_picture)
    ImageView mGradientView;

    @BindView(R.id.container)
    LinearLayout mContainer;

    @BindView(R.id.trailer_vp)
    ViewPager mTrailerVp;

    @BindView(R.id.review_label)
    TextView mReviewLabel;

    @BindView(R.id.favorit)
    ImageView mFavorit;

    private boolean mIsImageAnimated = false;
    private boolean mIsFavorit = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        final Movie movie = (Movie) intent.getSerializableExtra(Constants.MOVIE);
        final String movieId = movie.getMovieId();
        getReviews(movieId);
        getTrailers(movieId);
        populateUI(movie);
        isFavoritMovie(movie);
        mFavorit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (mIsFavorit) {
                    setFavoritStatus(false);
                    deleteMovieFromDb(movie);
                } else {
                    setFavoritStatus(true);
                    insertMovieInDb(movie);
                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void isFavoritMovie(Movie movie) {
        Uri uri = CONTENT_URI;
        uri = uri.buildUpon()
                .appendEncodedPath(movie.getMovieId())
                .build();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        if (cursor.getCount() > 0) {
            setFavoritStatus(true);
        } else {
            setFavoritStatus(false);
        }
    }

    /**
     * set favorit icon status
     *
     * @param status
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setFavoritStatus(boolean status) {
        if (status) {
            mIsFavorit = status;
            mFavorit.setImageDrawable(getDrawable(R.drawable.ic_favorite));
        } else {
            mIsFavorit = status;
            mFavorit.setImageDrawable(getDrawable(R.drawable.ic_favorite_border));
        }

    }

    /**
     * delete movie from database
     *
     * @param movie
     */
    private void deleteMovieFromDb(Movie movie) {
        Uri uri = CONTENT_URI;
        uri = uri.buildUpon()
                .appendEncodedPath(movie.getMovieId())
                .build();
        int idDeleted = getContentResolver().delete(uri, null, null);
        Toast.makeText(MovieDetailActivity.this, Integer.toString(idDeleted), Toast.LENGTH_SHORT).show();
    }

    /**
     * Insert movie details into sqlite database
     *
     * @param movie
     */
    private void insertMovieInDb(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_TITLE,
                movie.getTitle());
        contentValues.put(
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ADULT,
                movie.isAdult());
        contentValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ID,
                movie.getMovieId());
        contentValues.put(
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_OVERVIEW,
                movie.getOverview());
        contentValues.put(
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_POSTER_PATH,
                movie.getPosterPath());
        contentValues.put(
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_BACKDROP,
                movie.getBackdropPath());
        contentValues.put(
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                movie.getReleaseDate());
        contentValues.put(
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE,
                movie.getVoteAverage());
        contentValues.put(
                FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_COUNT,
                movie.getVoteCount());

        Uri uri = getContentResolver().insert(
                CONTENT_URI, contentValues);
        if (uri != null) {
            Toast.makeText(MovieDetailActivity.this, uri.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to populate UI with data coming from server
     * @param movie
     */
    private void populateUI(Movie movie) {
        mTitleTv.setText(movie.getTitle());
        mOverviewTv.setText(movie.getOverview());
        mVoteAverageTv.setText(movie.getVoteAverage());
        mVoteCountTv.setText(new StringBuilder().append(movie.getVoteCount()).append(" ").append(
                getString(R.string.votes)).toString());
        mReleaseDateTv.setText(movie.getReleaseDate());

        mAdultTv.setText(!movie.isAdult() ? getString(R.string.family) : getString(R.string.adult));

        String backDropImagePath = ImageUtils.buildPosterPath(movie.getBackdropPath(),
                Constants.IMAGE_SIZE_W500);
        Picasso.with(this)
                .load(backDropImagePath)
                .into(mBackdropIv);

        String posterPath = ImageUtils.buildPosterPath(movie.getPosterPath(),
                Constants.IMAGE_SIZE_W185);
        Picasso.with(this)
                .load(posterPath)
                .into(mPosterIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap original = ((BitmapDrawable) mPosterIv.getDrawable()).getBitmap();
                        int color = Palette.from(original).generate().getDominantColor(Color.BLACK);
                        color = PaletteUtils.darker(color, (float) 0.3);
                        setPictureColor(color);
                        setPictureViewAnimation();
                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    /**
     * Set color on items from main picture color
     *
     * @param color main picture color
     */
    protected void setPictureColor(int color) {
        mBackgroundView.setBackgroundColor(color);
        mGradientView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        mBackgroundView.animate().setDuration(200).alpha(1f);
    }

    /**
     * Animate picture view smoothly
     */
    private void setPictureViewAnimation() {
        if (!mIsImageAnimated) {
            mIsImageAnimated = true;
            mPosterIv.setScaleX(1.02f);
            mPosterIv.setScaleY(1.02f);
            mPosterIv.animate().scaleX(1).scaleY(1)
                    .setDuration(500).setInterpolator(new DecelerateInterpolator());
            mBackdropContainer.animate().alpha(1).setDuration(800)
                    .setInterpolator(new DecelerateInterpolator()).setStartDelay(200);
        }
    }

    private void getReviews(String movieId) {
        ServiceManager.createService(MovieService.class).getMovieReview(movieId).enqueue(
                new retrofit2.Callback<ReviewList>() {
                    @Override
                    public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                        setReviewText(response.body().getReviewList());
                    }

                    @Override
                    public void onFailure(Call<ReviewList> call, Throwable t) {

                    }
                });
    }

    private void getTrailers(String movieId) {
        ServiceManager.createService(MovieService.class).getTrailerVideos(movieId).enqueue(
                new retrofit2.Callback<TrailerList>() {
                    @Override
                    public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                        setTrailer(response.body().getTrailers());
                    }

                    @Override
                    public void onFailure(Call<TrailerList> call, Throwable t) {
                        Toast.makeText(MovieDetailActivity.this, "FAilure",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setReviewText(List<Review> reviews) {
        if (reviews.size() > 0) {

            for (Review review : reviews) {
                ReviewView reviewView = new ReviewView(this);
                reviewView.setReviewText(review.getContent());
                mContainer.addView(reviewView);
            }
        } else {
            mReviewLabel.setVisibility(View.GONE);
        }
    }

    public void setTrailer(List<Trailer> trailer) {
        CardTrailerAdapter cardAdapter = new CardTrailerAdapter(getBaseContext(), trailer);
        ShadowTransformer shadowTransformer = new ShadowTransformer(mTrailerVp, cardAdapter);
        mTrailerVp.setAdapter(cardAdapter);
        mTrailerVp.setPageTransformer(false, shadowTransformer);
        mTrailerVp.setOffscreenPageLimit(3);

    }
}
