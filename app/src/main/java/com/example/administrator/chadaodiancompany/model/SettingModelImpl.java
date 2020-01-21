package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ISettingCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class SettingModelImpl implements ISettingModel {
    @Override
    public void sendNetUpdateApp(final ISettingCallback callback) {
        OkGo.<String>get(NetUtils.BASEURLCOM + NetUtils.APP_UPDATE_URL)
                .tag("UPDATE_APP_TAG")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.updateVersion(response.body());
                    }
                });
    }

    @Override
    public void cancelTag() {
        OkGo.getInstance().cancelTag("UPDATE_APP_TAG");
    }
}
