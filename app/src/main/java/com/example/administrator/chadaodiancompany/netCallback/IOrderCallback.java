package com.example.administrator.chadaodiancompany.netCallback;

public interface IOrderCallback extends ICallback {
    void getOrderList(String result);

    void cancelOrder(String result);

    void changeOrderAmount(String result);

    void changeSheep(String result);
}
