package com.zetterstrom.android.chyeah;

import android.support.annotation.NonNull;

/**
 * The view interface
 * <p>
 * Created by zetterstromk on 2/1/17.
 */
interface ChyeahView {

    enum Speed {
        FAST,
        MEDIUM,
        SLOW
    }

    /**
     * Called by the presenter to show an animation on the screen. It is up to
     * the implementing view to dictate the animation behavior.
     *
     * @param speed a {@link Speed} at which the animation should occur. The view
     *              can choose to speed up the animation or possibly alter it completely
     *              based on this parameter.
     */
    void animateChyeah(@NonNull Speed speed);
}
