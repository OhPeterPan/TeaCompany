package com.example.administrator.chadaodiancompany.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderGoodBean implements Parcelable {
    /**
     * goods_id : 101851
     * goods_name : 八马茶业 茶食品 铁观音蕃茄 138G
     * goods_price : 17.00
     * goods_num : 2
     * deliver_num : 2
     * owe_deliver_num : 0
     * goods_image : http://upload.chadaodian.com/shop/store/goods/3/3_05277718552378336.jpg
     */
    public String rec_id;
    public String order_id;
    public String goods_id;
    public String goods_name;
    public String goods_price;
    public String goods_num;
    public String deliver_num;
    public String owe_deliver_num;
    public String goods_image;
    public String send_num = "0";

    public OrderGoodBean() {
    }


    protected OrderGoodBean(Parcel in) {
        rec_id = in.readString();
        order_id = in.readString();
        goods_id = in.readString();
        goods_name = in.readString();
        goods_price = in.readString();
        goods_num = in.readString();
        deliver_num = in.readString();
        owe_deliver_num = in.readString();
        goods_image = in.readString();
        send_num = in.readString();
    }

    public static final Creator<OrderGoodBean> CREATOR = new Creator<OrderGoodBean>() {
        @Override
        public OrderGoodBean createFromParcel(Parcel in) {
            return new OrderGoodBean(in);
        }

        @Override
        public OrderGoodBean[] newArray(int size) {
            return new OrderGoodBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rec_id);
        dest.writeString(order_id);
        dest.writeString(goods_id);
        dest.writeString(goods_name);
        dest.writeString(goods_price);
        dest.writeString(goods_num);
        dest.writeString(deliver_num);
        dest.writeString(owe_deliver_num);
        dest.writeString(goods_image);
        dest.writeString(send_num);
    }
}
