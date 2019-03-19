package com.example.administrator.chadaodiancompany.image;

import android.content.Context;

import com.bumptech.glide.MemoryCategory;

public interface ILoader {
    /**
     * @param context
     * @param cacheSizeInM   存在磁盘的图片空间总大小
     * @param memoryCategory 调整内存缓存的大小 LOW(0.5f) ／ NORMAL(1f) ／ HIGH(1.5f);
     * @param isInternalCD   true 磁盘缓存到应用的内部目录 / false 磁盘缓存到外部存
     */
    public void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD);

    /**
     * 去请求图片
     *
     * @param config 管理所有glide加载图片时所需要的所有数据
     */
    public void request(SingleConfig config);

    /**
     * 内存不足的时候调用
     *
     * @param level
     */
    void trimMemory(int level);

    boolean isCached(String url);

    void clearAllCache();
}
