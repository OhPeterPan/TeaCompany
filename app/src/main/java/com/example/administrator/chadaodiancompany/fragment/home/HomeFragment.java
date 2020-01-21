package com.example.administrator.chadaodiancompany.fragment.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.HomeSellAdapter;
import com.example.administrator.chadaodiancompany.bean.HomeDetailBean;
import com.example.administrator.chadaodiancompany.bean.HomeDetailBean.HomeDataBean.HomeStatisticsBean;
import com.example.administrator.chadaodiancompany.fragment.BaseFragment;
import com.example.administrator.chadaodiancompany.presenter.HomeDetailPresenter;
import com.example.administrator.chadaodiancompany.util.UIUtil;
import com.example.administrator.chadaodiancompany.view.IHomeDetailView;
import com.example.administrator.chadaodiancompany.view.NestGridView;

import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<HomeDetailPresenter> implements IHomeDetailView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @BindView(R.id.tvTradeMessage)
    TextView tvTradeMessage;
    @BindView(R.id.tvTradeOrderNumber)
    TextView tvTradeOrderNumber;
    @BindView(R.id.tvFragHomeWaitPay)
    TextView tvFragHomeWaitPay;
    @BindView(R.id.tvFragHomeWaitSendGood)
    TextView tvFragHomeWaitSendGood;
    @BindView(R.id.tvStoreGoodMessage)
    TextView tvStoreGoodMessage;
    @BindView(R.id.tvFragHomeVending)
    TextView tvFragHomeVending;
    @BindView(R.id.tvStoreSellMessage)
    TextView tvStoreSellMessage;
    @BindView(R.id.gridHomeMessage)
    NestGridView gridHomeMessage;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private String sellStr[] = new String[9];
    private List<HomeStatisticsBean> homeList;
    private HomeSellAdapter adapter;
    private OnClickStateListener onClickStateListener;

    @Override
    protected void initListener() {
        swipeRefresh.setOnRefreshListener(this);
        tvFragHomeWaitSendGood.setOnClickListener(this);
        tvFragHomeVending.setOnClickListener(this);
        tvFragHomeWaitPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onClickStateListener == null) return;
        onClickStateListener.onClickStateListener(v);
    }

    @Override
    protected void initData() {

        presenter.sendNet(key, true);
    }

    @Override
    protected void initPresenter() {
        presenter = new HomeDetailPresenter(this);
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    public void showError(Throwable throwable) {
        super.showError(throwable);
        if (swipeRefresh.isRefreshing())
            swipeRefresh.setRefreshing(false);
    }

    @Override
    public void getHomeDetailSuccess(String result) {
        LogUtils.iTag("wak", "首页：" + result);
        if (swipeRefresh.isRefreshing())
            swipeRefresh.setRefreshing(false);
        HomeDetailBean homeDetailBean = JSON.parseObject(result, HomeDetailBean.class);
        HomeDetailBean.HomeDataBean homeDataBean = homeDetailBean.datas;
        tvTradeOrderNumber.setText("交易中的订单：" + homeDataBean.progressing);
        // tvTradeOrderNumber.setText(String.format("交易中的订单：\n%s", homeDataBean.progressing));
        UIUtil.setBadgeView(tvFragHomeWaitPay).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeText(homeDataBean.no_payment).setGravityOffset(0, -2, true)
                .setBadgeTextSize(8, true);

        UIUtil.setBadgeView(tvFragHomeWaitSendGood).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeText(homeDataBean.no_delivery).setGravityOffset(0, -2, true)
                .setBadgeTextSize(8, true);

        UIUtil.setBadgeView(tvFragHomeVending).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeText(homeDataBean.goods_online).setGravityOffset(0, -2, true)
                .setBadgeTextSize(8, true);
        homeList = homeDataBean.statistics;
        initSellList();
    }

    private void initSellList() {
        HomeStatisticsBean homeStatisticsBean;
        for (int i = 0; i < homeList.size(); i++) {
            homeStatisticsBean = homeList.get(i);
            if (i == 0) {
                sellStr[0] = homeStatisticsBean.item;
                sellStr[1] = homeStatisticsBean.order;
                sellStr[2] = homeStatisticsBean.order_money;
            } else if (i == 1) {
                sellStr[3] = homeStatisticsBean.item_y;
                sellStr[4] = homeStatisticsBean.order_y + "笔";
                sellStr[5] = homeStatisticsBean.order_money_y;
            } else {
                sellStr[6] = homeStatisticsBean.item_m;
                sellStr[7] = homeStatisticsBean.order_y + "笔";
                sellStr[8] = homeStatisticsBean.order_money_y;
            }
        }
        setAdapter();
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new HomeSellAdapter(mContext, sellStr);
            gridHomeMessage.setAdapter(adapter);
        } else {
            adapter.notifyData(sellStr);
        }
    }

    @Override
    public void onRefresh() {
        presenter.sendNet(key, false);
    }

    public void setOnClickStateListener(OnClickStateListener onClickStateListener) {
        this.onClickStateListener = onClickStateListener;
    }

    public interface OnClickStateListener {
        void onClickStateListener(View v);
    }
}
