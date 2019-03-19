package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ILookExpressCallback;

public interface ILookExpressModel {
    void sendNetLookExpress(String key, String orderId, String num, ILookExpressCallback callback);
}
