package com.example.administrator.chadaodiancompany.netCallback;

public interface IMemberAuditInfoCallback extends ICallback {
    void getMemberGradeList(String result);

    void auditResult(String result);
}
