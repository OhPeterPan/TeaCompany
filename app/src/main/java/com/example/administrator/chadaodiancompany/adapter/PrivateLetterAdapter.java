package com.example.administrator.chadaodiancompany.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.PrivateLetterBean.DatasBean.PrivateLetterListBean;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateLetterAdapter extends BaseMultiItemQuickAdapter<PrivateLetterListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public PrivateLetterAdapter() {
        super(null);
        addItemType(0, R.layout.adapter_layout_reciver);
        addItemType(1, R.layout.adapter_layout_send);
    }

    @Override
    protected void convert(BaseViewHolder holder, PrivateLetterListBean item) {
        switch (item.type) {
            case 0:
                Glide.with(mContext).load(item.f_avatar).into((CircleImageView) holder.getView(R.id.ivAdapterReceiverPic));
                holder.setText(R.id.tvAdapterReceiverContent, item.t_msg);
                holder.setText(R.id.tvAdapterReceiverTime, item.time);
                holder.setText(R.id.tvAdapterReceiverName, item.f_name);
                break;
            case 1:
                Glide.with(mContext).load(item.f_avatar).into((CircleImageView) holder.getView(R.id.ivAdapterSendPic));
                holder.setText(R.id.tvAdapterSendContent, item.t_msg);
                holder.setText(R.id.tvAdapterSendTime, item.time);
                break;
        }
    }
}
