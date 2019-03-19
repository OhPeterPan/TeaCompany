package com.example.administrator.chadaodiancompany.netCallback;

public interface IGoodIndexCallback extends ICallback {
    void getGoodList(String result);

    void getBanGoodDetail(String result);

    void changeGoodNumber(String result);
}
