package com.example.saidi.popularmoviesstageone.ui.viewpager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.saidi.popularmoviesstageone.R;
import com.example.saidi.popularmoviesstageone.data.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saidi on 28/03/2018.
 */

public class CardTrailerAdapter extends PagerAdapter implements CardAdapter {
    private List<CardView> mViews;
    private List<Trailer> mData;
    private float mBaseElevation;
    private Context mContext;

    public CardTrailerAdapter(Context context , List<Trailer> data) {
        mContext = context;
        mData = data;
        mViews = new ArrayList<>();
        for (Trailer datum : data) {
            mViews.add(null);
        }
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.trailer_card, container, false);
        container.addView(view);
        CardView cardView = view.findViewById(R.id.cardView);
        bind(mData.get(position), view);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        openOnYoutube(position, view);
        return view;
    }

    private void openOnYoutube(final int position, View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoLink = mData.get(position).getVideoLink();
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(videoLink));
                try {
                    mContext.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    mContext.startActivity(webIntent);
                }
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(Trailer item, View view) {
        ImageView thumbnails =  view.findViewById(R.id.thumbnails);
        Picasso.with(mContext).load(item.buildThumbnails()).into(thumbnails);

    }
}
