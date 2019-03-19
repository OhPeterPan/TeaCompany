package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMsgCallback;

public interface IMsgModel {
    void sendNetMsgList(String key, String curPage, IMsgCallback callback);

    void sendNetMsgRead(String key, String msgId, IMsgCallback callback);

    void sendNetMsgReadTag(String key, String msgIds, IMsgCallback callback);

    void sendNetMsgDelete(String key, String msgIds, IMsgCallback callback);
}
