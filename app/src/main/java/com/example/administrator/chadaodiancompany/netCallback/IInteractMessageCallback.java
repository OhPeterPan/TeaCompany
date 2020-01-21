package com.example.administrator.chadaodiancompany.netCallback;

public interface IInteractMessageCallback extends ICallback {
    void getInteractDetail(String result);

    void sendMessage(String result);
}
