package com.example.administrator.chadaodiancompany.adapter;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.OrderGoodBean;
import com.example.administrator.chadaodiancompany.image.ImageLoader;
import com.example.administrator.chadaodiancompany.util.NumberUtil;

import java.util.List;

public class SendGoodIndexAdapter extends BaseQuickAdapter<OrderGoodBean, BaseViewHolder> {
    private String index = "一次";

    public SendGoodIndexAdapter() {
        super(R.layout.adapter_send_good, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderGoodBean orderGoods) {
        ImageLoader.with(mContext)
                .placeHolder(R.mipmap.image_loading)
                .error(R.mipmap.image_load_error)
                .url(orderGoods.goods_image)
                .into(holder.getView(R.id.ivAdapterOrderSendGoodsPic));
        holder.setText(R.id.tvAdapterOrderSendGoodsPrice, NumberUtil.getCurrency(Float.valueOf(StringUtils.isEmpty(orderGoods.goods_price) ? "0.00" : orderGoods.goods_price)));
        holder.setText(R.id.tvAdapterOrderSendGoodsName, orderGoods.goods_name);
        holder.setText(R.id.ivAdapterOrderSendGoodsNumber, "X " + orderGoods.goods_num);
        holder.setText(R.id.ivAdapterSendOrderSendGoodsState, "当前欠： " + orderGoods.owe_deliver_num);
        holder.setText(R.id.tvAdapterSendOrderSendNum, index + "次发货 X ");
        holder.setText(R.id.tvAdapterSendOrderSendNumber, orderGoods.send_num);
        holder.addOnClickListener(R.id.tvAdapterSendOrderSendNumber);
    }

    public void setNewDataIndex(String index, List<OrderGoodBean> goodsList) {
        this.index = index;
        setNewData(goodsList);
    }
}
