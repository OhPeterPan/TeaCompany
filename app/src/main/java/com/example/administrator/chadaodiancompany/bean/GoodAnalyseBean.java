package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class GoodAnalyseBean {

    public int code;
    public boolean hasmore;
    public int page_total;
    public GoodAnalyseDatas datas;

    public static class GoodAnalyseDatas {

        public String sum;
        public String pay_price;
        public String count;
        public List<ListBean> list;

        public static class ListBean {

            public String goods_id;
            public String goods_name;
            public String goods_image;
            public String sum;
            public String pay_price;
        }
    }
}
