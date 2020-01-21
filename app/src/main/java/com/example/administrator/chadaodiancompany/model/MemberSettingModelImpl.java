package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberSettingCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class MemberSettingModelImpl implements IMemberSettingModel {
    @Override
    public void sendNetMemberSettingInfo(String key, final IMemberSettingCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MEMBER_SETTING_INFO_URL)
                .tag("MEMBER_SETTING_TAG")
                .params("key", key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMemberSettingInfo(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetSaveMemberSettingInfo(String key, String up, String num, String money, String down, final IMemberSettingCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MEMBER_SETTING_INFO_SAVE_URL)
                .tag("MEMBER_SETTING_TAG")
                .params("key", key)
                .params("up", up)
                .params("num", num)
                .params("money", money)
                .params("down", down)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getSaveMemberSettingInfo(response.body());
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
        OkGo.getInstance().cancelTag("MEMBER_SETTING_TAG");
    }
}
