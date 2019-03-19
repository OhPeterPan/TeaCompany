package com.example.administrator.chadaodiancompany.netCallback;

public interface IOrderDetailCallback extends ICallback {
    void getOrderDetail(String json);

    void cancelOrder(String result);

    void changeSheep(String result);

    void changeOrderAmount(String result);
}
