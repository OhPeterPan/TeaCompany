package com.example.administrator.chadaodiancompany.viewImpl;

public interface IOrderView extends IView {
    void getOrderListSuccess(String result);

    void cancelOrderSuccess(String result);

    void changeOrderAmountSuccess(String result);

    void changeSheepSuccess(String result);
}
