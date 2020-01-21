package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ISingleGoodAnalyseCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class SingleGoodAnalyseModelImpl implements ISingleGoodAnalyseModel {
    @Override
    public void sendNet(String key, String goodId, String startTime, String endTime, final ISingleGoodAnalyseCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.SINGLE_GOOD_INFO_URL)
                .tag("single_good_info")
                .params("key", key)
                .params("goods_id", goodId)
                .params("start_time", startTime)
                .params("end_time", endTime)
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
        OkGo.getInstance().cancelTag("single_good_info");
    }
}
