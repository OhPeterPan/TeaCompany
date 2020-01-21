package com.example.administrator.chadaodiancompany.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class ExtraUtils {
    public static void sendIntent(AppCompatActivity activity, File file) {

        if (file == null)
            return;

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_SEND);

        String type = MIMETypeUtils.getMIMEType(file);

        Uri contentUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(activity, "com.example.administrator.chadaodiancompany.fileProvider", file);
        } else {
            contentUri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.setDataAndType(contentUri, type); //设置intent的data和Type属性。
        intent.putExtra(Intent.EXTRA_STREAM, contentUri);
        Intent.createChooser(intent, "发送文件");
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            LogUtil.logE(e);
            ToastUtil.showError("没有能打开文件的应用程序(wps、QQ、邮箱等应用程序)");
        }


    }
}
