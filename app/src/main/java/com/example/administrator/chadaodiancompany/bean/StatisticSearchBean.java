package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class StatisticSearchBean {

    public int code;
    public StatisticSearchDatas datas;

    public static class StatisticSearchDatas {
        public List<GoodsClass> goods_class;

        public static class GoodsClass {
            public String stc_id;
            public String stc_name;
            public String stc_parent_id;
            public String stc_state;
            public String store_id;
            public String stc_sort;
        }
    }
}
