package com.vishnu.exoplayerdemo.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.vishnu.exoplayerdemo.PlaybackHandler;
import com.vishnu.exoplayerdemo.RegionHolder;
import com.vishnu.exoplayerdemo.pojos.PlaylistItem;
import com.vishnu.exoplayerdemo.pojos.Region;
import com.vishnu.exoplayerdemo.pojos.SmartTemplateData;
import com.vishnu.exoplayerdemo.util.ExoPlayerUtils;
import com.vishnu.exoplayerdemo.util.Utils;

/**
 * Created by vishnu on 9/8/17.
 */

public class SmartTemplateItemAdapter extends ItemAdapter {
    private static final String LOG_LABEL = "SmartTemplateItemAdapter";
    private SimpleExoPlayerView simpleExoPlayerView;

    public void handleItem(PlaybackHandler playbackHandler, RegionHolder regionHolder, PlaylistItem item) {
        ViewGroup viewGroup = regionHolder.getCustomRegion();
        viewGroup.setVisibility(View.VISIBLE);

        SmartTemplateData data = Utils.getDefaultSmartTemplateData();
        for (Region region : data.regions) {
            createRegions(viewGroup, region);
        }

        playbackHandler.setItemsAsCompleted(item);
        playbackHandler.sendMessageDelayed(playbackHandler.obtainMessage(PlaybackHandler.NEXT), item.durationInMills);
    }

    private void createRegions(ViewGroup viewGroup, Region region) {
        switch (region.type) {
            case "VIDEO":
                createVideoRegion(viewGroup, region);
                break;
            case "IMAGE":
                createImageRegion(viewGroup, region);
                break;
        }
    }

    private void createVideoRegion(ViewGroup viewGroup, Region region) {
        simpleExoPlayerView = new SimpleExoPlayerView(viewGroup.getContext());
        simpleExoPlayerView.setUseController(false);
        setLayoutParams(viewGroup, region, simpleExoPlayerView);
        SimpleExoPlayer player = ExoPlayerUtils.getNewExoPlayerInstance(viewGroup.getContext());
        player.setPlayWhenReady(true);
        ExoPlayerUtils.setSourceForExoPlayer(viewGroup.getContext(), player, region.path, true);
        simpleExoPlayerView.setPlayer(player);
        viewGroup.addView(simpleExoPlayerView);
    }

    private void createImageRegion(ViewGroup viewGroup, Region region) {
        ImageView imageView = new ImageView(viewGroup.getContext());
        setLayoutParams(viewGroup, region, imageView);
        Glide.with(viewGroup.getContext())
                .load(region.path)
                .into(imageView);
        viewGroup.addView(imageView);
    }

    private void setLayoutParams(ViewGroup viewGroup, Region region, View view) {
        int layoutWidth = viewGroup.getLayoutParams().width;
        int layoutHeight = viewGroup.getLayoutParams().height;

        int left = (int) (layoutWidth * region.left / 100);
        int top = (int) (layoutHeight * region.top / 100);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        params.setMargins(left, top, 1, 1);

        params.width = (int) (layoutWidth * region.width / 100);
        params.height = (int) (layoutHeight * region.height / 100);

        view.setLayoutParams(params);
    }

    @Override
    public void stopPlayback() {
        if (simpleExoPlayerView != null) {
            ExoPlayerUtils.resetPlayerOnSimpleExoPlayerView(simpleExoPlayerView);
        }
    }
}
