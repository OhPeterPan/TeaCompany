package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberGradeOperateCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class MemberGradeOperateModelImpl implements IMemberGradeOperateModel {
    @Override
    public void sendNetMemberGradeOperate(String key, String gradeId, final IMemberGradeOperateCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MEMBER_GRADE_INFO_URL)
                .tag("MEMBER_GRADE_OPERATE_TAG")
                .params("key", key)
                .params("grade_id", gradeId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMemberGradeOperate(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetEditMemberGrade(String key, String gradeId, String gradeName, String money, String sort, final IMemberGradeOperateCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.EDIT_MEMBER_GRADE_URL)
                .tag("MEMBER_GRADE_OPERATE_TAG")
                .params("key", key)
                .params("grade_id", gradeId)
                .params("grade_name", gradeName)
                .params("money", money)
                .params("sort", sort)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.editMemberGrade(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetAddMemberGrade(String key, String gradeName, String money, String sort, final IMemberGradeOperateCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ADD_MEMBER_GRADE_URL)
                .tag("MEMBER_GRADE_OPERATE_TAG")
                .params("key", key)
                .params("grade_name", gradeName)
                .params("money", money)
                .params("sort", sort)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.addMemberGradeOperate(response.body());
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
        OkGo.getInstance().cancelTag("MEMBER_GRADE_OPERATE_TAG");
    }
}
