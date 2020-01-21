package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IGoodAnalyseCallback;
import com.example.administrator.chadaodiancompany.presenter.GoodAnalysePresenter;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

public class GoodAnalyseModelImpl implements IGoodAnalyseModel {
    @Override
    public void sendNet(String key, String type, String startTime, String endTime, String curPage, final IGoodAnalyseCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.GOOD_ANALYSE_URL)
                .tag("GOOD_ANALYSE_TAG")
                .params("key", key)
                .params("type", type)
                .params("start_time", startTime)
                .params("end_time", endTime)
                .params("curpage", curPage)
                .params("page", "15")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getGoodAnalyse(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void cancelTag() {
        OkGo.getInstance().cancelTag("GOOD_ANALYSE_TAG");
    }

    public void sendNetDownloadExcel(String key, String fileName, String startTime, String endTime, final IGoodAnalyseCallback callback) {

        OkGo.<File>get(NetUtils.BASE_URL + NetUtils.EXPORT_GOOD_URL)
                .tag("GOOD_ANALYSE_TAG")
                .params("key", key)
                .params("start_time", startTime)
                .params("end_time", endTime)
                .execute(new FileCallback(fileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        callback.downloadFileExcel(response.body());
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });


    }
}
