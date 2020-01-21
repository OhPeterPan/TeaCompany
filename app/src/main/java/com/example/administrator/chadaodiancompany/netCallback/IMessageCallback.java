package com.example.administrator.chadaodiancompany.netCallback;

public interface IMessageCallback extends ICallback {
    void getMessageList(String result);

    void delMessageInfo(String result);

    void readTag(String result);

    void readTags(String result);

    void getInteractMessage(String result);
}
