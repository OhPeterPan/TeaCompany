package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IAddressCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class AddressModelImpl implements IAddressModel {
    @Override
    public void sendNetGetAddressList(String key, final IAddressCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ORDER_ADDRESS_URL)
                .params("key", key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getAddressList(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetGetSetDefault(String key, String addressId, final IAddressCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ADDRESS_DEFAULT_URL)
                .params("key", key)
                .params("address_id", addressId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.setAddressDefault(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void sendNetDeleteAddress(String key, String addressId, final IAddressCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.ADDRESS_DELETE_URL)
                .params("key", key)
                .params("address_id", addressId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.deleteAddress(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }
}
