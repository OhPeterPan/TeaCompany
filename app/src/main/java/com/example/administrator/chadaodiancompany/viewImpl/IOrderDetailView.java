package com.example.administrator.chadaodiancompany.viewImpl;

public interface IOrderDetailView extends IView {
    void getOrderDetailSuccess(String result) throws Exception;

    void cancelOrderSuccess(String result);

    void changeOrderSuccess(String result);

    void changeSheepSuccess(String result);
}
