package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.InteractMessageModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IInteractMessageCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IInteractMessageView;

public class InteractMessagePresenter extends BasePresenter<IInteractMessageView> implements IInteractMessageCallback {

    private final InteractMessageModelImpl model;

    public InteractMessagePresenter(IInteractMessageView view) {
        super(view);
        model = new InteractMessageModelImpl();
    }

    public void sendNetGetInteractDetail(String key, String uId, int curPage, boolean showDialog) {
        if (mView != null && showDialog)
            mView.showLoading();
        if (model != null)
            model.sendNetInteractDetail(key, uId, String.valueOf(curPage), this);
    }

    public void sendNetContent(String key, String uId, String uName, String content) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetSendInteractMessage(key, uId, uName, content, this);
    }

    @Override
    public void getInteractDetail(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getInteractDetailResult(result);
    }

    @Override
    public void sendMessage(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.sendMessageResult(result);
    }


}
