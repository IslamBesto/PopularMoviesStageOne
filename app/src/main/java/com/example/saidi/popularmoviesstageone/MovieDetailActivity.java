package com.example.saidi.popularmoviesstageone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;

import com.example.saidi.popularmoviesstageone.data.model.Movie;
import com.example.saidi.popularmoviesstageone.util.ImageUtils;
import com.example.saidi.popularmoviesstageone.util.PaletteUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private boolean mIsImageAnimated = false;

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
        Movie movie = (Movie) intent.getSerializableExtra(Constants.MOVIE);
        populateUI(movie);
    }

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
}
