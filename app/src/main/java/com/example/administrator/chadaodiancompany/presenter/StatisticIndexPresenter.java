package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.StatisticIndexModel;
import com.example.administrator.chadaodiancompany.netCallback.IStatisticIndexCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IStatisticIndexView;

public class StatisticIndexPresenter extends BasePresenter<IStatisticIndexView> implements IStatisticIndexCallback {

    private final StatisticIndexModel model;

    public StatisticIndexPresenter(IStatisticIndexView view) {
        super(view);
        model = new StatisticIndexModel();
    }

    public void sendNet(String key, String searchType) {
        if (mView != null)
            mView.showLoading();

        if (model != null)
            model.sendNetStatistic(key, searchType, this);

    }

    @Override
    public void getStatistic(String result) {
        checkJson(result);
        if (mView != null && !isError) mView.getStatisticResult(result);
    }

    public void cancelTag() {
        model.cancelTag();
    }
}
