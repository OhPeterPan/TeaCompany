package com.example.administrator.chadaodiancompany.netCallback;

public interface IConfirmMoneyCallback extends ICallback {
    void getPayMethod(String result);

    void confirmPay(String result);
}
