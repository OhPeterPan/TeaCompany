package com.example.administrator.chadaodiancompany.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.StatisticSellBean.StatisticSellData.StatisticSellList;
import com.example.administrator.chadaodiancompany.view.SwipeLayout;

public class StatisticsSellAdapter extends BaseQuickAdapter<StatisticSellList, BaseViewHolder> {
    public StatisticsSellAdapter() {
        super(R.layout.adapter_member_index, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, StatisticSellList item) {
        int position = helper.getAdapterPosition();
        SwipeLayout adapterSwipeLayout = helper.getView(R.id.adapterSwipeLayout);
        adapterSwipeLayout.isState(false);
        helper.setText(R.id.tvAdapterOrderNumber, String.valueOf(position + 1));
        helper.setText(R.id.tvAdapterCompanyName, item.c_name);
        helper.setText(R.id.tvAdapterMemberName, item.num);
        helper.setText(R.id.tvAdapterARTNO, "¥ " + item.order_amount);
        if (position % 2 == 0) {
            helper.setBackgroundRes(R.id.layout_front, R.color.colorBackground);
        } else {
            helper.setBackgroundRes(R.id.layout_front, R.color.white);
        }
        helper.addOnClickListener(R.id.tvAdapterMemberEdit);
        helper.addOnClickListener(R.id.tvAdapterMemberDel);
    }
}
