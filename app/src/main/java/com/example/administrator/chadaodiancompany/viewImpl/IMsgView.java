package com.example.administrator.chadaodiancompany.viewImpl;

public interface IMsgView extends IView {
    void getMsgListSuccess(String result);

    void getMsgReadSuccess(String result);

    void getMsgReadTagSuccess(String result);

    void deleteMsgSuccess(String result);
}
