package com.example.administrator.chadaodiancompany.adapter;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.OrderGoodBean;
import com.example.administrator.chadaodiancompany.image.ImageLoader;
import com.example.administrator.chadaodiancompany.util.NumberUtil;

import java.util.List;

public class OrderDetailAdapter extends BaseQuickAdapter<OrderGoodBean, BaseViewHolder> {
    private String index = "一次";

    public OrderDetailAdapter() {
        super(R.layout.adapter_order_good, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderGoodBean orderGoods) {
        ImageLoader.with(mContext)
                .placeHolder(R.mipmap.image_loading)
                .error(R.mipmap.image_load_error)
                .url(orderGoods.goods_image)
                .into(holder.getView(R.id.ivAdapterOrderGoodsPic));
        holder.setText(R.id.ivAdapterOrderGoodsPrice, NumberUtil.getCurrency(Float.valueOf(StringUtils.isEmpty(orderGoods.goods_price) ? "0.00" : orderGoods.goods_price)));
        holder.setText(R.id.ivAdapterOrderGoodsName, orderGoods.goods_name);
        holder.setText(R.id.ivAdapterOrderGoodsNumber, "X " + orderGoods.goods_num);
        if (StringUtils.isEmpty(orderGoods.deliver_num) || orderGoods.num == 1) {
            holder.setGone(R.id.ivAdapterSendOrderGoodsState, false);
        } else {
            holder.setGone(R.id.ivAdapterSendOrderGoodsState, true);
            holder.setText(R.id.ivAdapterSendOrderGoodsState, "第" + index + "发货 X " + orderGoods.deliver_num + "，剩余待发 X " + orderGoods.owe_deliver_num);//+ "，剩余待发 X " + item.owe_deliver_num
        }
    }

    public void setNewDataIndex(String index, List<OrderGoodBean> goodsList) {
        this.index = index;
        setNewData(goodsList);
    }
}
