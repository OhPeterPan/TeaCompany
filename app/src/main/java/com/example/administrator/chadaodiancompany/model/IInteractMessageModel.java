package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IInteractMessageCallback;

public interface IInteractMessageModel extends IModel {
    void sendNetInteractDetail(String key, String uId, String curPage, IInteractMessageCallback callback);

    void sendNetSendInteractMessage(String key, String uId, String uName, String content, IInteractMessageCallback callback);
}
