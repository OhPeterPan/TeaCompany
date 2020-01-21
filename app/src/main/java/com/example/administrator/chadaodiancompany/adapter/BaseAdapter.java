package com.example.administrator.chadaodiancompany.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, com.chad.library.adapter.base.BaseViewHolder> {

    public BaseAdapter(int layoutResId, RecyclerView recyclerView,
                       RecyclerView.LayoutManager layoutManager, RecyclerView.ItemDecoration decoration) {
        super(layoutResId, null);
        recyclerView.setLayoutManager(layoutManager);
        if (decoration != null)
            recyclerView.addItemDecoration(decoration);
        bindToRecyclerView(recyclerView);

    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        bind(helper, item);
    }

    protected abstract void bind(BaseViewHolder helper, T item);
}
