package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.MessageModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IMessageCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IMessageView;

public class MessagePresenter extends BasePresenter<IMessageView> implements IMessageCallback {

    private final MessageModelImpl model;

    public MessagePresenter(IMessageView view) {
        super(view);
        model = new MessageModelImpl();
    }

    public void sendNetGetMessageResult(String key, int curPage, boolean showDialog) {
        if (mView != null && showDialog)
            mView.showLoading();
        if (model != null)
            model.sendNetMessageList(key, String.valueOf(curPage), this);
    }

    public void sendNetGetInteractMessageResult(String key, int curPage, boolean showDialog) {
        if (mView != null && showDialog)
            mView.showLoading();
        if (model != null)
            model.sendNetInteractMessage(key, String.valueOf(curPage), this);
    }

    public void sendNetReadMessage(String key, String smId) {
        if (model != null)
            model.sendNetReadMessage(key, smId, this);
    }

    @Override
    public void getMessageList(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getMessageListResult(result);
    }

    @Override
    public void delMessageInfo(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.delMessageInfoResult(result);
    }

    @Override
    public void readTag(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.readTagResult(result);
    }

    @Override
    public void readTags(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.readTagsResult(result);
    }

    @Override
    public void getInteractMessage(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getInteractMessageResult(result);
    }

    public void sendNetDelMsg(String key, String msgIds) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetDelMessage(key, msgIds, this);

    }

    public void cancelTag() {
        if (model != null)
            model.cancelTag();
    }
}
