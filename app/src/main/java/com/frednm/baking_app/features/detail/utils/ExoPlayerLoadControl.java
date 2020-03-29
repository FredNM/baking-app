package com.frednm.baking_app.features.detail.utils;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.upstream.DefaultAllocator;


// Taken here https://stackoverflow.com/questions/55773893/how-to-fix-exo-player-slow-loading-of-video
// This Load Control is expected to speed up video loading !
public class ExoPlayerLoadControl {
    //Minimum Video you want to buffer while Playing
    public static final int MIN_BUFFER_DURATION = 2000;
    //Max Video you want to buffer during PlayBack
    public static final int MAX_BUFFER_DURATION = 5000;
    //Min Video you want to buffer before start Playing it
    public static final int MIN_PLAYBACK_START_BUFFER = 1500;
    //Min video You want to buffer when user resumes video
    public static final int MIN_PLAYBACK_RESUME_BUFFER = 2000;


    public static LoadControl loadControl = new DefaultLoadControl.Builder()
            .setAllocator(new DefaultAllocator(true, 16))
            .setBufferDurationsMs(ExoPlayerLoadControl.MIN_BUFFER_DURATION,
                    ExoPlayerLoadControl.MAX_BUFFER_DURATION,
                    ExoPlayerLoadControl.MIN_PLAYBACK_START_BUFFER,
                    ExoPlayerLoadControl.MIN_PLAYBACK_RESUME_BUFFER)
            .setTargetBufferBytes(-1)
            .setPrioritizeTimeOverSizeThresholds(true).createDefaultLoadControl();
}
