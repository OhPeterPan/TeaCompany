package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.LookExpressModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.ILookExpressCallback;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.viewImpl.ILookExpressView;

public class LookExpressPresenter extends BasePresenter<ILookExpressView> implements ILookExpressCallback {

    private final LookExpressModelImpl model;

    public LookExpressPresenter(ILookExpressView view) {
        super(view);
        model = new LookExpressModelImpl();
    }

    public void sendNetGetExpressList(String key, String orderId, String num) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetLookExpress(key, orderId, num, this);
    }

    @Override
    public void getExpressList(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.getExpressListSuccess(result);
            } catch (Exception e) {
                LogUtil.logE(e);
                e.printStackTrace();
            }
        }
    }
}
