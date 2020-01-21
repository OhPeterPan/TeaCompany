package com.example.administrator.chadaodiancompany.adapter;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.SearchDetailBean.SearchDetailDatas.SearchDetailListBean;

public class SearchDetailAdapter extends BaseQuickAdapter<SearchDetailListBean, BaseViewHolder> {
    public SearchDetailAdapter() {
        super(R.layout.adapter_search_info, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDetailListBean item) {
        helper.setText(R.id.tvAdapterSearchGoodNO, String.valueOf(helper.getAdapterPosition()));
        helper.setText(R.id.tvAdapterSearchGoodVolume, item.goods_num);
        helper.setText(R.id.tvAdapterSearchGoodPrice, item.goods_price);
        ImageView ivAdapterGoodAnalysePic = helper.getView(R.id.ivAdapterSearchGoodPic);
        Glide.with(mContext).asDrawable().load(item.goods_image).into(ivAdapterGoodAnalysePic);
        helper.addOnClickListener(R.id.ivAdapterSearchGoodPic);
        if (helper.getAdapterPosition() % 2 != 0) {
            helper.itemView.setBackgroundColor(Color.WHITE);
        } else {
            helper.itemView.setBackgroundColor(Color.parseColor("#f9f9f9"));
        }
    }
}
