package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.HomeInfoModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IHomeDetailCallback;
import com.example.administrator.chadaodiancompany.view.IHomeDetailView;

public class HomeInfoPresenter extends BasePresenter<IHomeDetailView> implements IHomeDetailCallback {

    private final HomeInfoModelImpl model;

    public HomeInfoPresenter(IHomeDetailView view) {
        super(view);
        model = new HomeInfoModelImpl();
    }

    public void sendNetGetHomeInfo(String key, boolean isShowDialog) {
        if (mView != null && isShowDialog) mView.showLoading();
        if (model != null) model.sendNetHomeInfo(key, this);
    }

    @Override
    public void getHomeDetail(String result) {
        checkJson(result);
        if (!isError && mView != null) {
            mView.hideLoading();
            mView.getHomeDetailSuccess(result);
        }
    }

    public void cancelNet() {
        if (model != null)
            model.cancelNet();
    }
}
