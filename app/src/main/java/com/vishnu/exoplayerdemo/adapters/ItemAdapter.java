package com.vishnu.exoplayerdemo.adapters;

import com.vishnu.exoplayerdemo.PlaybackHandler;
import com.vishnu.exoplayerdemo.RegionHolder;
import com.vishnu.exoplayerdemo.pojos.PlaylistItem;

/**
 * Created by vishnu on 9/8/17.
 */

public abstract class ItemAdapter {
    public abstract void handleItem(PlaybackHandler playbackHandler, RegionHolder regionHolder, PlaylistItem item);

    public abstract void stopPlayback();
}
