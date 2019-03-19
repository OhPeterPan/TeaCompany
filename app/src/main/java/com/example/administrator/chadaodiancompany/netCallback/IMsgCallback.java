package com.example.administrator.chadaodiancompany.netCallback;

public interface IMsgCallback extends ICallback {
    void getMsgList(String result);

    void getMsgRead(String result);

    void getMsgReadTag(String result);

    void deleteMsg(String result);
}
