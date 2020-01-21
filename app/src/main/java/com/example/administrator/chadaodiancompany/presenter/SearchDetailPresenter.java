package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.SearchDetailModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.ISearchDetailCallback;
import com.example.administrator.chadaodiancompany.viewImpl.ISearchDetailView;

public class SearchDetailPresenter extends BasePresenter<ISearchDetailView> implements ISearchDetailCallback {

    private final SearchDetailModelImpl model;

    public SearchDetailPresenter(ISearchDetailView view) {
        super(view);
        model = new SearchDetailModelImpl();
    }

    public void sendNet(String key, String type, String keyword, String stcId, String startTime, String endTime, int curPage, boolean showDialog) {
        if (mView != null && showDialog)
            mView.showLoading();
        if (model != null)
            model.sendNet(key, type, keyword, stcId, startTime, endTime, String.valueOf(curPage), this);
    }

    @Override
    public void getGoodInfo(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getGoodInfoResult(result);
    }
}
