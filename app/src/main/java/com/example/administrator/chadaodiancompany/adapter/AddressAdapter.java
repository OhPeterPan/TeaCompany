package com.example.administrator.chadaodiancompany.adapter;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.AddressListBean;

public class AddressAdapter extends BaseQuickAdapter<AddressListBean, BaseViewHolder> {
    public AddressAdapter() {
        super(R.layout.adapter_address_list, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, AddressListBean item) {
        holder.setText(R.id.tvAdapterAddressPhone, item.telphone);
        holder.setText(R.id.tvAdapterAddressName, item.seller_name);
        holder.setText(R.id.tvAdapterAddressDetail, item.area_info + " " + item.address);

        if (StringUtils.equals("1", item.is_default)) {
            holder.setChecked(R.id.cbAdapterAddressDefault, true);
        } else {
            holder.setChecked(R.id.cbAdapterAddressDefault, false);
        }

        holder.addOnClickListener(R.id.cbAdapterAddressDefault);
        holder.addOnClickListener(R.id.cbAdapterAddressEdit);
        holder.addOnClickListener(R.id.cbAdapterAddressDelete);

    }
}
