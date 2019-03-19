package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class AddressDataBean {

    public int code;
    public AddressData datas;

    public static class AddressData {
        public List<AddressListBean> address_list;
    }
}
