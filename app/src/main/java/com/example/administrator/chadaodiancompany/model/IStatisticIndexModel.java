package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IStatisticIndexCallback;

public interface IStatisticIndexModel extends IModel {
    void sendNetStatistic(String key, String searchType, IStatisticIndexCallback callback);
}
