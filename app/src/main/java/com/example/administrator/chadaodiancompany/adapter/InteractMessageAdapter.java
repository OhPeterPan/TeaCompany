package com.example.administrator.chadaodiancompany.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.InteractBean.InteractDatasBean.InteractListBean;

public class InteractMessageAdapter extends BaseQuickAdapter<InteractListBean, BaseViewHolder> {
    public InteractMessageAdapter() {
        super(R.layout.adapter_message, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, InteractListBean item) {
        helper.setText(R.id.tvAdapterMessageName, item.u_name);
        helper.setText(R.id.tvAdapterMessageInfo, item.t_msg);
        helper.setText(R.id.tvAdapterMessageTime, item.time);
        helper.setVisible(R.id.ivDrop, false);
        ImageView ivAdapterMessagePic = helper.getView(R.id.ivAdapterMessagePic);
        Glide.with(mContext).asDrawable().load(item.avatar).into(ivAdapterMessagePic);
    }
}
