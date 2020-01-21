package com.example.administrator.chadaodiancompany.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.model.MemberOperateModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IMemberOperateCallback;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberOperateView;

public class MemberOperatePresenter extends BasePresenter<IMemberOperateView> implements IMemberOperateCallback {

    private final MemberOperateModelImpl model;

    public MemberOperatePresenter(IMemberOperateView view) {
        super(view);
        model = new MemberOperateModelImpl();
    }

    public void sendNetGradeList(String key) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetGradeList(key, this);
    }

    public void sendNetChangeMember(String key, String memberId, String gradeId) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.changeMemberGrade(key, memberId, gradeId, this);
    }

    public void sendNetAddMember(String key, String companyName, String gradeId) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.addMember(key, companyName, gradeId, this);
    }

    @Override
    public void getGradeList(String result) {
        checkJson(result);
        if (!isError && mView != null)
            mView.getGradeListResult(result);
    }

    @Override
    public void changeMemberGrade(String result) {
        checkJson(result);
        if (!isError && mView != null)
            mView.changeMemberGradeResult(result);
    }

    @Override
    public void addMember(String result) {
        checkJson(result);
        if (!isError && mView != null)
            mView.addMemberResult(result);
    }


    public boolean checkParams(String companyName, String gradeId) {
        if (StringUtils.isEmpty(companyName)) {
            ToastUtil.showError("请输入公司名称！");
            return false;
        }
        if (StringUtils.isEmpty(gradeId)) {
            ToastUtil.showError("请选择公司等级！");
            return false;
        }
        return true;
    }

    public void cancelTag() {
        if (model != null) {
            model.cancelTag();
        }
    }
}
