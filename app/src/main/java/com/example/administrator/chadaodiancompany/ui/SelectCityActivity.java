package com.example.administrator.chadaodiancompany.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.City;
import com.example.administrator.chadaodiancompany.bean.CityBean.AreaListDatasBean.AreaListBean;
import com.example.administrator.chadaodiancompany.util.CityUtils;
import com.example.administrator.chadaodiancompany.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectCityActivity extends BaseToolbarActivity implements OnClickListener {

    private static int PROVINCE = 0x00;
    private static int CITY = 0x01;
    private static int DISTRICT = 0x02;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    /**
     * 省份，城市，区
     */
    private TextView[] tvs = new TextView[3];
    /**
     * rediogroup,
     */
    private int[] ids = {R.id.rb_province, R.id.rb_city, R.id.rb_district};
    private City city;
    int last, current;

    private void initView() {
        city = new City();
        Intent in = getIntent();
        city = in.getParcelableExtra("city");

        for (int i = 0; i < tvs.length; i++) {
            tvs[i] = findViewById(ids[i]);
            tvs[i].setOnClickListener(this);
        }

        if (city == null) {
            city = new City();
            city.setProvince("");
            city.setCity("");
            city.setDistrict("");
        } else {
            if (city.getProvince() != null && !city.getProvince().equals("")) {
                tvs[0].setText(city.getProvince());
            }
            if (city.getCity() != null && !city.getCity().equals("")) {
                tvs[1].setText(city.getCity());
            }
            if (city.getDistrict() != null && !city.getDistrict().equals("")) {
                tvs[2].setText(city.getDistrict());
            }
        }
        regions = new ArrayList<>();
        util = new CityUtils(this, hand);
        util.initProvince();

        tvs[current].setBackgroundColor(0xff999999);

    }

    private CityAdapter adapter;

    public static void launch(Context ctx) {
        Intent intent = new Intent(ctx, SelectCityActivity.class);
        ((AppCompatActivity) ctx).startActivityForResult(intent, CommonUtil.REQUEST_CODE);
    }

    private class CityAdapter extends BaseQuickAdapter<AreaListBean, BaseViewHolder> {

        public CityAdapter(@Nullable List<AreaListBean> data) {
            super(R.layout.item_city, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, AreaListBean item) {
            holder.setText(R.id.tv_city, item.area_name);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hand.removeCallbacksAndMessages(null);
        hand = null;
    }

    private void setAdapter() {
        adapter = new CityAdapter(null);
        adapter.isFirstOnly(true);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        tvActTitle.setText("选择地址");
        ivActRightSetting.setVisibility(View.GONE);
        initView();
        setAdapter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_selectcity;
    }

    private List<AreaListBean> list;
    private List<AreaListBean> cityList;
    Handler hand = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {

                case 1:
                    System.out.println("省份列表what======" + msg.what);
                    regions = (ArrayList<AreaListBean>) msg.obj;
                    adapter.setNewData(regions);
                    break;

                case 2:
                    System.out.println("城市列表what======" + msg.what);
                    // list = new ArrayList<>();
                    list = (ArrayList<AreaListBean>) msg.obj;
                    adapter.setNewData(list);
                    break;

                case 3:
                    System.out.println("区/县列表what======" + msg.what);
                    //cityList = new ArrayList<>();
                    cityList = (ArrayList<AreaListBean>) msg.obj;
                    adapter.setNewData(cityList);
                    break;
            }
        }
    };
    BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int arg2) {

            if (current == PROVINCE) {
                String newProvince = regions.get(arg2).area_name;
                if (!newProvince.equals(city.getProvince())) {
                    city.setProvince(newProvince);
                    tvs[0].setText(regions.get(arg2).area_name);
                    city.setRegionId(regions.get(arg2).area_id);
                    city.setProvinceCode(regions.get(arg2).area_id);
                    city.setCityCode("");
                    city.setDistrictCode("");
                    tvs[1].setText("二级地区");
                    tvs[2].setText("三级地区 ");
                }

                current = 1;
                // 点击省份列表中的省份就初始化城市列表
                util.initCities(city.getProvinceCode());
            } else if (current == CITY) {
                String newCity = list.get(arg2).area_name;
                if (!newCity.equals(city.getCity())) {
                    city.setCity(newCity);
                    tvs[1].setText(list.get(arg2).area_name);
                    city.setRegionId(list.get(arg2).area_id);
                    city.setCityCode(list.get(arg2).area_id);
                    city.setDistrictCode("");
                    tvs[2].setText("三级地区  ");
                }

                // 点击城市列表中的城市就初始化区县列表
                util.initDistricts(city.getCityCode());
                current = 2;

            } else if (current == DISTRICT) {
                current = 2;
                city.setDistrictCode(cityList.get(arg2).area_id);
                city.setRegionId(cityList.get(arg2).area_id);
                city.setDistrict(cityList.get(arg2).area_name);
                tvs[2].setText(cityList.get(arg2).area_name);

                Intent in = new Intent();
                in.putExtra("city", city);
                setResult(CommonUtil.RESULT_CODE, in);
                finish();
            }
            tvs[last].setBackgroundColor(Color.WHITE);
            tvs[current].setBackgroundColor(Color.GRAY);
            last = current;
        }

    };
    private List<AreaListBean> regions;
    private CityUtils util;

    @Override
    public void onClick(View v) {
        if (ids[0] == v.getId()) {
            current = 0;
            util.initProvince();
            tvs[last].setBackgroundColor(Color.WHITE);
            tvs[current].setBackgroundColor(Color.GRAY);
            last = current;
        } else if (ids[1] == v.getId()) {
            if (city.getProvinceCode() == null
                    || city.getProvinceCode().equals("")) {
                current = 0;
                Toast.makeText(context, "您还没有选择省份", Toast.LENGTH_SHORT).show();
                return;
            }
            util.initCities(city.getProvinceCode());
            current = 1;
            tvs[last].setBackgroundColor(Color.WHITE);
            tvs[current].setBackgroundColor(Color.GRAY);
            last = current;
        } else if (ids[2] == v.getId()) {
            if (city.getProvinceCode() == null
                    || city.getProvinceCode().equals("")) {
                Toast.makeText(context, "您还没有选择省份", Toast.LENGTH_SHORT).show();
                current = 0;
                util.initProvince();
                return;
            } else if (city.getCityCode() == null
                    || city.getCityCode().equals("")) {
                Toast.makeText(context, "您还没有选择城市", Toast.LENGTH_SHORT).show();
                current = 1;
                util.initCities(city.getProvince());
                return;
            }
            current = 2;
            util.initDistricts(city.getCityCode());
            tvs[last].setBackgroundColor(Color.WHITE);
            tvs[current].setBackgroundColor(Color.GRAY);
            last = current;
        }
    }
}
