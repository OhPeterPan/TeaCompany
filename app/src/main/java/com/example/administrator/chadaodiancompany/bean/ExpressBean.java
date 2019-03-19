package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class ExpressBean {

    public int code;
    public ExpressDataBean datas;

    public static class ExpressDataBean {
        public List<AplListBean> apl_list;
    }
}
