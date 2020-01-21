package com.example.administrator.chadaodiancompany.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.GoodAnalyseBean.GoodAnalyseDatas.ListBean;

public class GoodAnalyseAdapter extends BaseQuickAdapter<ListBean, BaseViewHolder> {
    private RecyclerView recyclerView;

    public GoodAnalyseAdapter(RecyclerView recyclerView) {
        super(R.layout.adapter_good_analyse, null);
        this.recyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(mContext).resumeRequests();
                } else {
                    Glide.with(mContext).pauseRequests();
                }
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        helper.setText(R.id.tvAdapterGoodAnalyseNO, String.valueOf(helper.getAdapterPosition() + 1));
        helper.setText(R.id.tvAdapterGoodAnalyseName, item.goods_name);
        helper.setText(R.id.tvAdapterGoodAnalyseNumber, item.sum);
        ImageView ivAdapterGoodAnalysePic = helper.getView(R.id.ivAdapterGoodAnalysePic);
        Glide.with(mContext).asDrawable().load(item.goods_image).into(ivAdapterGoodAnalysePic);
        if (helper.getAdapterPosition() % 2 != 0) {
            helper.itemView.setBackgroundColor(Color.WHITE);
        } else {
            helper.itemView.setBackgroundColor(Color.parseColor("#f9f9f9"));
        }
    }
}
