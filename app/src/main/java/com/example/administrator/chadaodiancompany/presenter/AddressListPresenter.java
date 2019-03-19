package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.AddressModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IAddressCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IAddressModelView;

public class AddressListPresenter extends BasePresenter<IAddressModelView> implements IAddressCallback {

    private final AddressModelImpl model;

    public AddressListPresenter(IAddressModelView view) {
        super(view);
        model = new AddressModelImpl();
    }

    public void sendNetGetAddressList(String key) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetGetAddressList(key, this);
    }

    public void sendNetSetDefault(String key, String addressId) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetGetSetDefault(key, addressId, this);
    }

    public void sendNetDeleteAddress(String key, String addressId) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetDeleteAddress(key, addressId, this);
    }

    @Override
    public void getAddressList(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.getAddressListSuccess(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setAddressDefault(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.setAddressDefaultSuccess(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteAddress(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.deleteAddressSuccess(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
