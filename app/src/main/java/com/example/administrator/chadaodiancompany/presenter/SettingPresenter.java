package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.SettingModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.ISettingCallback;
import com.example.administrator.chadaodiancompany.viewImpl.ISettingView;

public class SettingPresenter extends BasePresenter<ISettingView> implements ISettingCallback {

    private final SettingModelImpl model;

    public SettingPresenter(ISettingView view) {
        super(view);
        model = new SettingModelImpl();
    }

    public void sendNetUpdateApp() {

        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetUpdateApp(this);
    }

    @Override
    public void updateVersion(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.updateVersionResult(result);
    }
}
