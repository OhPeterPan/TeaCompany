package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class StatisticSellBean {
   public int code;
   public boolean hasmore;
   public int page_total;
   public StatisticSellData datas;

    public static class StatisticSellData {
        public List<StatisticSellList> order_list;

        public static class StatisticSellList {
         public String c_id;
         public String c_name;
         public String order_amount;
         public String num;
        }
    }
}
