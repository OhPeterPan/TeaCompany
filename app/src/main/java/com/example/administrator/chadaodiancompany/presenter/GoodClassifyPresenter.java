package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.GoodClassifyModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IGoodClassifyCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IGoodClassifyView;

public class GoodClassifyPresenter extends BasePresenter<IGoodClassifyView> implements IGoodClassifyCallback {

    private final GoodClassifyModelImpl model;

    public GoodClassifyPresenter(IGoodClassifyView view) {
        super(view);
        model = new GoodClassifyModelImpl();
    }

    public void sendNet(String key) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNet(key, this);
    }

    @Override
    public void getGoodClassify(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            mView.getGoodClassifySuccess(result);
        }
    }
}
