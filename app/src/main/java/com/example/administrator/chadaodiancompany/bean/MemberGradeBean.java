package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class MemberGradeBean {
    public int code;
    public DatasBean datas;

    public static class DatasBean {
        public List<GradeListBean> grade_list;

        public static class GradeListBean {
            public String id;
            public String grade_name;
            public String store_id;
            public String store_name;
            public String money;
            public String sort;
            public String grade_id;
        }
    }
}
