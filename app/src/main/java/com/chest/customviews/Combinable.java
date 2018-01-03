package com.chest.customviews;

import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;

/**
 * Created by TrungTV on 01/03/2018.
 */

public interface Combinable {

    public void animate();

    public AnimatorSet getAnimatorSet();

    public Animation setInterpolator(TimeInterpolator interpolator);

    public long getDuration();

    public Animation setDuration(long duration);

    public Animation setListener(AnimationListener listener);

}
