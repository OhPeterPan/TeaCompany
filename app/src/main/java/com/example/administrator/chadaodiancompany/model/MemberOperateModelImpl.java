package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberOperateCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class MemberOperateModelImpl implements IMemberOperateModel {
    @Override
    public void sendNetGradeList(String key, final IMemberOperateCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MEMBER_GRADE_LIST_URL)
                .tag("MEMBER_GRADE")
                .params("key", key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getGradeList(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void changeMemberGrade(String key, String memberId, String gradeId, final IMemberOperateCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MEMBER_CHANGE_MEMBER_URL)
                .tag("MEMBER_GRADE")
                .params("key", key)
                .params("member_id", memberId)
                .params("grade_id", gradeId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.changeMemberGrade(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void addMember(String key, String companyName, String gradeId, final IMemberOperateCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ADD_MEMBER_URL)
                .tag("MEMBER_GRADE")
                .params("key", key)
                .params("company_name", companyName)
                .params("grade_id", gradeId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.addMember(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void cancelTag() {
        OkGo.getInstance().cancelTag("MEMBER_GRADE");
    }
}
