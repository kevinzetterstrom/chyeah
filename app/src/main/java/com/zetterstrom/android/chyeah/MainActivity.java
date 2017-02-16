/*
 * Copyright (c) 2017 Kevin Zetterstrom (www.kevinzetterstrom.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zetterstrom.android.chyeah;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * The main {@link AppCompatActivity} for Chyea! This class implements the
 * {@link ChyeahView} and creates a {@link ChyeahPresenter}.
 * <p>
 * Created by zetterstromk on 2/1/17.
 */
public class MainActivity extends AppCompatActivity implements OnTouchListener, ChyeahView {

    private static final int FAST_ANIMATION_DURATION = 75;
    private static final int MEDIUM_ANIMATION_DURATION = 125;
    private static final int SLOW_ANIMATION_DURATION = 250;

    private ChyeahPresenter mPresenter;

    private MotionEvent mLastMotionEvent;

    private View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new ChyeahPresenter(this);
        mPresenter.onCreate();

        mRootView = findViewById(R.id.activity_main);
        mRootView.setOnTouchListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
                mLastMotionEvent = MotionEvent.obtain(event);
                long time = event.getDownTime();
                mPresenter.onEndTouch(time);
                break;
        }
        return true;
    }

    @Override
    public void animateChyeah(@NonNull Speed speed) {
        if (mLastMotionEvent == null) {
            return;
        }

        int x = (int) mLastMotionEvent.getX();
        int y = (int) mLastMotionEvent.getY();

        @ColorRes int colorResource = R.color.colorAccent;
        int animationDuration = MEDIUM_ANIMATION_DURATION;

        switch (speed) {
            case FAST:
                colorResource = R.color.fast;
                animationDuration = FAST_ANIMATION_DURATION;
                break;
            case MEDIUM:
                colorResource = R.color.medium;
                animationDuration = MEDIUM_ANIMATION_DURATION;
                break;
            case SLOW:
                colorResource = R.color.slow;
                animationDuration = SLOW_ANIMATION_DURATION;
                break;
        }

        animateRevealColorFromCoordinates(mRootView, colorResource, animationDuration, x, y);
    }

    private void animateRevealColorFromCoordinates(@NonNull View viewRoot, @ColorRes int color,
                                                   int animationDuration, int x, int y) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        anim.setDuration(animationDuration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }
}
