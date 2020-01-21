package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberAuditCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class MemberAuditModelImpl implements IMemberAuditModel {
    @Override
    public void sendNetMemberAudit(String key, final IMemberAuditCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ADD_MEMBER_AUDIT_URL)
                .tag("MEMBER_AUDIT_TAG")
                .params("key", key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMemberAuditList(response.body());
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
