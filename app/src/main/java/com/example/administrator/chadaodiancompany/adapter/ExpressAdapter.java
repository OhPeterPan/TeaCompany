package com.example.administrator.chadaodiancompany.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.ExpressDetailListBean;


/**
 * Created by Administrator on 2018/5/22 0022.
 */

public class ExpressAdapter extends BaseQuickAdapter<ExpressDetailListBean, BaseViewHolder> {
    public ExpressAdapter() {
        super(R.layout.adapter_express_list, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, ExpressDetailListBean item) {
        if (holder.getAdapterPosition() == 0) {
            holder.setImageResource(R.id.imageShapeGrayDrop, R.mipmap.red_drop_pic);
        } else {
            holder.setImageResource(R.id.imageShapeGrayDrop, R.drawable.shape_gray_drop);
        }
        holder.setText(R.id.tvAdapterExpressInfo, item.context);
        holder.setText(R.id.tvAdapterExpressTime, item.time);
    }
}
