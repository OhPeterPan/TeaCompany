package com.example.administrator.chadaodiancompany.bean;

public class HomeInfoBean {

    public String today_amount;
    public String today_count;
    public String month_amount;
    public String month_count;
    public String member_count;
    public String goods_online;
    public String message;
    public String progressing;
    public String no_payment;
    public String no_delivery;

    @Override
    public String toString() {
        return today_amount + ":::" + today_count;
    }
}
