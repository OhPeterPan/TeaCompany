package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class MemberAuditBean {

    public int code;
    public MemberAuditDatas datas;

    public static class MemberAuditDatas {
        public List<AuditListBean> audit_list;

        public static class AuditListBean {

            public String c_id;
            public String member_id;
            public String entry_shop_time;
            public String entry_shop_reason;
            public String audit_id;
            public String c_name;
            public String member_name;

        }
    }
}
