package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMessageCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class MessageModelImpl implements IMessageModel {
    @Override
    public void sendNetInteractMessage(String key, String curPage, final IMessageCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.INTERACT_MESSAGE_URL)
                .tag("MESSAGE_TAG")
                .params("key", key)
                .params("curpage", curPage)
                .params("page", "15")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getInteractMessage(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetMessageList(String key, String curPage, final IMessageCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.MESSAGE_URL)
                .tag("MESSAGE_TAG")
                .params("key", key)
                .params("curpage", curPage)
                .params("page", "15")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMessageList(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetDelMessage(String key, String messageIds, final IMessageCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MESSAGE_DELETE_URL)
                .tag("MESSAGE_TAG")
                .params("key", key)
                .params("smids", messageIds)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.delMessageInfo(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetReadMessage(String key, String messageId, final IMessageCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MSG_READ_URL)
                .tag("MESSAGE_TAG")
                .params("key", key)
                .params("sm_id", messageId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.readTag(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetReadMessageList(String key, String messageIds, final IMessageCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MESSAGE_READ_TAG_URL)
                .tag("MESSAGE_TAG")
                .params("key", key)
                .params("smids", messageIds)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.readTags(response.body());
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
        OkGo.getInstance().cancelTag("MESSAGE_TAG");
    }
}
