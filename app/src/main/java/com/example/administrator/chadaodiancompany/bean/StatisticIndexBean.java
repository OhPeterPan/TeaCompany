package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class StatisticIndexBean {

    public int code;
    public StatisticDataBean datas;

    public static class StatisticDataBean {
        public String total;
        public String daymax;
        public String avg;
        public String stime;
        public String etime;
        public List<StatisticList> list;

        public static class StatisticList {

            public String total;
            public String time;

        }
    }
}
