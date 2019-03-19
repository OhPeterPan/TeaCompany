package com.example.administrator.chadaodiancompany.util;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.example.administrator.chadaodiancompany.image.ImageLoader;
import com.lzy.okgo.OkGo;

import org.litepal.LitePal;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class UtilManager {
    private static UtilManager manager;

    private UtilManager() {
    }

    public static UtilManager getInstance() {
        if (manager == null) {
            synchronized (UtilManager.class) {
                if (manager == null)
                    manager = new UtilManager();
            }
        }
        return manager;
    }

    public void initProject() {
        Utils.init(UIUtil.getContext());
        initNet(UIUtil.getContext());
        LitePal.initialize(UIUtil.getContext());
        ImageLoader.init(UIUtil.getContext());
    }

    /**
     * 初始化网络连接
     *
     * @param mContext
     */
    private void initNet(Context mContext) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(TimeUtil.DEFAULT_NET_READ_TIME, TimeUnit.MILLISECONDS);//OkGo.DEFAULT_MILLISECONDS
        //全局的写入超时时间
        builder.writeTimeout(TimeUtil.DEFAULT_NET_WRITE_TIME, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(TimeUtil.DEFAULT_NET_CONNECTION_TIME, TimeUnit.MILLISECONDS);//客户端与服务器三次握手时间
        OkGo.getInstance().init((Application) mContext)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setRetryCount(2);                            //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }
}
