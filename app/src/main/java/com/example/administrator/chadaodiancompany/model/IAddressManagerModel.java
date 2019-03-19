package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IAddressManagerCallback;

public interface IAddressManagerModel {
    void sendNetAddressManager(String key, String addressId,
                               String name, String phone, String areaId, String cityId,
                               String region, String address, String company, String isDefault, IAddressManagerCallback callback);
}
