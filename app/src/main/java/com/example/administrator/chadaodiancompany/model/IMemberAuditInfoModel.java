package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberAuditInfoCallback;

public interface IMemberAuditInfoModel extends IModel {
    void sendNetList(String key, IMemberAuditInfoCallback callback);

    void sendNetList(String key, String audit_id, String yesno, String group_id, String content, IMemberAuditInfoCallback callback);
}
