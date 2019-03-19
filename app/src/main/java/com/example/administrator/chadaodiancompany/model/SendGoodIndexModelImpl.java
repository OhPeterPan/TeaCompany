package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ISendGoodCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class SendGoodIndexModelImpl implements ISendGoodIndexModel {
    @Override
    public void sendNetGetOrderDetail(String key, String orderId, final ISendGoodCallback callback) {
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
}
