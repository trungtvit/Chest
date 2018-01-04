package com.chest.customviews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by TrungTV on 01/04/2018.
 */

public class ScaleOutAnimation extends Animation implements Combinable {

    TimeInterpolator interpolator;
    long duration;
    AnimationListener listener;

    public ScaleOutAnimation(View view) {
        this.view = view;
        interpolator = new AccelerateDecelerateInterpolator();
        duration = DURATION_LONG;
        listener = null;
    }

    @Override
    public void animate() {
        getAnimatorSet().start();
    }

    @Override
    public AnimatorSet getAnimatorSet() {
        final float originalScaleX = view.getScaleX(), originalScaleY = view
                .getScaleY();
        AnimatorSet scaleSet = new AnimatorSet();
        scaleSet.playTogether(ObjectAnimator.ofFloat(view, View.SCALE_X, 0f),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f));
        scaleSet.setInterpolator(interpolator);
        scaleSet.setDuration(duration);
        scaleSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
                view.setScaleX(originalScaleX);
                view.setScaleY(originalScaleY);
                if (getListener() != null) {
                    getListener().onAnimationEnd(ScaleOutAnimation.this);
                }
            }
        });
        return scaleSet;
    }

    public TimeInterpolator getInterpolator() {
        return interpolator;
    }

    public ScaleOutAnimation setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public ScaleOutAnimation setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public AnimationListener getListener() {
        return listener;
    }

    public ScaleOutAnimation setListener(AnimationListener listener) {
        this.listener = listener;
        return this;
    }

}
