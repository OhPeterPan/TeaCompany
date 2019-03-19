package com.example.administrator.chadaodiancompany.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.GoodClassifyBean.GoodClassifyData.GoodsClassifyListBean;
import com.example.administrator.chadaodiancompany.bean.GoodClassifyBean.GoodClassifyData.GoodsClassifyListBean.GoodsChildClassify;

import java.util.List;

public class GoodChildClassifyAdapter extends BaseListAdapter<GoodsClassifyListBean.GoodsChildClassify> {

    public GoodChildClassifyAdapter(Context context, List<GoodsChildClassify> list) {
        super(context, list);
    }

    @Override
    public View initView(GoodsChildClassify goodsClassifyListBean, View convertView, ViewGroup parent, int position) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_class, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAdapterClassify.setText(list.get(position).stc_name);
        return convertView;
    }

    public void notifyData(List<GoodsChildClassify> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView tvAdapterClassify;

        public ViewHolder(View view) {
            tvAdapterClassify = view.findViewById(R.id.tvAdapterClassify);
        }
    }
}
