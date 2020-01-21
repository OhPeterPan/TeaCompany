package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.IModel;
import com.example.administrator.chadaodiancompany.netCallback.IMemberGradeCallback;

public interface IMemberGradeModel extends IModel {
    void sendNetList(String key, IMemberGradeCallback callback);

    void sendNetDelGrade(String key, String gradeId, IMemberGradeCallback callback);
}
