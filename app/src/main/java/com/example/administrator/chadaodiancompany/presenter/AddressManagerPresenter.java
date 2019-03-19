package com.example.administrator.chadaodiancompany.presenter;

import com.example.administrator.chadaodiancompany.model.AddressManagerModelImpl;
import com.example.administrator.chadaodiancompany.netCallback.IAddressManagerCallback;
import com.example.administrator.chadaodiancompany.viewImpl.IAddressManagerView;

public class AddressManagerPresenter extends BasePresenter<IAddressManagerView> implements IAddressManagerCallback {

    private final AddressManagerModelImpl model;

    public AddressManagerPresenter(IAddressManagerView view) {
        super(view);
        model = new AddressManagerModelImpl();
    }

    public void sendNetAddressManager(String key, String addressId, String name,
                                      String phone, String areaId, String cityId,
                                      String region, String address, String company,
                                      String isDefault) {
        if (mView != null) mView.showLoading();
        if (model != null)
            model.sendNetAddressManager(key, addressId, name, phone, areaId, cityId, region, address, company, isDefault, this);
    }

    @Override
    public void getAddressManager(String result) {
        checkJson(result);
        if (mView != null && !isError) {
            mView.hideLoading();
            try {
                mView.getAddressManagerSuccess(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
