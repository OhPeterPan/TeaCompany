package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberAuditInfoCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class MemberAuditInfoModelImpl implements IMemberAuditInfoModel {
    @Override
    public void sendNetList(String key, final IMemberAuditInfoCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MEMBER_GRADE_LIST_URL)
                .tag("MEMBER_AUDIT_TAG")
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
    public void sendNetList(String key, String audit_id, String yesno, String group_id, String content, final IMemberAuditInfoCallback callback) {

        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MEMBER_AUDIT_RESULT_URL)
                .tag("MEMBER_AUDIT_TAG")
                .params("key", key)
                .params("audit_id", audit_id)
                .params("yesno", yesno)
                .params("group_id", group_id)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.auditResult(response.body());
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
        OkGo.getInstance().cancelTag("MEMBER_AUDIT_TAG");
    }
}
