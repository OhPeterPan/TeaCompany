package com.example.administrator.chadaodiancompany.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.ui.LoginActivity;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.SpUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IView;
import com.lzy.okgo.OkGo;

public abstract class BasePresenter<T extends IView> {
    public T mView;
    public boolean isError = true;

    public BasePresenter(T view) {
        this.mView = view;
    }

    public void detach() {
        if (mView != null)
            mView = null;
    }

    public void getResultError(Throwable throwable) {
        LogUtils.eTag("wak", throwable);
        if (mView == null) return;
        mView.hideLoading();
        mView.showError(throwable);
    }

    public void checkJson(String result) {
        LogUtil.logI("结果是？" + result);
        if (mView != null)
            mView.hideLoading();
        if (StringUtils.isEmpty(result)) {
            isError = true;
            return;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject == null) {
                isError = true;
                return;
            }
            JSONObject datas = jsonObject.getJSONObject("datas");
            if (datas == null) {
                isError = true;
                return;
            }
            if (datas != null && datas.containsKey("error")) {
                isError = true;
                ToastUtil.showError(datas.getString("error"));
                viewErrorInfo();
                if (TextUtils.equals("请登录", datas.getString("error"))) {
                    OkGo.getInstance().cancelAll();
                    SpUtil.clearAll();
                    if (mView instanceof Fragment) {
                        LoginActivity.launchNewFlag(((Fragment) mView).getActivity());
                    } else {
                        LoginActivity.launchNewFlag((Context) mView);
                    }
                }
                return;
            }
            isError = false;
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
            LogUtils.eTag("wak", e);
        }
    }

    /**
     * 后台返回错误时有的界面需要处理
     */
    public void viewErrorInfo() {

    }
}
