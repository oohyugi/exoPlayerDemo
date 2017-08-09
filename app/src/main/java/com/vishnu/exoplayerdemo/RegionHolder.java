package com.vishnu.exoplayerdemo;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.vishnu.exoplayerdemo.util.ExoPlayerUtils;
import com.vishnu.exoplayerdemo.util.Utils;

/**
 * Created by vishnu on 9/8/17.
 */

public class RegionHolder extends RelativeLayout {
    private static final String LOG_LABEL = "RegionHolder";
    private ImageView imageView;
    private SimpleExoPlayerView simpleExoPlayerView;
    private ViewGroup customView;

    public RegionHolder(Context context) {
        super(context);
    }

    public ImageView getImageRegion() {
        if (imageView == null) setImageRegion(new ImageView(getContext()));
        return imageView;
    }

    public void setImageRegion(ImageView imageView) {
        this.imageView = imageView;
        if (imageView != null) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(imageView);
            imageView.invalidate();
        }
    }

    public void resetImageRegion() {
        try {
            if (imageView != null) {
                imageView.setImageBitmap(null);
                imageView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Utils.logException(e, LOG_LABEL);
        }
    }

    public SimpleExoPlayerView getExoPlayerVideoRegion() {
        if (simpleExoPlayerView == null)
            setExoPlayerVideoRegion(new SimpleExoPlayerView(getContext()));
        return simpleExoPlayerView;
    }

    public void setExoPlayerVideoRegion(SimpleExoPlayerView videoView) {
        this.simpleExoPlayerView = videoView;
        if (videoView != null) {
            videoView.setUseController(false);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            videoView.setLayoutParams(params);
            addView(videoView);
            videoView.invalidate();
        }
    }

    public void resetExoPlayerVideoRegion() {
        try {
            if (simpleExoPlayerView != null) {
                ExoPlayerUtils.resetPlayerOnSimpleExoPlayerView(simpleExoPlayerView);
                simpleExoPlayerView.setBackground(null);
                simpleExoPlayerView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Utils.logException(e, LOG_LABEL);
        }
    }

    public ViewGroup getCustomRegion() {
        if (customView == null) setCustomRegion(new RelativeLayout(getContext()));
        return customView;
    }

    public void setCustomRegion(ViewGroup viewGroup) {
        this.customView = viewGroup;
        if (viewGroup != null) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

            Point point = Utils.getScreenMetrics(viewGroup.getContext());
            params.width = point.x;
            params.height = point.y;

            viewGroup.setLayoutParams(params);
            addView(customView);
            viewGroup.invalidate();
        }
    }

    public void resetCustomRegion() {
        try {
            if (customView != null) {
                customView.setBackground(null);
                customView.removeAllViews();
                customView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Utils.logException(e, LOG_LABEL);
        }
    }

    public void resetAll() {
        resetExoPlayerVideoRegion();
        resetCustomRegion();
        resetImageRegion();
    }
}
