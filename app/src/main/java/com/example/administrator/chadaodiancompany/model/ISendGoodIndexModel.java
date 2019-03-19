package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ISendGoodCallback;

public interface ISendGoodIndexModel {
    void sendNetGetOrderDetail(String key, String orderId, ISendGoodCallback callback);
}
