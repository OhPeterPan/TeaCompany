package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberGradeOperateCallback;

public interface IMemberGradeOperateModel extends IModel {
    void sendNetMemberGradeOperate(String key, String gradeId, IMemberGradeOperateCallback callback);

    void sendNetEditMemberGrade(String key, String gradeId, String gradeName, String money, String sort, IMemberGradeOperateCallback callback);

    void sendNetAddMemberGrade(String key, String gradeName, String money, String sort, IMemberGradeOperateCallback callback);
}
