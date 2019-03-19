package com.example.administrator.chadaodiancompany.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.chadaodiancompany.R;

public class GlideLoader implements ILoader {
    @Override
    public void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD) {
        Glide.get(context).setMemoryCategory(memoryCategory); //如果在应用当中想要调整内存缓存的大小，开发者可以通过如下方式：
        GlideBuilder builder = new GlideBuilder();
        if (isInternalCD) {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSizeInM * 1024 * 1024));
        } else {
            builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, cacheSizeInM * 1024 * 1024));
        }
    }

    @Override
    public void request(SingleConfig config) {
        if (config == null) return;
        /* config.get*/
        RequestManager requestManager = Glide.with(config.mContext);
        RequestBuilder<Drawable> requestBuilder = getRequestBuilder(config, requestManager);
        RequestOptions requestOptions = null;
        if (config.memoryCacheStrategy) {
            if (requestOptions == null) {
                requestOptions = new RequestOptions();
            }
            requestOptions = requestOptions.skipMemoryCache(true);
        }

        if (config.diskCacheStrategy != null) {
            if (requestOptions == null) {
                requestOptions = new RequestOptions();
            }
            requestOptions = requestOptions.diskCacheStrategy(config.diskCacheStrategy);
        }
        if (config.placeHolderResId > 0) {
            if (requestOptions == null) {
                requestOptions = new RequestOptions();
            }
            requestOptions = requestOptions.placeholder(config.placeHolderResId);
        }
        if (config.errorResId > 0) {
            if (requestOptions == null) {
                requestOptions = new RequestOptions();
            }
            requestOptions = requestOptions.placeholder(config.errorResId);
        }
        if (requestOptions != null)
            requestBuilder = requestBuilder.apply(requestOptions);

        requestBuilder.into(config.tagetView);
    }

    /**
     * load各种资源，可以自己添加一些raw等资源
     * *
     *
     * @param config
     * @param requestManager
     * @return
     */

    @Nullable
    private RequestBuilder getRequestBuilder(SingleConfig config, RequestManager requestManager) {
        RequestBuilder requestBuilder = null;
        if (!TextUtils.isEmpty(config.url)) {
            requestBuilder = requestManager.load(config.url);
            Log.e("TAG", "getUrl : " + config.url);
        } else if (!TextUtils.isEmpty(config.filePath)) {
            requestBuilder = requestManager.load(config.filePath);
            Log.e("TAG", "getFilePath : " + config.filePath);
        } else if (config.resId > 0) {
            requestBuilder = requestManager.load(config.resId);
            Log.e("TAG", "getResId : " + config.resId);
        } else if (config.file != null) {
            requestBuilder = requestManager.load(config.file);
            Log.e("TAG", "getFile : " + config.file);
        } else if (config.uri != null) {
            requestBuilder = requestManager.load(config.uri);
        } else if (config.errorResId > 0) {
            requestBuilder = requestManager.load(config.errorResId);
        } else {//全部都没有设置的时候
            requestBuilder = requestManager.load(R.mipmap.image_load_error);
        }
        return requestBuilder;
    }

    @Override
    public void trimMemory(int level) {
        Glide.get(GlideConfig.mContext).trimMemory(level);
    }

    @Override
    public boolean isCached(String url) {
        return false;
    }

    @Override
    public void clearAllCache() {
        Glide.get(GlideConfig.mContext).onLowMemory();
    }
}
