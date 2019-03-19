package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class OrderIndexBean {
    public int code;
    public boolean hasmore;
    public int page_total;
    public OrderIndexDataBean datas;

    public static class OrderIndexDataBean {
        public List<OrderDetailListBean> order_list;

        public static class OrderDetailListBean {
            public String order_id;
            public String buyer_id;
            public String buyer_name;
            public String state_desc;
            public String shipping_fee;
            public String store_shipping;
            public String order_amount;
            public String add_time;
            public String order_sn;
            public int deliver_num;
            public List<OrderGoodBean> order_goods;
        }
    }
}
