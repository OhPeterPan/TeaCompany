package com.example.administrator.chadaodiancompany.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.example.administrator.chadaodiancompany.bean.CityBean;
import com.example.administrator.chadaodiancompany.bean.CityBean.AreaListDatasBean.AreaListBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;


public class CityUtils {
    private Context context;
    private Handler handler;
    private List<AreaListBean> list;
    private List<AreaListBean> cityList;
    private List<AreaListBean> districtsList;
    private Message msg;

    public CityUtils(final Context context, final Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    /***
     * 初始化省份
     */
    public void initProvince() {

        list = new ArrayList<>();
        OkGo.<String>get(NetUtils.BASEURLCOM + NetUtils.PROVINCE_LIST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            CityBean cityBean = JSON.parseObject(response.body(), CityBean.class);
                            list = cityBean.datas.area_list;
                            msg = Message.obtain();
                            msg.what = 1;
                            msg.obj = list;
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    public void initCities(String pcode) {

        cityList = new ArrayList<>();
        OkGo.<String>get(NetUtils.BASEURLCOM + NetUtils.PROVINCE_LIST)
                .params("area_id", pcode)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            CityBean cityBean = JSON.parseObject(response.body(), CityBean.class);
                            cityList = cityBean.datas.area_list;
                            msg = Message.obtain();
                            msg.what = 2;
                            msg.obj = cityList;
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void initDistricts(String pcode) {

        districtsList = new ArrayList<>();
        OkGo.<String>get(NetUtils.BASEURLCOM + NetUtils.PROVINCE_LIST)
                .params("area_id", pcode)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            CityBean cityBean = JSON.parseObject(response.body(), CityBean.class);
                            districtsList = cityBean.datas.area_list;
                            msg = Message.obtain();
                            msg.what = 3;
                            msg.obj = districtsList;
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
