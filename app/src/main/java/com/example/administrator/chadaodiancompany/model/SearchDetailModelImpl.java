package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ISearchDetailCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class SearchDetailModelImpl implements ISearchDetailModel {
    @Override
    public void sendNet(String key, String type, String keyword, String stcId, String startTime, String endTime, String curPage, final ISearchDetailCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.SEARCH_INFO_URL)
                .tag("good_search_detail_tag")
                .params("key", key)
                .params("type", type)
                .params("goods_name", keyword)
                .params("start_time", startTime)
                .params("end_time", endTime)
                .params("curpage", curPage)
                .params("page", "15")
                .params("stc_id", stcId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getGoodInfo(response.body());
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
        OkGo.getInstance().cancelTag("good_search_detail_tag");
    }
}
