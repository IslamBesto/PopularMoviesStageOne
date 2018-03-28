package com.example.saidi.popularmoviesstageone.ui.viewpager;

import android.support.v7.widget.CardView;

/**
 * Created by saidi on 28/03/2018.
 */

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}

