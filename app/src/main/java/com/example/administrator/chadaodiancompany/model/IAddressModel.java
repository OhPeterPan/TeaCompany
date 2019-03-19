package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IAddressCallback;

public interface IAddressModel {
    void sendNetGetAddressList(String key, IAddressCallback callback);

    void sendNetGetSetDefault(String key, String addressId, IAddressCallback callback);

    void sendNetDeleteAddress(String key, String addressId, IAddressCallback callback);
}
