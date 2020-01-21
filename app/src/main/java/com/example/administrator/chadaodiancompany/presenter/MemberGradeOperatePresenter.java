package com.example.administrator.chadaodiancompany.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.model.MemberGradeOperateModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IMemberGradeOperateCallback;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberGradeOperateView;

public class MemberGradeOperatePresenter extends BasePresenter<IMemberGradeOperateView> implements IMemberGradeOperateCallback {

    private final MemberGradeOperateModelImpl model;

    public MemberGradeOperatePresenter(IMemberGradeOperateView view) {
        super(view);
        model = new MemberGradeOperateModelImpl();
    }

    public void sendNetGetGradeInfo(String key, String gradeId) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetMemberGradeOperate(key, gradeId, this);
    }

    public void sendNetEditGrade(String key, String gradeId, String gradeName, String money, String sort) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetEditMemberGrade(key, gradeId, gradeName, money, sort, this);
    }

    public void sendNetAddGrade(String key, String gradeName, String money, String sort) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetAddMemberGrade(key, gradeName, money, sort, this);
    }

    @Override
    public void getMemberGradeOperate(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.getMemberGradeOperateResult(result);
    }

    @Override
    public void editMemberGrade(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.editMemberGradeResult(result);
    }

    @Override
    public void addMemberGradeOperate(String result) {
        checkJson(result);
        if (mView != null && !isError)
            mView.addMemberGradeOperateResult(result);
    }

    public boolean checkParams(String gradeName, String money, String sort) {
        if (StringUtils.isEmpty(gradeName)) {
            ToastUtil.showError("请输入等级名称！");
            return false;
        }
        if (StringUtils.isEmpty(money)) {
            ToastUtil.showError("请输入等级对应金额！");
            return false;
        }
        if (StringUtils.isEmpty(sort)) {
            ToastUtil.showError("请输入排序数字！");
            return false;
        }
        return true;
    }
}
