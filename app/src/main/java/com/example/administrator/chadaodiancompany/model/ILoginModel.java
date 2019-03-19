package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.ILoginCallback;

public interface ILoginModel {
    void sendNetLogin(String sellerName, String pwd, ILoginCallback callback);
}
