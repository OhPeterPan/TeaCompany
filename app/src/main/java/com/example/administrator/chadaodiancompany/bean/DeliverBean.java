package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class DeliverBean {
  public String shipping_time;
  public String deliver_explain;
  public String shipping_code;
  public String e_name;
  public String shipping_express_id;
  public String delivery_state;
  public String delivery_time;
  public String delay_time;
  public List<DeliverGoodsList> goods_list;

    public static class DeliverGoodsList {

        public String rec_id;
        public String goods_id;
        public String goods_num;
    }
}
