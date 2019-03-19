package com.example.administrator.chadaodiancompany.image;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * Created by Administrator on 2018/1/20 0020.
 */

public class SingleConfig {
    public Uri uri;
    public ImageView tagetView;
    public Context context;
    public String url;
    public boolean isGif;
    public File file;
    public int resId;
    public int placeHolderResId;
    public DiskCacheStrategy diskCacheStrategy;
    public int errorResId;
    public boolean memoryCacheStrategy;
    public String filePath;
    public Context mContext;

    public SingleConfig(ConfigBuilder build) {
        this.context = build.context;
        this.url = build.url;
        this.uri = build.uri;
        this.isGif = build.isGif;
        this.file = build.file;
        this.resId = build.resId;
        this.placeHolderResId = build.placeHolderResId;
        this.diskCacheStrategy = build.diskCacheStrategy;
        this.errorResId = build.errorResId;
        this.memoryCacheStrategy = build.memoryCacheStrategy;
        this.tagetView = build.tagetView;
        this.filePath = build.filePath;
        mContext = build.context;
    }

    public void show() {
        GlideConfig.getLoader().request(this);
    }

    public static class ConfigBuilder {
        public Context context;
        public String url;
        public boolean isGif;
        public File file;
        public int resId;
        public int placeHolderResId;
        public DiskCacheStrategy diskCacheStrategy;
        public int errorResId;
        public boolean memoryCacheStrategy;
        public ImageView tagetView;
        public String filePath;
        private Uri uri;

        public ConfigBuilder(Context ctx) {
            this.context = ctx;
        }

        /**
         * 设置网络路径
         *
         * @param url
         * @return
         */
        public ConfigBuilder url(String url) {
            this.url = url;
            if (url != null && url.contains("gif")) {
                isGif = true;
            }
            return this;
        }

        /**
         * 设置网络路径
         *
         * @param uri
         * @return
         */
        public ConfigBuilder uri(Uri uri) {
            this.uri = uri;
            return this;
        }

        /**
         * 加载SD卡资源
         *
         * @param filePath
         * @return
         */
        public ConfigBuilder file(String filePath) {
            if (filePath.startsWith("file:")) {
                this.filePath = filePath;
                return this;
            }

            if (!new File(filePath).exists()) {
                //throw new RuntimeException("文件不存在");
                Log.e("imageloader", "文件不存在");
                return this;
            }

            this.filePath = filePath;
            if (filePath.contains("gif")) {
                isGif = true;
            }
            return this;
        }

        /**
         * 加载SD卡资源
         *
         * @param file
         * @return
         */
        public ConfigBuilder file(File file) {
            this.file = file;

            return this;
        }

        /**
         * 加载drawable资源
         *
         * @param resId
         * @return
         */
        public ConfigBuilder res(int resId) {
            this.resId = resId;
            return this;
        }

        /**
         * 占位图
         *
         * @param placeHolderResId
         * @return
         */
        public ConfigBuilder placeHolder(int placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }

        /**
         * 错误图
         *
         * @param errorResId
         * @return
         */
        public ConfigBuilder error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        /**
         * 是否跳过内存存储
         */
        public ConfigBuilder diskCacheStrategy(boolean memoryCacheStrategy) {
            this.memoryCacheStrategy = memoryCacheStrategy;
            return this;
        }

        /**
         * 磁盘缓存
         */
        public ConfigBuilder diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        /**
         * 加载到一个imageview上面
         */
        public void into(View targetView) {
            this.tagetView = (ImageView) targetView;
            new SingleConfig(this).show();
        }
    }
}
