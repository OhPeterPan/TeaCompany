package com.example.administrator.chadaodiancompany.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.application.TeaApplication;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class UIUtil {
    public static final boolean IS_DEBUG = true;

    public static Context getContext() {
        return TeaApplication.getInstance().getApplicationContext();
    }

    public static float getDimens(int dimensId) {

        return getResources().getDimensionPixelOffset(dimensId);
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static Badge setBadgeView(View view) {//设置未读角标
        return new QBadgeView(getContext()).bindTarget(view);
    }

    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }
    public static void setData(TextView tv, String data) {
        if (StringUtils.isEmpty(data)) {
            tv.setText("无");
        } else {
            tv.setText(data);
        }
    }
}
