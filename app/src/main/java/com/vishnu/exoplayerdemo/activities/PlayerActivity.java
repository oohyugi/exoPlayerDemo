package com.vishnu.exoplayerdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vishnu.exoplayerdemo.PlaybackHandler;
import com.vishnu.exoplayerdemo.RegionHolder;
import com.vishnu.exoplayerdemo.util.Utils;

/**
 * Created by vishnu on 9/8/17.
 */

public class PlayerActivity extends AppCompatActivity {
    private static final String LOG_LABEL = "PlayerActivity";
    private RegionHolder regionHolder;
    private PlaybackHandler playbackHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Utils.TAG, LOG_LABEL + "onCreate Called");


        this.regionHolder = new RegionHolder(this);
        setContentView(regionHolder);

        playbackHandler = new PlaybackHandler(regionHolder);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (regionHolder != null) {
            regionHolder.resetAll();
        }

        if (playbackHandler != null) {
            playbackHandler.stop();
            playbackHandler.removeMessages(PlaybackHandler.NEXT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        playbackHandler.sendEmptyMessage(PlaybackHandler.NEXT);
    }
}
