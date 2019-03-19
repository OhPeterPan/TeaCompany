package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ILookExpressCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class LookExpressModelImpl implements ILookExpressModel {
    @Override
    public void sendNetLookExpress(String key, String orderId, String num, final ILookExpressCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.EXPRESS_URL)
                .params("key", key)
                .params("order_id", orderId)
                .params("deliver_num", num)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getExpressList(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }
}
