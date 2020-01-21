package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMessageCallback;

public interface IMessageModel extends IModel {
    void sendNetInteractMessage(String key, String curPage, IMessageCallback callback);

    void sendNetMessageList(String key, String curPage, IMessageCallback callback);

    void sendNetDelMessage(String key, String messageIds, IMessageCallback callback);

    void sendNetReadMessage(String key, String messageId, IMessageCallback callback);

    void sendNetReadMessageList(String key, String messageIds, IMessageCallback callback);
}
