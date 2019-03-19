package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IGoodClassifyCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class GoodClassifyModelImpl implements IGoodClassifyModel {
    @Override
    public void sendNet(String key, final IGoodClassifyCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.GOOD_CLASSIFY_URL)
                .params("key", key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getGoodClassify(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }
}
