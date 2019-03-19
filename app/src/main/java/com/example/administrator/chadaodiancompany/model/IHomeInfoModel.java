package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IHomeDetailCallback;

public interface IHomeInfoModel {
    void sendNetHomeInfo(String key, IHomeDetailCallback callback);
}
