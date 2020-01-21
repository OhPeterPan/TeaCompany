package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberGradeCallback;
import com.example.administrator.chadaodiancompany.presenter.IMemberGradeModel;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class MemberGradeModelImpl implements IMemberGradeModel {
    @Override
    public void sendNetList(String key, final IMemberGradeCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MEMBER_GRADE_LIST_URL)
                .tag("MEMBER_GRADE_INDEX")
                .params("key", key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMemberGradeList(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetDelGrade(String key, String gradeId, final IMemberGradeCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.DEL_MEMBER_GRADE_URL)
                .tag("MEMBER_GRADE_INDEX")
                .params("key", key)
                .params("grade_id", gradeId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.delMemberInfo(response.body());
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
        OkGo.getInstance().cancelTag("MEMBER_GRADE_INDEX");
    }
}
