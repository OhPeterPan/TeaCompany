package com.example.administrator.chadaodiancompany.image;

import android.content.Context;

import com.bumptech.glide.MemoryCategory;

public class ImageLoader {
    /**
     * 设置在本地磁盘的最大存储空间
     */
    public static int CACHE_SIZE = 250;
    public static Context mContext;

    public static void init(Context ctx) {
        init(ctx, CACHE_SIZE);

    }

    private static void init(Context ctx, int cacheSize) {
        init(ctx, cacheSize, MemoryCategory.NORMAL);
    }

    private static void init(Context ctx, int cacheSize, MemoryCategory memoryCategory) {
        init(ctx, cacheSize, memoryCategory, true);
    }

    /**
     * @param ctx
     * @param cacheSize      图片存储在磁盘最大的容量空间
     * @param memoryCategory 调整内存缓存的大小  LOW(0.5f)/NORMAL(1F)/HIGH(1.5F)
     * @param isInternalCD   是否存储在APP内部空间
     */
    private static void init(Context ctx, int cacheSize, MemoryCategory memoryCategory, boolean isInternalCD) {
        mContext = ctx;
        GlideConfig.init(ctx, cacheSize, memoryCategory, isInternalCD);
    }

    /**
     * 获取当前的Loader
     *
     * @return
     */
    public static ILoader getActualLoader() {
        return GlideConfig.getLoader();
    }

    /**
     * 当手机内存紧张时调用
     *
     * @param level
     */
    public static void trimMemory(int level) {
        getActualLoader().trimMemory(level);
    }

    public static SingleConfig.ConfigBuilder with(Context ctx) {
        return new SingleConfig.ConfigBuilder(ctx);
    }

    public static void clearAllCache() {
        getActualLoader().clearAllCache();
    }
}
