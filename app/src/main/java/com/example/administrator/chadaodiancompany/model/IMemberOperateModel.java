package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberOperateCallback;

public interface IMemberOperateModel extends IModel {
    void sendNetGradeList(String key, IMemberOperateCallback callback);

    void changeMemberGrade(String key, String memberId, String gradeId, IMemberOperateCallback callback);

    void addMember(String key, String companyName, String gradeId, IMemberOperateCallback callback);
}
