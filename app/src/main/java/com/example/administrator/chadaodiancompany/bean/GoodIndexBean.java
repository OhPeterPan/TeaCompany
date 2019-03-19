package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class GoodIndexBean {

    public int code;
    public boolean hasmore;
    public int page_total;
    public GoodDataBean datas;

    public static class GoodDataBean {
        public List<GoodsListBean> goods_list;

        public static class GoodsListBean {

            public String owe_deliver_num;
            public String deliver_num;
            public String gc_id;
            public String goods_type;
            public String promotions_id;
            public String store_id;
            public String shop_id;
            public String c_id;
            public String buyer_id;
            public String goods_pay_price;
            public String goods_commonid;
            public String rec_id;
            public String order_id;
            public String goods_id;
            public String goods_price;
            public String goods_name;
            public String goods_num;
            public String goods_marketprice;
            public String goods_addtime;
            public String goods_image;
            public String goods_state;
            public String goods_lock;
            public String goods_serial;
            public float goods_storage_sum;
            public boolean choose;
        }
    }
}
