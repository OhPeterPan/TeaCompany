package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMsgCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class MsgModel implements IMsgModel {
    @Override
    public void sendNetMsgList(String key, String curPage, final IMsgCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.MESSAGE_URL)
                .params("key", key)
                .params("curpage", curPage)
                .params("page", "10")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMsgList(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetMsgRead(String key, String msgId, final IMsgCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MSG_READ_URL)
                .params("key", key)
                .params("sm_id", msgId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMsgRead(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetMsgReadTag(String key, String msgIds, final IMsgCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MESSAGE_READ_TAG_URL)
                .params("key", key)
                .params("smids", msgIds)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMsgReadTag(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetMsgDelete(String key, String msgIds, final IMsgCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MESSAGE_DELETE_URL)
                .params("key", key)
                .params("smids", msgIds)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.deleteMsg(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }
}
