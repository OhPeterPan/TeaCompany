package com.example.administrator.chadaodiancompany.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;

import java.util.List;

public class MemberIndexAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MemberIndexAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_member_index, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tvAdapterOrderNumber, String.valueOf(position + 1));
        helper.setText(R.id.tvAdapterCompanyName, "我是一个公司的名称");
        if (position % 2 == 0) {
            helper.setBackgroundRes(R.id.layout_front, R.color.colorBackground);
        } else {
            helper.setBackgroundRes(R.id.layout_front, R.color.white);
        }
        helper.addOnClickListener(R.id.tvAdapterMemberEdit);
        helper.addOnClickListener(R.id.tvAdapterMemberDel);
    }
}
