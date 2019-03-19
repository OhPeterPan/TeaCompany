package com.example.administrator.chadaodiancompany.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;

import java.util.List;

public class MessageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MessageAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
