package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class SearchDetailBean {

    public int code;
    public boolean hasmore;
    public int page_total;
    public SearchDetailDatas datas;

    public static class SearchDetailDatas {

        public String count;
        public String count_goods_num;
        public String count_goods_price;
        public List<SearchDetailListBean> list;

        public static class SearchDetailListBean {

            public String goods_id;
            public String goods_name;
            public String goods_image;
            public String goods_num;
            public String goods_price;

        }
    }
}
