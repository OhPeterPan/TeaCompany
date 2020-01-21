package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IGoodAnalyseCallback;

public interface IGoodAnalyseModel extends IModel {
    void sendNet(String key, String type, String startTime, String endTime, String curPage, IGoodAnalyseCallback callback);
}
