package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class GoodClassifyBean {
    public int code;
    public GoodClassifyData datas;

    public static class GoodClassifyData {
        public List<GoodsClassifyListBean> store_goods_class;

        public static class GoodsClassifyListBean {
            public String stc_id;
            public String stc_name;
            public String stc_parent_id;
            public String stc_state;
            public String store_id;
            public String stc_sort;
            public List<GoodsChildClassify> child;

            public static class GoodsChildClassify {
                public String stc_id;
                public String stc_name;
                public String stc_parent_id;
                public String stc_state;
                public String store_id;
                public String stc_sort;
            }
        }
    }
}
