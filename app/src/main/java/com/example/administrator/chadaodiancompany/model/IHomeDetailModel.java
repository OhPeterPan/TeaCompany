package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IHomeDetailCallback;

public interface IHomeDetailModel {
    void sendNetGetHomeDetail(String key, IHomeDetailCallback callback);
}
