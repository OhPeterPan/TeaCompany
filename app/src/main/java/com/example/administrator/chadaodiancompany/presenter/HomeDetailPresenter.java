package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.HomeDetailModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IHomeDetailCallback;
import com.example.administrator.chadaodiancompany.view.IHomeDetailView;

public class HomeDetailPresenter extends BasePresenter<IHomeDetailView> implements IHomeDetailCallback {

    private final HomeDetailModelImpl model;

    public HomeDetailPresenter(IHomeDetailView view) {
        super(view);
        model = new HomeDetailModelImpl();
    }

    public void sendNet(String key, boolean showDialog) {
        if (mView != null && showDialog) mView.showLoading();
        if (model != null) model.sendNetGetHomeDetail(key, this);
    }

    @Override
    public void getHomeDetail(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            mView.getHomeDetailSuccess(result);
        }
    }
}
