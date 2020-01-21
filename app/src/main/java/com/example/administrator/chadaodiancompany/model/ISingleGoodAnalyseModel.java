package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ISingleGoodAnalyseCallback;

public interface ISingleGoodAnalyseModel extends IModel {
    void sendNet(String key, String goodId, String startTime, String endTime, ISingleGoodAnalyseCallback callback);
}
