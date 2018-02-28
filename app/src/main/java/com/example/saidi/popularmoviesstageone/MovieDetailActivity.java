package com.example.saidi.popularmoviesstageone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saidi.popularmoviesstageone.data.model.Movie;
import com.example.saidi.popularmoviesstageone.util.ImageUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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
                .into(mPosterIv);

    }
}
