package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IGoodClassifyCallback;

public interface IGoodClassifyModel {
    void sendNet(String key, IGoodClassifyCallback callback);
}
