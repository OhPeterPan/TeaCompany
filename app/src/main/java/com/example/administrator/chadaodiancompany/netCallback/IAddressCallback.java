package com.example.administrator.chadaodiancompany.netCallback;

public interface IAddressCallback extends ICallback {
    void getAddressList(String result);

    void setAddressDefault(String result);

    void deleteAddress(String result);
}
