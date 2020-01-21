package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IStatisticSearchCallback;

public interface IStatisticSearchModel extends IModel {
    void sendNet(String key, IStatisticSearchCallback callback);
}
