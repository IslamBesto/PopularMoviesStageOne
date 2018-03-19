package com.example.saidi.popularmoviesstageone.ui.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.saidi.popularmoviesstageone.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saidi on 17/03/2018.
 */

public class ReviewView extends FrameLayout {
    @BindView(R.id.review_text)
    TextView mReviewTv;

    public ReviewView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ReviewView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReviewView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ReviewView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    protected void init(Context context) {
        LayoutInflater.from(context).inflate(getLayoutId(), this);
        ButterKnife.bind(this);
    }

    public int getLayoutId() {
        return R.layout.review_view;
    }

    /**
     * Set review text
     *
     * @param reviewText : text to set
     */
    public void setReviewText(String reviewText) {
        mReviewTv.setText(reviewText);
    }
}
