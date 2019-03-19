package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IOrderDetailCallback;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class OrderDetailModelImpl implements IOrderDetailModel {
    @Override
    public void sendNetOrderDetail(String key, String orderId, final IOrderDetailCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ORDER_DETAIL_URL)
                .params("key", key)
                .params("order_id", orderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getOrderDetail(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetCancelOrder(String key, String orderId, String reason, final IOrderDetailCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ORDER_CANCEL_URL)
                .tag(CommonUtil.OkGoTag.ORDER_TAG)
                .params("key", key)
                .params("order_id", orderId)
                .params("reason", reason)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.cancelOrder(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetChangeSheep(String key, String orderId, String sheep, final IOrderDetailCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ORDER_SHEEP_URL)
                .tag(CommonUtil.OkGoTag.ORDER_TAG)
                .params("key", key)
                .params("order_id", orderId)
                .params("shipping_fee", sheep)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.changeSheep(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetChangeAmount(String key, String orderId, String amount, String remark, final IOrderDetailCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ORDER_AMOUNT_URL)
                .tag(CommonUtil.OkGoTag.ORDER_TAG)
                .params("key", key)
                .params("order_id", orderId)
                .params("goods_amount", amount)
                .params("remark", remark)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.changeOrderAmount(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }
}
