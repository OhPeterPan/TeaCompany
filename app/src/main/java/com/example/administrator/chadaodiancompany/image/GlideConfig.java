package com.example.administrator.chadaodiancompany.image;

import android.content.Context;

import com.bumptech.glide.MemoryCategory;

public class GlideConfig {
    public static Context mContext;
    private static ILoader loader;

    public static void init(Context ctx, int cacheSize, MemoryCategory memoryCategory, boolean isInternalCD) {
        getLoader().init(ctx, cacheSize, memoryCategory, isInternalCD);
    }

    public static ILoader getLoader() {
        if (loader == null) loader = new GlideLoader();

        return loader;
    }
}
