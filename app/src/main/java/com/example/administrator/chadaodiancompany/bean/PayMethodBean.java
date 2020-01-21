package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class PayMethodBean {

    public int code;
    public PayMethodData datas;

    public static class PayMethodData {
        public List<PayMethodListBean> list;

        public static class PayMethodListBean {

            public String payment_code;
            public String payment_name;

        }
    }
}
