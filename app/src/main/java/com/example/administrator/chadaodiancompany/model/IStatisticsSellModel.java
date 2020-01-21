package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IStatisticsSellCallback;

public interface IStatisticsSellModel extends IModel {
    void sendNet(String key, String curPage, String sort, String startTime, String endTime, IStatisticsSellCallback callback);
}
