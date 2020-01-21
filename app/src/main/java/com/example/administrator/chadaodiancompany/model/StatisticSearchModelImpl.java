package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IStatisticSearchCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class StatisticSearchModelImpl implements IStatisticSearchModel {
    @Override
    public void sendNet(String key, final IStatisticSearchCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.STATISTIC_SEARCH_URL)
                .tag("statistic_search_tag")
                .params("key", key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getHistory(response.body());
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
        OkGo.getInstance().cancelTag("statistic_search_tag");
    }
}
