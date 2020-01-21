package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberIndexCallback;

public interface IMemberIndexModel extends IModel {
    void sendNetMemberList(String key, String c_name, String sort, String curPage, IMemberIndexCallback callback);

    void sendNetDelMember(String key, String memberId, IMemberIndexCallback callback);

    void sendNetOutExcel(String key, String type, String destFileName, IMemberIndexCallback callback);
}
