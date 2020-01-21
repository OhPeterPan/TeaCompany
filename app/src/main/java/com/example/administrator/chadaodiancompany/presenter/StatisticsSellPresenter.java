package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.StatisticsSellModel;
import com.example.administrator.chadaodiancompany.netCallback.IStatisticsSellCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IStatisticsSellView;

public class StatisticsSellPresenter extends BasePresenter<IStatisticsSellView> implements IStatisticsSellCallback {

    private final StatisticsSellModel model;

    public StatisticsSellPresenter(IStatisticsSellView view) {
        super(view);
        model = new StatisticsSellModel();
    }

    public void sendNet(String key, int curPage, String sort, String startTime, String endTime, boolean showDialog) {
        if (mView != null && showDialog) mView.showLoading();
        if (model != null)
            model.sendNet(key, String.valueOf(curPage), sort, startTime, endTime, this);
    }

    @Override
    public void getSell(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getSellResult(result);
    }

    public void cancelTag() {
        model.cancelTag();
    }
}
