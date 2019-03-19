package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IExpressManagerCallback;

import java.util.HashMap;

public interface IExpressManagerModel {
    void sendNetGetExpressList(String key, String orderId, IExpressManagerCallback callback);

    void sendNetSendGood(String key, HashMap<String, String> paramsMap, IExpressManagerCallback callback);
}
