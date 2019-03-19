package com.example.administrator.chadaodiancompany.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class OrderAdapterType implements MultiItemEntity {
    public int type;
    public String time;
    public String payMount;
    public String buyerName;
    //public OrderDetailBean orderbean;
    public OrderGoodBean extendOrderGoods;
    public String count;
    public String pay_sn;
    public String order_id;
    public String stateDesc;
    public String shippingFee;
    public String evaluationState;
    public String goodsId;
    public List<OrderGoodBean> goodsList;
    public String storeId;
    public int num;
    public String order_sn;
    public String storeShipping;

    /**
     * @return 0 列表头部  1商品列表  2 底部数据
     */
    @Override
    public int getItemType() {
        return type;
    }
}
