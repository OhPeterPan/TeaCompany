package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class MsgBean {

    public int code;
    public boolean hasmore;
    public int page_total;
    public MsgDataBean datas;

    public static class MsgDataBean {

        public int no_count;
        public List<MsgListBean> msg_list;

        public static class MsgListBean {

            public String sm_id;
            public String smt_code;
            public String store_id;
            public String sm_content;
            public String sm_addtime;
            public boolean choose;
            public String is_read;
            public List<String> sm_readids;

        }
    }
}
