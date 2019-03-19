package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IOrderDetailCallback;

public interface IOrderDetailModel {
    void sendNetOrderDetail(String key, String orderId, IOrderDetailCallback callback);

    void sendNetCancelOrder(String key, String orderId, String reason, IOrderDetailCallback callback);

    void sendNetChangeSheep(String key, String orderId, String sheep, IOrderDetailCallback callback);

    void sendNetChangeAmount(String key, String orderId, String amount, String remark, IOrderDetailCallback callback);
}
