package com.example.administrator.chadaodiancompany.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.AddressListBean;
import com.example.administrator.chadaodiancompany.bean.City;
import com.example.administrator.chadaodiancompany.presenter.AddressManagerPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.UIUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IAddressManagerView;
import com.suke.widget.SwitchButton;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class AddressManagerActivity extends BaseToolbarActivity<AddressManagerPresenter> implements View.OnClickListener, IAddressManagerView {
    public static final int REQUEST_CODE = 0;
    @BindView(R.id.etAddressManagerUserName)
    EditText etAddressManagerUserName;
    @BindView(R.id.ivClearAddressManagerUserName)
    ImageView ivClearAddressManagerUserName;
    @BindView(R.id.etAddressManagerUserPhone)
    EditText etAddressManagerUserPhone;
    @BindView(R.id.ivClearAddressManagerUserPhone)
    ImageView ivClearAddressManagerUserPhone;

    @BindView(R.id.etAddressManagerCompany)
    EditText etAddressManagerCompany;
    @BindView(R.id.ivClearAddressManagerCompany)
    ImageView ivClearAddressManagerCompany;

    @BindView(R.id.tvAddressManagerChoose)
    TextView tvAddressManagerChoose;
    @BindView(R.id.etAddressManagerAddressInfo)
    EditText etAddressManagerAddressInfo;
    @BindView(R.id.switchAddress)
    SwitchButton switchAddress;
    @BindView(R.id.tvAddressSave)
    TextView tvAddressSave;
    private AddressListBean bean;
    private int flag;
    private String addressId;
    private String areaId;
    private String cityId;
    private City cityBean;

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
        presenter = new AddressManagerPresenter(this);
    }

    @Override
    protected void initData() {
        ivActRightSetting.setVisibility(View.GONE);
        initIntent();

    }

    private void initIntent() {
        Intent intent = getIntent();
        flag = intent.getIntExtra(CommonUtil.FLAG, 0);
        if (flag == 0) {
            tvActTitle.setText("新增地址");
        } else {
            tvActTitle.setText("编辑地址");
            bean = intent.getParcelableExtra(CommonUtil.ADDRESS_INFO);
            if (bean != null)
                initPageInfo();
        }
    }

    private void initPageInfo() {
        addressId = bean.address_id;
        UIUtil.setData(etAddressManagerUserName, bean.seller_name);
        UIUtil.setData(etAddressManagerUserPhone, bean.telphone);
        UIUtil.setData(tvAddressManagerChoose, bean.area_info);
        UIUtil.setData(etAddressManagerAddressInfo, bean.address);
        UIUtil.setData(etAddressManagerCompany, bean.company);
        switchAddress.setChecked(TextUtils.equals("1", bean.is_default));
    }

    @Override
    protected void initListener() {
        tvAddressManagerChoose.setOnClickListener(this);
        ivClearAddressManagerUserName.setOnClickListener(this);
        ivClearAddressManagerUserPhone.setOnClickListener(this);
        ivClearAddressManagerCompany.setOnClickListener(this);
        tvAddressSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddressManagerChoose:
                SelectCityActivity.launch(context);
                break;
            case R.id.ivClearAddressManagerUserName:
                etAddressManagerUserName.setText("");
                break;
            case R.id.ivClearAddressManagerUserPhone:
                etAddressManagerUserPhone.setText("");
                break;
            case R.id.tvAddressSave://保存地址
                saveAddress();
                break;
            case R.id.ivClearAddressManagerCompany:
                etAddressManagerCompany.setText("");
                break;
        }
    }

    private void saveAddress() {
        String name = etAddressManagerUserName.getText().toString().trim();
        String phone = etAddressManagerUserPhone.getText().toString().trim();
        String company = etAddressManagerCompany.getText().toString().trim();
        String region = tvAddressManagerChoose.getText().toString().trim();
        String address = etAddressManagerAddressInfo.getText().toString().trim();
        String isDefault = "0";
        if (StringUtils.isEmpty(addressId)) {
            addressId = "";
        }
        if (switchAddress.isChecked()) {
            isDefault = "1";
        } else {
            isDefault = "0";
        }
        presenter.sendNetAddressManager(key, addressId, name, phone, areaId, cityId, region, address, company, isDefault);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CommonUtil.RESULT_CODE) {
            if (requestCode == CommonUtil.REQUEST_CODE) {
                cityBean = data.getParcelableExtra("city");
                LogUtil.logI(cityBean.getProvince() + cityBean.getCity() + cityBean.getDistrict());
                areaId = cityBean.getDistrictCode();
                cityId = cityBean.getCityCode();
                LogUtil.logI(cityBean.getProvinceCode() + "::::" + areaId + ":::::" + cityId);
                tvAddressManagerChoose.setText(cityBean.getProvince() + " " + cityBean.getCity() + " " + cityBean.getDistrict());
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    public static void launch(Context ctx, AddressListBean bean, int flag) {
        Intent intent = new Intent(ctx, AddressManagerActivity.class);
        intent.putExtra(CommonUtil.FLAG, flag);
        if (bean != null)
            intent.putExtra(CommonUtil.ADDRESS_INFO, bean);
        ((AppCompatActivity) ctx).startActivityForResult(intent, AddressActivity.REQUEST_CODE);
    }

    @Override
    public void getAddressManagerSuccess(String result) {
        setResult(CommonUtil.RESULT_CODE);
        finish();
    }
}
