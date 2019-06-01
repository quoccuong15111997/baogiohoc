package com.example.tkb;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ManagerSharePreferense {
    private static final String trangThai="TRANG_THAI";


    private static SharedPreferences sPreferences;

    private ManagerSharePreferense() {
    }

    public static void init(Context context) {
        if (sPreferences == null) {
            sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static void setStart(boolean isFirstTime) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean(trangThai, isFirstTime);
        editor.apply();
    }

    public static boolean isStart() {
        return sPreferences.getBoolean(trangThai, true);
    }
}
