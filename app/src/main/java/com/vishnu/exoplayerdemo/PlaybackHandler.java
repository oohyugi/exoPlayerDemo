package com.vishnu.exoplayerdemo;

import android.os.Handler;
import android.os.Message;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.vishnu.exoplayerdemo.adapters.ImageItemAdapter;
import com.vishnu.exoplayerdemo.adapters.ItemAdapter;
import com.vishnu.exoplayerdemo.adapters.SmartTemplateItemAdapter;
import com.vishnu.exoplayerdemo.adapters.VideoItemAdapter;
import com.vishnu.exoplayerdemo.pojos.PlaylistItem;
import com.vishnu.exoplayerdemo.util.Utils;

import java.util.ArrayList;

/**
 * Created by vishnu on 9/8/17.
 */

public class PlaybackHandler extends Handler {
    public static final int NEXT = 1;
    private static final String LOG_LABEL = "PlaybackHandler";
    private ArrayList<PlaylistItem> items = Utils.getDefaultPlaylistItems();
    private RegionHolder regionHolder;
    private ItemAdapter itemAdapter;

    public PlaybackHandler(RegionHolder regionHolder) {
        this.regionHolder = regionHolder;
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == NEXT) {
            if (itemAdapter != null) {
                itemAdapter.stopPlayback();
                itemAdapter = null;
            }
            regionHolder.resetAll();
            ArrayList<PlaylistItem> items = filterNotPlayedItems();
            if (items.size() == 0){
                resetPlaylistItems();
                items = this.items;
            }

            handleItem(items.get(0));
        }
    }

    private void handleItem(PlaylistItem item) {
        switch (item.type) {
            case "VIDEO":
                itemAdapter = new VideoItemAdapter();
                break;
            case "IMAGE":
                itemAdapter = new ImageItemAdapter();
                break;
            case "SMART_TEMPLATE":
                itemAdapter = new SmartTemplateItemAdapter();
                break;
        }

        if (itemAdapter != null) {
            itemAdapter.handleItem(this, regionHolder, item);
        }
    }

    public ArrayList<PlaylistItem> filterNotPlayedItems() {
        ArrayList<PlaylistItem> items = new ArrayList<>();
        items.addAll(Collections2.filter(this.items, new Predicate<PlaylistItem>() {
            @Override
            public boolean apply(PlaylistItem input) {
                return !input.isPlayed;
            }
        }));
        return items;
    }

    public void setItemsAsCompleted(PlaylistItem item) {
        for (PlaylistItem playlistItem : items) {
            if (playlistItem.path.equals(item.path)) {
                playlistItem.isPlayed = true;
            }
        }
    }

    private void resetPlaylistItems() {
        for (PlaylistItem playlistItem : items) {
            playlistItem.isPlayed = false;
        }
    }

    public void stop(){
        if(itemAdapter != null){
            itemAdapter.stopPlayback();
        }
    }
}
