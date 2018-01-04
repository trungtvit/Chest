package com.chest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.chest.constant.Config;

/**
 * Created by TrungTV on 01/03/2018.
 */

public class SharePref {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharePref(Context context) {
        preferences = context.getSharedPreferences("Chest", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveTimeClickChest(long time) {
        editor.putLong(Config.KEY_TIME_CLICK_CHEST, time);
        editor.commit();
    }

    public long getTimeClickChest() {
        long timeClickChest = preferences.getLong(Config.KEY_TIME_CLICK_CHEST, 0);
        return timeClickChest;
    }

}
