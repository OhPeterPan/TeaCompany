package com.example.administrator.chadaodiancompany.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SpUtil {
    private static final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(UIUtil.getContext());
    public static final String KEY = "key";
    public static final String STORE_NAME = "storeName";

    public static String getString(String key, String defaultValue) {
        String value = sp.getString(key, defaultValue);
        return value;
    }

    public static void putString(String key, String defaultValue) {
        sp.edit().putString(key, defaultValue).commit();
    }

    public static void clearAll() {
        sp.edit().clear().commit();
    }
}
