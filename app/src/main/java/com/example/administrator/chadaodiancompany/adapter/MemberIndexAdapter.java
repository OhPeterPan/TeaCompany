package com.example.administrator.chadaodiancompany.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.MemberListBean;

public class MemberIndexAdapter extends BaseQuickAdapter<MemberListBean, BaseViewHolder> {
    public MemberIndexAdapter() {
        super(R.layout.adapter_member_index, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberListBean item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tvAdapterOrderNumber, String.valueOf(position + 1));
        helper.setText(R.id.tvAdapterCompanyName, item.c_name);
        helper.setText(R.id.tvAdapterMemberName, item.member_name);
        helper.setText(R.id.tvAdapterARTNO, item.grade_name);
        if (position % 2 == 0) {
            helper.setBackgroundRes(R.id.layout_front, R.color.colorBackground);
        } else {
            helper.setBackgroundRes(R.id.layout_front, R.color.white);
        }
        helper.addOnClickListener(R.id.tvAdapterMemberEdit);
        helper.addOnClickListener(R.id.tvAdapterMemberDel);
    }
}
