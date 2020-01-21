package com.example.administrator.chadaodiancompany.bean;

import com.example.administrator.chadaodiancompany.bean.PrivateLetterBean.DatasBean.PrivateLetterListBean;

public class InteractMessageBean {

    public int code;
    public DatasBean datas;

    public static class DatasBean {

        public PrivateLetterListBean msg;
    }
}
