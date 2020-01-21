package com.example.administrator.chadaodiancompany.netCallback;

public interface IMemberOperateCallback extends ICallback {
    void getGradeList(String result);

    void changeMemberGrade(String result);

    void addMember(String result);
}
