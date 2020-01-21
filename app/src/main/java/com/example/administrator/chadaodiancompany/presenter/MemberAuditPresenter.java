package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.MemberAuditModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IMemberAuditCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberAuditView;

public class MemberAuditPresenter extends BasePresenter<IMemberAuditView> implements IMemberAuditCallback {

    private final MemberAuditModelImpl model;

    public MemberAuditPresenter(IMemberAuditView view) {
        super(view);
        model = new MemberAuditModelImpl();
    }

    public void sendNet(String key) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetMemberAudit(key, this);
    }

    @Override
    public void getMemberAuditList(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getMemberAuditListResult(result);
    }
}
