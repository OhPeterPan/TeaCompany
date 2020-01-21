package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.MemberSettingModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IMemberSettingCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberSettingView;

public class MemberSettingPresenter extends BasePresenter<IMemberSettingView> implements IMemberSettingCallback {

    private final MemberSettingModelImpl model;

    public MemberSettingPresenter(IMemberSettingView view) {
        super(view);
        model = new MemberSettingModelImpl();
    }

    public void sendNetGetSetInfo(String key) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetMemberSettingInfo(key, this);
    }

    public void sendNetSaveInfo(String key, String up, String num, String money, String down) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetSaveMemberSettingInfo(key, up, num, money, down, this);
    }

    @Override
    public void getMemberSettingInfo(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getMemberSettingInfoResult(result);
    }

    @Override
    public void getSaveMemberSettingInfo(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getSaveMemberSettingInfoResult(result);
    }
}
