package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IHomeDetailCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class HomeDetailModelImpl implements IHomeDetailModel {
    @Override
    public void sendNetGetHomeDetail(String key, final IHomeDetailCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.HOME_URL)
                .params("key", key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getHomeDetail(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }
}
