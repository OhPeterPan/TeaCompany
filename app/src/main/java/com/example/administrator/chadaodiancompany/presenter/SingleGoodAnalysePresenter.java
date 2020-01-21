package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.SingleGoodAnalyseModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.ISingleGoodAnalyseCallback;
import com.example.administrator.chadaodiancompany.viewImpl.ISingleGoodAnalyseView;

public class SingleGoodAnalysePresenter extends BasePresenter<ISingleGoodAnalyseView> implements ISingleGoodAnalyseCallback {

    private final SingleGoodAnalyseModelImpl model;

    public SingleGoodAnalysePresenter(ISingleGoodAnalyseView view) {
        super(view);
        model = new SingleGoodAnalyseModelImpl();
    }

    public void sendNet(String key, String goodId, String startTime, String endTime) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNet(key, goodId, startTime, endTime, this);
    }

    @Override
    public void getGoodInfo(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.getGoodInfoResult(result);
        }
    }
}
