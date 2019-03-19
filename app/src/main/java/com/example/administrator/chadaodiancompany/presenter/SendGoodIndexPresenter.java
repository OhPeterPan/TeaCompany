package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.SendGoodIndexModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.ISendGoodCallback;
import com.example.administrator.chadaodiancompany.viewImpl.ISendGoodIndexView;

public class SendGoodIndexPresenter extends BasePresenter<ISendGoodIndexView> implements ISendGoodCallback {

    private final SendGoodIndexModelImpl model;

    public SendGoodIndexPresenter(ISendGoodIndexView view) {
        super(view);
        model = new SendGoodIndexModelImpl();
    }

    public void sendNetOrderDetail(String key, String orderId) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetGetOrderDetail(key, orderId, this);
    }

    @Override
    public void getOrderDetail(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            try {
                mView.getOrderDetailSuccess(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
