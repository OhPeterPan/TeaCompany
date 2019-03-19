package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IExpressManagerCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

public class ExpressManagerModelImpl implements IExpressManagerModel {
    @Override
    public void sendNetGetExpressList(String key, String orderId, final IExpressManagerCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.EXPRESS_LIST_URL)
                .params("key", key)
                .params("order_id", orderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getExpressDetail(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetSendGood(String key, HashMap<String, String> paramsMap, final IExpressManagerCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.SEND_GOOD_URL)
                .params("key", key)
                .params(paramsMap)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.sendGood(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }
}
