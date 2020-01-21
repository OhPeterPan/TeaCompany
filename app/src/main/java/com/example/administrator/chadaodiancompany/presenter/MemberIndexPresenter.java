package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.MemberIndexModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IMemberIndexCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberIndexView;

import java.io.File;

public class MemberIndexPresenter extends BasePresenter<IMemberIndexView> implements IMemberIndexCallback {

    private final MemberIndexModelImpl model;

    public MemberIndexPresenter(IMemberIndexView view) {
        super(view);
        model = new MemberIndexModelImpl();
    }

    public void sendNet(String key, String keyword, String sort, int curPage, boolean showDialog) {
        if (mView != null && showDialog)
            mView.showLoading();

        if (model != null)
            model.sendNetMemberList(key, keyword, sort, String.valueOf(curPage), this);
    }

    public void sendNetGetFile(String key, String type,String destFileName) {
        if (mView != null)
            mView.showLoading();

        if (model != null)
            model.sendNetOutExcel(key, type,destFileName, this);
    }

    public void sendNetDelMember(String key, String memberId) {
        if (mView != null)
            mView.showLoading();

        if (model != null)
            model.sendNetDelMember(key, memberId, this);
    }

    @Override
    public void memberResult(String result) {
        checkJson(result);
        if (!isError && mView != null) {
            mView.memberResultSucc(result);
        }
    }

    @Override
    public void delMemberResult(String result) {
        checkJson(result);
        if (!isError && mView != null) {
            mView.delMemberResultSucc(result);
        }
    }

    @Override
    public void downloadFileExcel(File file) {
        if (mView != null) {
            mView.hideLoading();
            mView.downloadFileExcelSucc(file);
        }
    }

    public void cancelTag() {
        model.cancelTag();
    }


}
