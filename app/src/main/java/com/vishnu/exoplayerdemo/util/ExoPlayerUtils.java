package com.vishnu.exoplayerdemo.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;

/**
 * Created by vishnu on 9/8/17.
 */

public class ExoPlayerUtils {
    private static final String LOG_LABEL = "ExoPlayerUtils";

    public static SimpleExoPlayer getNewExoPlayerInstance(Context context) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        return ExoPlayerFactory.newSimpleInstance(context, trackSelector);
    }

    public static void setSourceForExoPlayer(Context context, ExoPlayer exoPlayer, String source,
                                             boolean loop) {
        setSourceForExoPlayer(context, exoPlayer, source, "", "", loop);
    }

    public static void setSourceForExoPlayer(@NonNull SimpleExoPlayerView simpleExoPlayerView,
                                             String source, boolean loop) {
        setSourceForExoPlayer(simpleExoPlayerView, source, "", "", loop);
    }

    public static void setSourceForExoPlayer(@NonNull SimpleExoPlayerView simpleExoPlayerView, String video,
                                             String subTitle, String language, boolean loop) {
        Context context = simpleExoPlayerView.getContext();
        ExoPlayer player = simpleExoPlayerView.getPlayer();
        if (player != null) {
            setSourceForExoPlayer(context, player, video, subTitle, language, loop);
        }
    }

    public static void setSourceForExoPlayer(Context context, @NonNull ExoPlayer player,
                                             String video, String subTitle, String language,
                                             boolean loop) {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                "DS", bandwidthMeter);

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource source;
        if (!TextUtils.isEmpty(subTitle) && Utils.isFileExists(subTitle)) {
            MediaSource videoSource = new ExtractorMediaSource(Uri.parse(video),
                    dataSourceFactory, extractorsFactory, null, null);

            if (TextUtils.isEmpty(language)) language = "en";
            Format format = Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP, 1, language);

            MediaSource subTitleSource = new SingleSampleMediaSource(Uri.parse(subTitle),
                    dataSourceFactory, format, C.TIME_UNSET);

            source = new MergingMediaSource(videoSource, subTitleSource);
        } else {
            source = new ExtractorMediaSource(Uri.parse(video),
                    dataSourceFactory, extractorsFactory, null, null);
        }

        if (loop) {
            LoopingMediaSource loopingSource = new LoopingMediaSource(source);
            player.prepare(loopingSource);
        } else {
            player.prepare(source);
        }
    }

    public static void resetPlayerOnSimpleExoPlayerView(@NonNull SimpleExoPlayerView videoView) {
        ExoPlayer player = videoView.getPlayer();
        if (player != null) {
            player.stop();
            player.release();
            videoView.setPlayer(null);
        }
    }
}
