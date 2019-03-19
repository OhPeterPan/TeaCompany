package com.example.administrator.chadaodiancompany.adapter;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.OrderAdapterType;
import com.example.administrator.chadaodiancompany.bean.OrderGoodBean;
import com.example.administrator.chadaodiancompany.image.ImageLoader;
import com.example.administrator.chadaodiancompany.util.NumberUtil;

import java.util.List;

public class OrderIndexAdapter extends BaseMultiItemQuickAdapter<OrderAdapterType, BaseViewHolder> {

    public OrderIndexAdapter(List<OrderAdapterType> data) {
        super(data);
        addItemType(0, R.layout.adapter_title);
        addItemType(1, R.layout.adapter_order_good);
        addItemType(2, R.layout.adapter_order_index_bottom);
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderAdapterType item) {
        switch (item.type) {
            case 0:
                initOrderListTitle(holder, item);
                break;
            case 1:
                initOrderListGood(holder, item);
                break;
            case 2:
                initOrderListBottom(holder, item);
                break;
        }
    }

    private void initOrderListBottom(BaseViewHolder holder, OrderAdapterType item) {

        if (StringUtils.equals("1", item.storeShipping)) {
            holder.setText(R.id.tvAdapterOrderExpenses, "运费：运费到付");
            holder.setGone(R.id.tvAdapterChangeOrderExpenses, false);
        } else {
            if (StringUtils.isEmpty(item.shippingFee)) {
                holder.setText(R.id.tvAdapterOrderExpenses, "运费：免运费");
                holder.setGone(R.id.tvAdapterChangeOrderExpenses, false);
            } else if (Double.valueOf(item.shippingFee) == 0) {
                holder.setText(R.id.tvAdapterOrderExpenses, "运费：免运费");
                holder.setGone(R.id.tvAdapterChangeOrderExpenses, false);
            } else {
                holder.setText(R.id.tvAdapterOrderExpenses, "运费：¥" + item.shippingFee);
                holder.setGone(R.id.tvAdapterChangeOrderExpenses, true);
            }
        }

        // holder.setText(R.id.tvAdapterOrderExpenses, "运费：¥" + item.shippingFee);
        holder.setText(R.id.tvAdapterOrderOrderAmount, "订单金额：¥" + item.payMount);
        holder.addOnClickListener(R.id.tvAdapterCancelOrder);
        holder.addOnClickListener(R.id.tvAdapterChangeOrderExpenses);
        holder.addOnClickListener(R.id.tvAdapterChangeOrderAmount);
        holder.addOnClickListener(R.id.tvAdapterChangeOrderSendGood);
        holder.addOnClickListener(R.id.tvAdapterConfirmMoney);
        switch (item.stateDesc) {
            case "待付款":
                holder.setGone(R.id.llAdapterWaitPay, true);
                holder.setGone(R.id.llAdapterWaitSend, false);
                break;
            case "待发货":
                holder.setGone(R.id.llAdapterWaitPay, false);
                holder.setGone(R.id.llAdapterWaitSend, true);
                break;
            default:
                holder.setGone(R.id.llAdapterWaitPay, false);
                holder.setGone(R.id.llAdapterWaitSend, false);
                break;
        }
    }

    private void initOrderListGood(BaseViewHolder holder, OrderAdapterType item) {
        OrderGoodBean orderGoods = item.extendOrderGoods;
        ImageLoader.with(mContext)
                .placeHolder(R.mipmap.image_loading)
                .error(R.mipmap.image_load_error)
                .url(orderGoods.goods_image)
                .into(holder.getView(R.id.ivAdapterOrderGoodsPic));
        holder.setText(R.id.ivAdapterOrderGoodsPrice, NumberUtil.getCurrency(Float.valueOf(StringUtils.isEmpty(orderGoods.goods_price) ? "0.00" : orderGoods.goods_price)));
        holder.setText(R.id.ivAdapterOrderGoodsName, orderGoods.goods_name);
        holder.setText(R.id.ivAdapterOrderGoodsNumber, "X " + orderGoods.goods_num);
        switch (item.stateDesc) {
            case "待发货":
                holder.setGone(R.id.ivAdapterSendOrderGoodsState, true);
                holder.setText(R.id.ivAdapterSendOrderGoodsState, "已发货 X " + orderGoods.deliver_num + ",当前欠 X " + orderGoods.owe_deliver_num);
                break;
            case "待收货":
                holder.setGone(R.id.ivAdapterSendOrderGoodsState, true);
                holder.setText(R.id.ivAdapterSendOrderGoodsState, "已发货 X " + orderGoods.deliver_num);
                break;
            default:
                holder.setGone(R.id.ivAdapterSendOrderGoodsState, false);
                break;
        }
    }

    private void initOrderListTitle(BaseViewHolder holder, OrderAdapterType item) {
        holder.setText(R.id.tvAdapterOrderPurchaserName, "买家：" + item.buyerName);
        holder.setText(R.id.tvAdapterOrderState, item.stateDesc);
    }
}
