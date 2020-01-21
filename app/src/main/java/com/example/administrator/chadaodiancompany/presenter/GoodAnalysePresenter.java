package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.GoodAnalyseModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IGoodAnalyseCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IGoodAnalyseView;

import java.io.File;

public class GoodAnalysePresenter extends BasePresenter<IGoodAnalyseView> implements IGoodAnalyseCallback {

    private final GoodAnalyseModelImpl model;

    public GoodAnalysePresenter(IGoodAnalyseView view) {
        super(view);
        model = new GoodAnalyseModelImpl();
    }

    public void sendNet(String key, String type, String startTime, String endTime, int curPage, boolean showDialog) {
        if (mView != null && showDialog) {
            mView.showLoading();
        }
        if (model != null)
            model.sendNet(key, type, startTime, endTime, String.valueOf(curPage), this);
    }

    @Override
    public void getGoodAnalyse(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getGoodAnalyseResult(result);
    }

    @Override
    public void downloadFileExcel(File file) {
        if (mView != null) {
            mView.hideLoading();
            mView.downloadFileExcelSuccess(file);
        }
    }

    public void cancelTag() {

        if (model != null)
            model.cancelTag();
    }

    public void sendNetDownloadExcel(String key, String excelFileName, String startTime, String endTime ) {
        if (mView != null) {
            mView.showLoading();
        }
        if (model != null)
            model.sendNetDownloadExcel(key, excelFileName,startTime, endTime,this);
    }
}
