package com.vishnu.exoplayerdemo.adapters;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vishnu.exoplayerdemo.PlaybackHandler;
import com.vishnu.exoplayerdemo.RegionHolder;
import com.vishnu.exoplayerdemo.pojos.PlaylistItem;

/**
 * Created by vishnu on 9/8/17.
 */

public class ImageItemAdapter extends ItemAdapter {
    public void handleItem(PlaybackHandler playbackHandler, RegionHolder regionHolder, PlaylistItem item) {
        ImageView imageView = regionHolder.getImageRegion();
        imageView.setVisibility(View.VISIBLE);
        Glide.with(regionHolder.getContext())
                .load(item.path)
                .into(imageView);

        playbackHandler.setItemsAsCompleted(item);
        playbackHandler.sendMessageDelayed(playbackHandler.obtainMessage(PlaybackHandler.NEXT), item.durationInMills);
    }

    @Override
    public void stopPlayback() {

    }

}
