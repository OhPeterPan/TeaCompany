package com.example.administrator.chadaodiancompany.util;

import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.chadaodiancompany.toasty.Toasty;

/**
 * Created by Administrator on 2018/1/23 0023.
 * Toast的封装
 */

public class ToastUtil {
    public static void showMassageShort(CharSequence text) {
        ToastUtils.showShort(text);
    }

    public static void showMassageLong(CharSequence text) {
        ToastUtils.showLong(text);
    }

    public static void showSuccess(CharSequence text) {
        Toasty.success(UIUtil.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showError(CharSequence text) {
        Toasty.error(UIUtil.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showInfoShort(CharSequence text) {
        Toasty.info(UIUtil.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showInfoLong(CharSequence text) {
        Toasty.info(UIUtil.getContext(), text, Toast.LENGTH_LONG).show();
    }

    public static void showWarnShort(CharSequence text) {
        Toasty.warning(UIUtil.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showWarnLong(CharSequence text) {
        Toasty.warning(UIUtil.getContext(), text, Toast.LENGTH_LONG).show();
    }
}
