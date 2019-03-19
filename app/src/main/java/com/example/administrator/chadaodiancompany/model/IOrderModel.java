package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IOrderCallback;

public interface IOrderModel {
    void sendNetGetOrderList(String key, String curPage, String orderState, String keyword, IOrderCallback callback);

    void sendNetCancelOrder(String key, String orderId, String reason, IOrderCallback callback);

    void sendNetChangeSheep(String key, String orderId, String sheep, IOrderCallback callback);

    void sendNetChangeAmount(String key, String orderId, String amount, String remark, IOrderCallback callback);
}
