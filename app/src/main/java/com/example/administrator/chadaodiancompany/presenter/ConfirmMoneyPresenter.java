package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.ConfirmMoneyModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IConfirmMoneyCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IConfirmMoneyView;

public class ConfirmMoneyPresenter extends BasePresenter<IConfirmMoneyView> implements IConfirmMoneyCallback {

    private final ConfirmMoneyModelImpl model;

    public ConfirmMoneyPresenter(IConfirmMoneyView view) {
        super(view);
        model = new ConfirmMoneyModelImpl();
    }

    public void sendNet(String key) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNet(key, this);
    }

    @Override
    public void getPayMethod(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.getPayMethodResult(result);
        }
    }

    @Override
    public void confirmPay(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.confirmPayResult(result);
        }
    }

    public void sendNetPay(String key, String time, String payCode, String remark) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetPay(key,time,payCode,remark, this);
    }
}
