package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IInteractMessageCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class InteractMessageModelImpl implements IInteractMessageModel {
    @Override
    public void sendNetInteractDetail(String key, String uId, String curPage, final IInteractMessageCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.INTERACT_MESSAGE_DETAIL_URL)
                .tag("INTERACT_MESSAGE_TAG")
                .params("key", key)
                .params("u_id", uId)
                .params("curpage", curPage)
                .params("page", "15")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getInteractDetail(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetSendInteractMessage(String key, String uId, String uName, String content, final IInteractMessageCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.INTERACT_MESSAGE_SEND_URL)
                .tag("INTERACT_MESSAGE_TAG")
                .params("key", key)
                .params("u_id", uId)
                .params("u_name", uName)
                .params("t_msg", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.sendMessage(response.body());
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
        OkGo.getInstance().cancelTag("INTERACT_MESSAGE_TAG");
    }
}
