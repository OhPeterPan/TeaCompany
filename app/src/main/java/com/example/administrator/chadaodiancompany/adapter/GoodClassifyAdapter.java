package com.example.administrator.chadaodiancompany.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.GoodClassifyBean.GoodClassifyData.GoodsClassifyListBean;
import com.example.administrator.chadaodiancompany.util.UIUtil;

import java.util.List;

public class GoodClassifyAdapter extends BaseListAdapter<GoodsClassifyListBean> {
    private int mPosition;

    public GoodClassifyAdapter(Context context, List<GoodsClassifyListBean> list) {
        super(context, list);
    }

    @Override
    public View initView(GoodsClassifyListBean goodsClassifyListBean, View convertView, ViewGroup parent, int position) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_class, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvAdapterClassify.setText(list.get(position).stc_name);
        if (position == mPosition) {
            viewHolder.tvAdapterClassify.setBackgroundColor(0xFFF6F6F6);
            viewHolder.tvAdapterClassify.setTextColor(UIUtil.getColor(R.color.backgroundRed));
        } else {
            viewHolder.tvAdapterClassify.setBackgroundColor(Color.WHITE);

            viewHolder.tvAdapterClassify.setTextColor(Color.parseColor("#ff494949"));
        }
        return convertView;
    }

    public void notifyData(int position) {
        this.mPosition = position;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView tvAdapterClassify;

        public ViewHolder(View view) {
            tvAdapterClassify = view.findViewById(R.id.tvAdapterClassify);
        }
    }
}
