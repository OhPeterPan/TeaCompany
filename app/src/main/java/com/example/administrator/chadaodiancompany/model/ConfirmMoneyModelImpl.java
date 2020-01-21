package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IConfirmMoneyCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class ConfirmMoneyModelImpl implements IConfirmMoneyModel {
    @Override
    public void sendNet(String key, final IConfirmMoneyCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.PAY_METHOD_URL)
                .tag("PAY_METHOD_TAG")
                .params("key", key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getPayMethod(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetPay(String key, String time, String payCode, String remark, final IConfirmMoneyCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.CONFIRM_PAY_URL)
                .tag("PAY_METHOD_TAG")
                .params("key", key)
                .params("payment_time", time)
                .params("payment_code", payCode)
                .params("trade_no", remark)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.confirmPay(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void cancelTag() {
        OkGo.getInstance().cancelTag("PAY_METHOD_TAG");
    }
}
