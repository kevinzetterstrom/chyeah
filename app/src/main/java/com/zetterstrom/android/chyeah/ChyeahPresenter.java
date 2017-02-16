package com.zetterstrom.android.chyeah;

import android.support.annotation.NonNull;

import com.zetterstrom.android.chyeah.ChyeahView.Speed;

import java.util.concurrent.TimeUnit;

/**
 * A simple presenter for the Chyeah MVP system. This is the glue between the view and the model.
 * This class is in charge of responding to view events and telling the model and view to react
 * based on those events.
 * <p>
 * Created by zetterstromk on 2/1/17.
 */
class ChyeahPresenter {

    private static final String FAST_CHYEAH = "chyeah_loud.mp3";
    private static final String MEDIUM_CHYEAH = "chyeah_fast.mp3";
    private static final String SLOW_CHYEAH = "chyeah_soft.mp3";

    private static final int FAST_THRESHOLD_MS = 125;
    private static final int MEDIUM_THRESHOLD_MS = 500;

    private final ChyeahPlayer mPlayer = new ChyeahPlayer();

    @NonNull
    private final ChyeahView mView;

    ChyeahPresenter(@NonNull ChyeahView view) {
        mView = view;
    }

    /**
     * The view should call this when a touch event is finished, such as on
     * ACTION_CANCEL, ACTION_UP, or ACTION_OUTSIDE
     *
     * @param downTimeMs the timestamp when the touch event began with a ACTION_DOWN
     */
    void onEndTouch(long downTimeMs) {
        long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()) - downTimeMs;

        if (durationMs < FAST_THRESHOLD_MS) {
            mPlayer.playFile(FAST_CHYEAH);
            mView.animateChyeah(Speed.FAST);
        } else if (durationMs < MEDIUM_THRESHOLD_MS) {
            mPlayer.playFile(MEDIUM_CHYEAH);
            mView.animateChyeah(Speed.MEDIUM);
        } else {
            mPlayer.playFile(SLOW_CHYEAH);
            mView.animateChyeah(Speed.SLOW);
        }
    }

    /**
     * The view should call this in its onCreate
     */
    void onCreate() {
        mPlayer.createMediaPlayer();
    }

    /**
     * The view should call this in its onDestroy
     */
    void onDestroy() {
        mPlayer.releaseMediaPlayer();
    }

}
