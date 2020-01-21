package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ISettingCallback;

public interface ISettingModel extends IModel {
    void sendNetUpdateApp(ISettingCallback callback);
}
