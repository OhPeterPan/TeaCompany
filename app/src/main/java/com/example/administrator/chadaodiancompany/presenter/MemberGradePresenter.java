package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.MemberGradeModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IMemberGradeCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberGradeView;

public class MemberGradePresenter extends BasePresenter<IMemberGradeView> implements IMemberGradeCallback {

    private final MemberGradeModelImpl model;

    public MemberGradePresenter(IMemberGradeView view) {
        super(view);
        model = new MemberGradeModelImpl();
    }

    public void sendNetMemberList(String key) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetList(key, this);
    }

    public void sendNetDelMember(String key, String gradeId) {
        if (mView != null)
            mView.showLoading();
        if (model != null) {
            model.sendNetDelGrade(key, gradeId, this);
        }
    }

    @Override
    public void getMemberGradeList(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getMemberGradeListResult(result);
    }

    @Override
    public void delMemberInfo(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.delMemberInfoResult(result);
    }

}
