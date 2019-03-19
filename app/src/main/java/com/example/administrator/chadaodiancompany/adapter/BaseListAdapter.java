package com.example.administrator.chadaodiancompany.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by chenchangxing on 15/12/18.
 */
public abstract class BaseListAdapter<T> extends android.widget.BaseAdapter {

    protected Context mContext;
    protected LayoutInflater inflater;
    protected List<T> list;
    private final WeakReference<Context> weakReference;

    public BaseListAdapter(Context context, List<T> list) {
        weakReference = new WeakReference<>(context);
        mContext = weakReference.get();
        this.inflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int getCount() {

        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(list.get(position), convertView, parent, position);
    }

    public abstract View initView(T t, View convertView, ViewGroup parent, int position);

}
