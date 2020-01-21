package com.example.administrator.chadaodiancompany.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.MemberAuditBean.MemberAuditDatas.AuditListBean;

import java.util.List;

public class MemberAuditAdapter extends BaseAdapter<AuditListBean> {

    public MemberAuditAdapter(int layoutResId, RecyclerView recyclerView,
                              RecyclerView.LayoutManager layoutManager,
                              RecyclerView.ItemDecoration decoration) {
        super(layoutResId, recyclerView, layoutManager, decoration);
    }

    @Override
    protected void bind(BaseViewHolder helper, AuditListBean item) {
        ImageView adapterIvAuditPic = helper.getView(R.id.adapterIvAuditPic);
        //Glide.with(mContext).asDrawable().load(item.)
        helper.setText(R.id.adapterTvAuditTime, item.entry_shop_time);
        helper.setText(R.id.adapterTvAuditName, item.c_name);
        helper.setText(R.id.adapterTvAuditInfo, item.entry_shop_reason);
    }

    public void notifyData(List<AuditListBean> gradeList) {
        setNewData(gradeList);
        setEmptyView(R.layout.layout_empty, getRecyclerView());
    }
}
