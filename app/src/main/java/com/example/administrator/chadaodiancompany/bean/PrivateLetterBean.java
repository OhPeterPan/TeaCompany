package com.example.administrator.chadaodiancompany.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class PrivateLetterBean {

    public int code;
    public boolean hasmore;
    public int page_total;
    public DatasBean datas;

    public static class DatasBean {
        public List<PrivateLetterListBean> list;

        public static class PrivateLetterListBean implements MultiItemEntity {

            public String m_id;
            public String f_id;
            public String f_name;
            public String t_id;
            public String t_name;
            public String t_msg;
            public String add_time;
            public String f_avatar;
            public String t_avatar;
            public int type;
            public String time;


            @Override
            public int getItemType() {
                return type;
            }
        }
    }
}
