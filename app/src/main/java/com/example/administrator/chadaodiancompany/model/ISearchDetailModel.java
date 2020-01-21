package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ISearchDetailCallback;

public interface ISearchDetailModel extends IModel {
    void sendNet(String key, String type, String keyword, String stcId, String startTime, String endTime, String curPage, ISearchDetailCallback callback);
}
