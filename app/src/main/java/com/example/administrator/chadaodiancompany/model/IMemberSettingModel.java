package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberSettingCallback;

public interface IMemberSettingModel extends IModel {
    void sendNetMemberSettingInfo(String key, IMemberSettingCallback callback);

    void sendNetSaveMemberSettingInfo(String key, String up, String num, String money, String down, IMemberSettingCallback callback);
}
