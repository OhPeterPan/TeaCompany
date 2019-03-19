package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IGoodIndexCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class GoodIndexModelImpl implements IGoodIndexModel {
    @Override
    public void sendNetGetGoodDetail(String key, String curPage, String stcId, String keyWord, final IGoodIndexCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.GOOD_URL)
                .params("key", key)
                .params("curpage", curPage)
                .params("stc_id", stcId)
                .params("keyword", keyWord)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getGoodList(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetGetGoodBan(String key, String goodCommonId, final IGoodIndexCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.GOOD_BAN_URL)
                .params("key", key)
                .params("commonid", goodCommonId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getBanGoodDetail(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetChangeGoodNumber(String key, String goodsCommonId, String number, final IGoodIndexCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.GOOD_STORAGE_URL)
                .params("key", key)
                .params("commonid", goodsCommonId)
                .params("goods_storage", number)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.changeGoodNumber(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }
}
