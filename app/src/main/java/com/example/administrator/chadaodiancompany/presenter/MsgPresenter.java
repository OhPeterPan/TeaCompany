package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.MsgModel;
import com.example.administrator.chadaodiancompany.netCallback.IMsgCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IMsgView;

public class MsgPresenter extends BasePresenter<IMsgView> implements IMsgCallback {

    private final MsgModel model;

    public MsgPresenter(IMsgView view) {
        super(view);
        model = new MsgModel();
    }

    public void sendNetGetMsgList(String key, int curPage, boolean showDialog) {
        if (mView != null && showDialog) mView.showLoading();
        if (model != null) model.sendNetMsgList(key, curPage + "", this);
    }


    public void sendNetRedMsg(String key, String smId) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetMsgRead(key, smId, this);
    }

    public void sendNetRedMsgTag(String key, String smIds) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetMsgReadTag(key, smIds, this);
    }

    public void sendNetDeleteMsg(String key, String smIds) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetMsgDelete(key, smIds, this);
    }

    @Override
    public void getMsgList(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.getMsgListSuccess(result);
        }
    }

    @Override
    public void getMsgRead(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.getMsgReadSuccess(result);
        }
    }

    @Override
    public void getMsgReadTag(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.getMsgReadTagSuccess(result);
        }
    }

    @Override
    public void deleteMsg(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.deleteMsgSuccess(result);
        }
    }

}
