package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.GoodIndexModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IGoodIndexCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IGoodIndexView;

public class GoodIndexPresenter extends BasePresenter<IGoodIndexView> implements IGoodIndexCallback {

    private final GoodIndexModelImpl model;

    public GoodIndexPresenter(IGoodIndexView view) {
        super(view);
        model = new GoodIndexModelImpl();
    }

    public void sendNet(String key, int curPage, String stcId, String keyWord, boolean showDialog) {

        if (mView != null && showDialog) mView.showLoading();
        if (model != null) model.sendNetGetGoodDetail(key, curPage + "", stcId, keyWord, this);
    }

    public void sendNetBanGood(String key, String goodCommonId) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetGetGoodBan(key, goodCommonId, this);
    }

    public void sendNetChangeGoodNumber(String key, String goodsCommonId, String number) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetChangeGoodNumber(key, goodsCommonId, number, this);
    }


    @Override
    public void getGoodList(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            mView.getGoodListSuccess(result);
        }
    }

    @Override
    public void getBanGoodDetail(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            mView.getBanGoodDetailSuccess(result);
        }
    }

    @Override
    public void changeGoodNumber(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            mView.changeGoodNumberSuccess(result);
        }
    }
}
