package com.example.administrator.chadaodiancompany.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;

public class HomeSellAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mData;

    public HomeSellAdapter(Context context, String[] data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public String getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_sell, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvAdapterSellDetail.setText(getItem(position));
        return convertView;
    }

    public void notifyData(String[] sellStr) {
        this.mData = sellStr;
        notifyDataSetChanged();
    }

    private static class ViewHolder {

        public TextView tvAdapterSellDetail;

        public ViewHolder(View view) {
            tvAdapterSellDetail = view.findViewById(R.id.tvAdapterSellDetail);
        }
    }
}
