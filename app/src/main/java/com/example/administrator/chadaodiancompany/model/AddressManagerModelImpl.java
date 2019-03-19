package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IAddressManagerCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class AddressManagerModelImpl implements IAddressManagerModel {
    @Override
    public void sendNetAddressManager(String key, String addressId, String name, String phone,
                                      String areaId, String cityId, String region,
                                      String address, String company, String isDefault, final IAddressManagerCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ADDRESS_MANAGER_URL)
                .params("key", key)
                .params("address_id", addressId)
                .params("seller_name", name)
                .params("telphone", phone)
                .params("area_id", areaId)
                .params("city_id", cityId)
                .params("region", region)
                .params("address", address)
                .params("company", company)
                .params("is_default", isDefault)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getAddressManager(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }
}
