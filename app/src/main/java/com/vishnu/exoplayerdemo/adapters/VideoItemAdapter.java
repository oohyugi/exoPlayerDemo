package com.vishnu.exoplayerdemo.adapters;

import android.view.View;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.vishnu.exoplayerdemo.PlaybackHandler;
import com.vishnu.exoplayerdemo.RegionHolder;
import com.vishnu.exoplayerdemo.pojos.PlaylistItem;
import com.vishnu.exoplayerdemo.util.ExoPlayerUtils;

/**
 * Created by vishnu on 9/8/17.
 */

public class VideoItemAdapter extends ItemAdapter {

    public void handleItem(PlaybackHandler playbackHandler, RegionHolder regionHolder, PlaylistItem item) {
        SimpleExoPlayerView videoView = regionHolder.getExoPlayerVideoRegion();
        videoView.setVisibility(View.VISIBLE);
        SimpleExoPlayer simpleExoPlayer = ExoPlayerUtils.getNewExoPlayerInstance(regionHolder.getContext());
        ExoPlayerUtils.setSourceForExoPlayer(regionHolder.getContext(), simpleExoPlayer, item.path, false);
        simpleExoPlayer.setPlayWhenReady(true);
        videoView.setPlayer(simpleExoPlayer);

        playbackHandler.setItemsAsCompleted(item);
        playbackHandler.sendMessageDelayed(playbackHandler.obtainMessage(PlaybackHandler.NEXT), 30000);
    }

    @Override
    public void stopPlayback() {

    }

}
