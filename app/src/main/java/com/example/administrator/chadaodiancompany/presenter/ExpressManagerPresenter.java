package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.ExpressManagerModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IExpressManagerCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IExpressManagerView;

import java.util.HashMap;

public class ExpressManagerPresenter extends BasePresenter<IExpressManagerView> implements IExpressManagerCallback {

    private final ExpressManagerModelImpl model;

    public ExpressManagerPresenter(IExpressManagerView view) {
        super(view);
        model = new ExpressManagerModelImpl();
    }

    public void sendNetGetExpressList(String key, String orderId) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetGetExpressList(key, orderId, this);
    }

    public void sendNetSendGood(String key, HashMap<String, String> hasMap) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetSendGood(key, hasMap, this);
    }

    @Override
    public void getExpressDetail(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.getExpressDetailSuccess(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendGood(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.sendGoodSuccess(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
