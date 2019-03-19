package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.OrderDetailModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IOrderDetailCallback;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IOrderDetailView;

public class OrderDetailPresenter extends BasePresenter<IOrderDetailView> implements IOrderDetailCallback {

    private final OrderDetailModelImpl model;

    public OrderDetailPresenter(IOrderDetailView view) {
        super(view);
        model = new OrderDetailModelImpl();
    }

    public void sendNetGetOrderDetail(String key, String orderId) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetOrderDetail(key, orderId, this);
    }

    public void sendNetCancelOrder(String key, String orderId, String reason) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetCancelOrder(key, orderId, reason, this);
    }

    public void sendNetChangeSheep(String key, String orderId, String sheep) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetChangeSheep(key, orderId, sheep, this);
    }

    public void sendNetChangeOrderAmount(String key, String orderId, String amount,String remark) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetChangeAmount(key, orderId, amount,remark, this);
    }

    @Override
    public void getOrderDetail(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.getOrderDetailSuccess(result);
            } catch (Exception e) {
                LogUtil.logE(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void cancelOrder(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.cancelOrderSuccess(result);
            } catch (Exception e) {
                LogUtil.logE(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeSheep(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.changeSheepSuccess(result);
            } catch (Exception e) {
                LogUtil.logE(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeOrderAmount(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.changeOrderSuccess(result);
            } catch (Exception e) {
                LogUtil.logE(e);
                e.printStackTrace();
            }
        }
    }
}
