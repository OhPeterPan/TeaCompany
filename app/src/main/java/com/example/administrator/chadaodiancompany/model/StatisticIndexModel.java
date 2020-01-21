package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IStatisticIndexCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class StatisticIndexModel implements IStatisticIndexModel {
    @Override
    public void sendNetStatistic(String key, String searchType, final IStatisticIndexCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.STATISTIC_INDEX_URL)
                .tag("statistic_tag")
                .params("key", key)
                .params("search_type", searchType)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getStatistic(response.body());
                    }
                });
    }

    @Override
    public void cancelTag() {
        OkGo.getInstance().cancelTag("statistic_tag");
    }
}
