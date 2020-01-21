package com.example.administrator.chadaodiancompany.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.SingleGoodInfoBean.SingleDatas.SingleOrderGoods;

public class SingleGoodAnalyseAdapter extends BaseQuickAdapter<SingleOrderGoods, BaseViewHolder> {
    public SingleGoodAnalyseAdapter() {
        super(R.layout.adapter_single_good, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SingleOrderGoods item) {
        helper.setText(R.id.tvAdapterSingleGoodTime, item.day + "\n" + item.time);
        helper.setText(R.id.tvAdapterSingleGoodMember, item.member_name);
        helper.setText(R.id.tvAdapterSingleGoodNum, item.goods_num);
        helper.setText(R.id.tvAdapterSingleGoodMoney, "¥ " + item.goods_pay_price);
        if (helper.getAdapterPosition() % 2 != 0) {
            helper.itemView.setBackgroundColor(Color.WHITE);
        } else {
            helper.itemView.setBackgroundColor(Color.parseColor("#f9f9f9"));
        }
    }
}
