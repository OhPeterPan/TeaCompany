package com.example.administrator.chadaodiancompany.util;

import com.blankj.utilcode.util.LogUtils;
import com.example.administrator.chadaodiancompany.BuildConfig;

/**
 * Created by Administrator on 2018/1/22 0022.
 * 对logutils再次进行封装
 */

public class LogUtil {

    public static void logE(Throwable e) {
        if (BuildConfig.DEBUG)
            LogUtils.eTag("wak", e);
    }

    public static void logI(Object o) {
        if (BuildConfig.DEBUG)
            LogUtils.iTag("wak", o);
    }
}
