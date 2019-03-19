package com.example.administrator.chadaodiancompany.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressListBean implements Parcelable {
    public String address_id;
    public String store_id;
    public String seller_name;
    public String area_id;
    public String city_id;
    public String area_info;
    public String address;
    public String telphone;
    public String company;
    public String is_default;

    public AddressListBean() {
    }

    protected AddressListBean(Parcel in) {
        address_id = in.readString();
        store_id = in.readString();
        seller_name = in.readString();
        area_id = in.readString();
        city_id = in.readString();
        area_info = in.readString();
        address = in.readString();
        telphone = in.readString();
        company = in.readString();
        is_default = in.readString();
    }

    public static final Creator<AddressListBean> CREATOR = new Creator<AddressListBean>() {
        @Override
        public AddressListBean createFromParcel(Parcel in) {
            return new AddressListBean(in);
        }

        @Override
        public AddressListBean[] newArray(int size) {
            return new AddressListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address_id);
        dest.writeString(store_id);
        dest.writeString(seller_name);
        dest.writeString(area_id);
        dest.writeString(city_id);
        dest.writeString(area_info);
        dest.writeString(address);
        dest.writeString(telphone);
        dest.writeString(company);
        dest.writeString(is_default);
    }
}
