package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class OrderDetailBean {

    public int code;
    public OrderDetailData datas;

    public static class OrderDetailData {

        public OrderInfoBean order_info;

        public static class OrderInfoBean {

            public String order_id;
            public String order_sn;
            public String price_remark;
            public String pay_sn;
            public String order_type;
            public String store_id;
            public String store_name;
            public String shoper_id;
            public String buyer_id;
            public String buyer_name;
            public String buyer_email;
            public String c_id;
            public String shop_id;
            public String add_time;
            public String payment_code;
            public String payment_time;
            public String shipping_time;
            public String finnshed_time;
            public String goods_amount;
            public String order_amount;
            public String rcb_amount;
            public String pd_amount;
            public String shipping_fee;
            public String store_shipping;
            public String evaluation_state;
            public int order_state;
            public String refund_state;
            public String lock_state;
            public String delete_state;
            public String refund_amount;
            public String delay_time;
            public String order_from;
            public String shipping_code;
            public String state_desc;
            public String payment_name;
            public String order_message;
            public int num;
            public InvoiceInfoBean invoice_info;
            public int goods_count;
            public List<DeliverBean> deliver;
            public List<OrderGoodBean> goods_list;
            public List<OrderGoodBean> zengpin_list;
            public AddressBean reciver_info;
            public String promotion_info;
        }
    }
}
