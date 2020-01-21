package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.StatisticSearchModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IStatisticSearchCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IStatisticSearchView;

public class StatisticSearchPresenter extends BasePresenter<IStatisticSearchView> implements IStatisticSearchCallback {

    private final StatisticSearchModelImpl model;

    public StatisticSearchPresenter(IStatisticSearchView view) {
        super(view);
        model = new StatisticSearchModelImpl();
    }

    public void sendNet(String key) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNet(key, this);
    }

    @Override
    public void getHistory(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getHistoryResult(result);
    }
}
