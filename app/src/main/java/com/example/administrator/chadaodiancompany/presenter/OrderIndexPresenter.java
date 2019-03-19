package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.OrderModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IOrderCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IOrderView;

public class OrderIndexPresenter extends BasePresenter<IOrderView> implements IOrderCallback {

    private final OrderModelImpl model;

    public OrderIndexPresenter(IOrderView view) {
        super(view);
        model = new OrderModelImpl();
    }

    public void sendNetGetOrderList(String key, int curPage, String orderState, String keyword, boolean showDialog) {
        if (mView != null && showDialog) mView.showLoading();
        if (model != null) model.sendNetGetOrderList(key, curPage + "", orderState, keyword, this);
    }

    public void sendNetCancelOrder(String key, String orderId, String reason) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetCancelOrder(key, orderId, reason, this);
    }

    public void sendNetChangeSheep(String key, String orderId, String sheep) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetChangeSheep(key, orderId, sheep, this);
    }

    public void sendNetChangeOrderAmount(String key, String orderId, String amount, String remark) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetChangeAmount(key, orderId, amount,remark, this);
    }

    @Override
    public void getOrderList(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            mView.getOrderListSuccess(result);
        }
    }

    @Override
    public void cancelOrder(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            mView.cancelOrderSuccess(result);
        }
    }

    @Override
    public void changeOrderAmount(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            mView.changeOrderAmountSuccess(result);
        }
    }

    @Override
    public void changeSheep(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            mView.changeSheepSuccess(result);
        }
    }


}
