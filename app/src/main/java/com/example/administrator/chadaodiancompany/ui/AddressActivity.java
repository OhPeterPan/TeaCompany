package com.example.administrator.chadaodiancompany.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.AddressAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.AddressDataBean;
import com.example.administrator.chadaodiancompany.bean.AddressListBean;
import com.example.administrator.chadaodiancompany.dialog.IOSDialog;
import com.example.administrator.chadaodiancompany.presenter.AddressListPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IAddressModelView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

public class AddressActivity extends BaseToolbarActivity<AddressListPresenter> implements View.OnClickListener, IAddressModelView, BaseQuickAdapter.OnItemChildClickListener {
    public static final int REQUEST_CODE = 0;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvAddressNextButton)
    TextView tvAddressNextButton;
    private HashMap<String, String> hasMap;
    private AddressAdapter adapter;
    private IOSDialog deleteDialog;
    private AddressListBean adapterItem;
    private int mPosition;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initPresenter() {
        presenter = new AddressListPresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("设置发货");
        ivActRightSetting.setVisibility(View.GONE);
        tvTitleRight.setVisibility(View.VISIBLE);
        initIntent();
        initAdapter();
        sendNet();
    }

    private void initAdapter() {
        adapter = new AddressAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter ada, View v, int position) {
        adapterItem = adapter.getItem(position);
        if (adapterItem == null) return;
        this.mPosition = position;
        switch (v.getId()) {
            case R.id.cbAdapterAddressDefault:
                //ToastUtil.showInfoLong("默认");
                presenter.sendNetSetDefault(key, adapterItem.address_id);
                break;
            case R.id.cbAdapterAddressEdit://编辑
                AddressManagerActivity.launch(context, adapterItem, 1);
                break;
            case R.id.cbAdapterAddressDelete:
                showDeleteDialog();
                break;
        }
    }

    private void showDeleteDialog() {
        if (deleteDialog == null)
            deleteDialog = new IOSDialog(context).builder().setCancelable(false).setMsg("确认删除地址？").setTitle("删除地址")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.sendNetDeleteAddress(key, adapterItem.address_id);
                        }
                    });
        deleteDialog.show();
    }

    private void sendNet() {
        presenter.sendNetGetAddressList(key);
    }

    private void initIntent() {
        Intent intent = getIntent();
        hasMap = (HashMap<String, String>) intent.getSerializableExtra(CommonUtil.SEND_GOOD_MAP);
    }

    @Override
    public void getAddressListSuccess(String result) {
        LogUtil.logI("地址列表:" + result);
        AddressDataBean addressBean = JSON.parseObject(result, AddressDataBean.class);
        List<AddressListBean> addressList = addressBean.datas.address_list;
        adapter.setNewData(addressList);
    }

    @Override
    public void setAddressDefaultSuccess(String result) {
        ToastUtil.showSuccess("默认地址设置成功！");
        sendNet();
    }

    @Override
    public void deleteAddressSuccess(String result) {
        ToastUtil.showSuccess("地址删除成功！");
        if (adapter != null) adapter.remove(mPosition);
    }

    @Override
    protected void initListener() {
        tvTitleRight.setOnClickListener(this);
        tvAddressNextButton.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == CommonUtil.RESULT_CODE) {
                sendNet();
            }
        }
    }

    public static void launch(Context context, LinkedHashMap<String, String> paramsMap) {
        Intent intent = new Intent(context, AddressActivity.class);
        intent.putExtra(CommonUtil.SEND_GOOD_MAP, paramsMap);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTitleRight://新增
                AddressManagerActivity.launch(context, null, 0);
                break;
            case R.id.tvAddressNextButton://下一步
                sendGoodAddress();
                break;
        }
    }

    private void sendGoodAddress() {
        if (hasMap == null || hasMap.size() == 0) {
            ToastUtil.showError("数据错误请重新发货....");
            return;
        }

        if (adapter == null) return;
        List<AddressListBean> mData = adapter.getData();
        AddressListBean addressListBean;
        for (int i = 0; i < mData.size(); i++) {
            addressListBean = mData.get(i);
            if (StringUtils.equals("1", addressListBean.is_default)) {
                hasMap.put("daddress_id", addressListBean.address_id);
            }
        }


        ExpressManagerActivity.launch(context, hasMap);
        finish();
    }
}
