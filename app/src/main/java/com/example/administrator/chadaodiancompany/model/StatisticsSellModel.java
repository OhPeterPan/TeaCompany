package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IStatisticsSellCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class StatisticsSellModel implements IStatisticsSellModel {
    @Override
    public void sendNet(String key, String curPage, String sort, String startTime, String endTime, final IStatisticsSellCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.STATISTICS_SELL_URL)
                .tag("statistics_sell_tag")
                .params("key", key)
                .params("curpage", curPage)
                .params("page", "15")
                .params("sort", sort)
                .params("stime", startTime)
                .params("etime", endTime)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getSell(response.body());
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
        OkGo.getInstance().cancelTag("statistics_sell_tag");
    }
}
