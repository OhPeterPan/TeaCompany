package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.MemberAuditInfoModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IMemberAuditInfoCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberAuditInfoView;

public class MemberAuditInfoPresenter extends BasePresenter<IMemberAuditInfoView> implements IMemberAuditInfoCallback {

    private final MemberAuditInfoModelImpl model;

    public MemberAuditInfoPresenter(IMemberAuditInfoView view) {
        super(view);
        model = new MemberAuditInfoModelImpl();
    }

    public void sendNetAuditResult(String key, String auditId, String result, String gradeId, String reason) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetList(key, auditId, result, gradeId, reason, this);
    }

    public void sendNetGradeList(String key) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetList(key, this);
    }

    @Override
    public void getMemberGradeList(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getMemberGradeList(result);
    }

    @Override
    public void auditResult(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.auditResult(result);

    }
}
