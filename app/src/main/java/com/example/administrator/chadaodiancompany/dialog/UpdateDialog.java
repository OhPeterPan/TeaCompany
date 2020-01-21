package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.UIUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateDialog extends AlertDialog {
    private String url;

    public UpdateDialog(@NonNull Context context, String url) {
        super(context, R.style.CommonDialog);
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        setCanceledOnTouchOutside(false);
        startUpdate();
    }

    private void startUpdate() {
        new UpdateAsyncTask().execute(url);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    private class UpdateAsyncTask extends AsyncTask<String, Integer, File> {

        @Override
        protected File doInBackground(String... params) {
            RandomAccessFile saveFile = null;
            InputStream inputStream = null;
            File file = null;
            try {
                //准备阶段
                long downloadedLength = 0;//记录已经下载的文件长度
                String downloadUrl = params[0];
                // String downloadUrl = "http://shouji.360tpcdn.com/180809/e552e11e2437dcb3cc376c72639e358f/com.qihoo360.mobilesafe_261.apk";
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                //Environment.DIRECTORY_DOWNLOADS 下载的标准目录。
                //String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();//获取顶级公共外部存储目录，以放置特定类型的文件。
                file = new File(UIUtil.getContext().getFilesDir(), fileName);
                //MyLog.getLog(directory + fileName);
                if (file.exists()) {//判断这个文件夹下是否已经存在数据，如果有就读取大小，开启断点续传的功能
                    // downloadedLength = file.length();
                    file.delete();
                }
                //  long contentLength = UIUtil.getContentLength(downloadUrl);//得到下载文件的总长度
                // LogUtil.logI(downloadedLength + "文件总长度" + contentLength + "::::公共目录:" + directory);

                //开始下载文件
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(downloadUrl)
                        .build();
                Response response = client.newCall(request).execute();
                if (response != null) {
                    inputStream = response.body().byteStream();
                    saveFile = new RandomAccessFile(file, "rw");//设置下载文件所在文件夹可读可写
                    // saveFile.seek(downloadedLength);//跳过已经下载的字节
                    byte[] b = new byte[1024];//每次读取的文件大小
                    // int total = 0;
                    int len;
                    while ((len = inputStream.read(b)) != -1) {
                        // total += len;
                        saveFile.write(b, 0, len);//将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此输出流。
                 /*       int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                        publishProgress(progress);*/
                    }
                    response.body().close();
                    return file;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (saveFile != null) {
                        saveFile.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return file;
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            LogUtil.logI(file.getAbsolutePath());
            dismiss();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(getContext(), "com.example.administrator.chadaodiancompany.fileProvider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//FLAG_ACTIVITY_NEW_TASK
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            getContext().startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

}
