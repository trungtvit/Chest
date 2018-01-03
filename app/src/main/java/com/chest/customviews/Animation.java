package com.chest.customviews;

import android.view.View;

/**
 * Created by TrungTV on 01/03/2018.
 */

public abstract class Animation {

    public static final int DURATION_DEFAULT = 300;
    public static final int DURATION_SHORT = 100;
    public static final int DURATION_LONG = 500;

    View view;

    public abstract void animate();

}
