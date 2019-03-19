package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.LoginModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.ILoginCallback;
import com.example.administrator.chadaodiancompany.viewImpl.ILoginView;

public class LoginPresenter extends BasePresenter<ILoginView> implements ILoginCallback {

    private final LoginModelImpl model;

    public LoginPresenter(ILoginView view) {
        super(view);
        model = new LoginModelImpl();
    }

    public void sendNetLogin(String userName, String pwd) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetLogin(userName, pwd, this);
    }

    @Override
    public void getLoginDetail(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            mView.getLoginResult(result);
        }
    }
}
