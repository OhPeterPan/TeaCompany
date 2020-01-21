package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IConfirmMoneyCallback;

public interface IConfirmMoneyModel extends IModel {

    void sendNet(String key, IConfirmMoneyCallback callback);

    void sendNetPay(String key, String time, String payCode, String remark, IConfirmMoneyCallback callback);
}
