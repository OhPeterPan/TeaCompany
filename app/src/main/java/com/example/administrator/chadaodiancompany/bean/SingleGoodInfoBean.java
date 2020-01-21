package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class SingleGoodInfoBean {

    public int code;
    public SingleDatas datas;

    public static class SingleDatas {

        public SingleGoodsInfo goods_info;
        public List<SingleOrderGoods> order_goods;

        public static class SingleGoodsInfo {

            public String goods_id;
            public String goods_name;
            public String goods_image;
            public String goods_num;
            public String pay_price;
        }

        public static class SingleOrderGoods {

            public String order_id;
            public String c_id;
            public String payment_time;
            public String goods_num;
            public String goods_pay_price;
            public String member_name;
            public String day;
            public String time;

        }
    }
}
