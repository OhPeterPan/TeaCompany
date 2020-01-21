package com.example.administrator.chadaodiancompany.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.MemberGradeBean.DatasBean.GradeListBean;

import java.util.List;

public class MemberGradeAdapter extends BaseAdapter<GradeListBean> {
    public MemberGradeAdapter(RecyclerView recyclerView,
                              RecyclerView.LayoutManager layoutManager,
                              RecyclerView.ItemDecoration decoration) {
        super(R.layout.adapter_member_grade, recyclerView, layoutManager, decoration);
    }

    @Override
    protected void bind(BaseViewHolder helper, GradeListBean item) {
        helper.setText(R.id.tvAdapterMemberNumber, String.valueOf(helper.getAdapterPosition() + 1));
        helper.setText(R.id.tvAdapterMemberGradeName, item.grade_name);
        helper.setText(R.id.tvAdapterMemberGradePrice, item.money);
        helper.addOnClickListener(R.id.tvSwipeEdit);
        helper.addOnClickListener(R.id.tvSwipeDel);
        if (helper.getAdapterPosition() % 2 != 0) {
            helper.itemView.setBackgroundColor(Color.WHITE);
        } else {
            helper.itemView.setBackgroundColor(Color.parseColor("#f9f9f9"));
        }
    }

    public void notifyData(List<GradeListBean> gradeList) {
        setNewData(gradeList);
        setEmptyView(R.layout.layout_empty, getRecyclerView());
    }
}
