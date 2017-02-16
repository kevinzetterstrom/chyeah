package com.zetterstrom.android.chyeah;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * The model of the Chyeah MVP system. This class is in charge of interacting
 * with the Android system for audio playback.
 * <p>
 * Created by zetterstromk on 2/1/17.
 */
class ChyeahPlayer {

    private static final String LOG_TAG = "ChyeahPlayer";

    private MediaPlayer mMediaPlayer;

    /**
     * The presenter should call this to create a new {@link MediaPlayer}
     */
    void createMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * The presenter should call this to release the {@link MediaPlayer} when it is
     * no longer needed.
     */
    void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * Called to play an audio file. If the {@link MediaPlayer} has not been created, or it
     * has been released before the call to this method, then nothing will happen. Use
     * {@link #createMediaPlayer()} before calling this method.
     *
     * @param file a string representing an audio file located in the apps assets directory.
     */
    void playFile(@NonNull String file) {

        if (mMediaPlayer == null) {
            return;
        }

        AssetFileDescriptor descriptor = null;

        try {
            descriptor = ChyeahApplication.getContext().getAssets().openFd(file);

            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }

            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(),
                                       descriptor.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();

        } catch (IOException | IllegalArgumentException | IllegalStateException e) {
            Log.e(LOG_TAG, "Exception playing file " + file, e);
        } finally {
            closeAsset(descriptor);
        }
    }

    private static void closeAsset(@Nullable AssetFileDescriptor descriptor) {
        if (descriptor != null) {
            try {
                descriptor.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Exception closing AssetFileDescriptor", e);
            }
        }
    }
}
