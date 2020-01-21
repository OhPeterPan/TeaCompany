package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberAuditCallback;

public interface IMemberAuditModel extends IModel {
    void sendNetMemberAudit(String key, IMemberAuditCallback callback);
}
