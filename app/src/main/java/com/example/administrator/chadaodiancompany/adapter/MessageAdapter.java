package com.example.administrator.chadaodiancompany.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.MsgBean.MsgDataBean.MsgListBean;

public class MessageAdapter extends BaseQuickAdapter<MsgListBean, BaseViewHolder> {
    public MessageAdapter() {
        super(R.layout.adapter_message, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgListBean item) {
        helper.setText(R.id.tvAdapterMessageName, StringUtils.equals("new_order", item.smt_code) ? "订单消息" : "商品消息");
        helper.setText(R.id.tvAdapterMessageInfo, item.sm_content);
        helper.setText(R.id.tvAdapterMessageTime, item.sm_addtime);
        ImageView ivAdapterMessagePic = helper.getView(R.id.ivAdapterMessagePic);
        if (StringUtils.equals("1", item.is_read)) {
            helper.setVisible(R.id.ivDrop, false);
        } else {
            helper.setVisible(R.id.ivDrop, true);
        }
        if (StringUtils.equals("new_order", item.smt_code)) {
            Glide.with(mContext).asDrawable().load(R.drawable.ic_message_order).into(ivAdapterMessagePic);
        } else {
            Glide.with(mContext).asDrawable().load(R.drawable.ic_message_good).into(ivAdapterMessagePic);
        }
    }
}
