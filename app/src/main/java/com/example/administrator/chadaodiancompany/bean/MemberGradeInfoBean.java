package com.example.administrator.chadaodiancompany.bean;

public class MemberGradeInfoBean {

    public int code;
    public MemberGradeInfoData datas;

    public static class MemberGradeInfoData {

        public GradeInfoBean grade_info;

        public static class GradeInfoBean {
            public String id;
            public String grade_name;
            public String store_id;
            public String store_name;
            public String money;
            public String sort;
        }
    }
}
