package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class MemberIndexBean {

    public int code;
    public boolean hasmore;
    public int page_total;
    public MemberInfoBean datas;

    public static class MemberInfoBean {

        public String member_count;
        public String year_amount;
        public String month_amount;
        public List<MemberListBean> member_list;
    }
}
