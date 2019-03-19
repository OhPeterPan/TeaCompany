package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class HomeDetailBean {

    public int code;
    public HomeDataBean datas;

    public static class HomeDataBean {

        public String progressing;
        public String no_payment;
        public String no_delivery;
        public String goods_online;
        public List<HomeStatisticsBean> statistics;

        public static class HomeStatisticsBean {

            public String item;
            public String order;
            public String order_money;
            public String item_y;
            public String order_y;
            public String order_money_y;
            public String item_m;

        }
    }
}
