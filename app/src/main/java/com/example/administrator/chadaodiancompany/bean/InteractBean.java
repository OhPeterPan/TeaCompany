package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class InteractBean {

    public int code;
    public boolean hasmore;
    public int page_total;
    public InteractDatasBean datas;

    public static class InteractDatasBean {
        public List<InteractListBean> list;

        public static class InteractListBean {
           public String u_id;
           public String u_name;
           public String avatar;
           public int recent;
           public String t_msg;
           public String time;
        }
    }
}
