package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IHomeDetailCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class HomeInfoModelImpl implements IHomeInfoModel {
    @Override
    public void sendNetHomeInfo(String key, final IHomeDetailCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.HOME_INFO_URL)
                .tag("HOME")
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
    public void cancelNet(){
        OkGo.getInstance().cancelTag("HOME");
    }

}
