package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ILoginCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class LoginModelImpl implements ILoginModel {
    @Override
    public void sendNetLogin(String sellerName, String pwd, final ILoginCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.LOGIN_URL)
                .params("seller_name", sellerName)
                .params("password", pwd)
                .params("client", "android")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getLoginDetail(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }
}
