package com.example.administrator.chadaodiancompany.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.ViewSkeletonScreen;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.CommonResponse;
import com.example.administrator.chadaodiancompany.bean.HomeInfoBean;
import com.example.administrator.chadaodiancompany.fragment.BaseFragment;
import com.example.administrator.chadaodiancompany.presenter.HomeInfoPresenter;
import com.example.administrator.chadaodiancompany.ui.good.GoodActivity;
import com.example.administrator.chadaodiancompany.ui.member.MemberIndexActivity;
import com.example.administrator.chadaodiancompany.ui.message.MessageActivity;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.UIUtil;
import com.example.administrator.chadaodiancompany.view.IHomeDetailView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.BindView;

public class NewHomeFragment extends BaseFragment<HomeInfoPresenter> implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, IHomeDetailView {
    @BindView(R.id.tvMainMoney)
    TextView tvMainMoney;
    @BindView(R.id.tvMainOrderCount)
    TextView tvMainOrderCount;
    @BindView(R.id.tvMainOrderPrice)
    TextView tvMainOrderPrice;
    @BindView(R.id.tvMainOrderTotal)
    TextView tvMainOrderTotal;
    @BindView(R.id.tvMainMemberCount)
    TextView tvMainMemberCount;
    @BindView(R.id.tvMainGood)
    TextView tvMainGood;
    @BindView(R.id.tvMainMember)
    TextView tvMainMember;
    @BindView(R.id.tvMainMessage)
    TextView tvMainMessage;
    @BindView(R.id.tvMainOrdering)
    TextView tvMainOrdering;
    @BindView(R.id.tvMainWaitMoney)
    TextView tvMainWaitMoney;
    @BindView(R.id.tvMainWaitSendGood)
    TextView tvMainWaitSendGood;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    private ViewSkeletonScreen skeletonScreen;
    private OnClickStateListener onClickStateListener;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_new_main;
    }

    @Override
    protected void initPresenter() {
        presenter = new HomeInfoPresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        skeletonScreen = Skeleton.bind(swipeRefresh)
                .load(R.layout.activity_view_skeleton)
                .duration(1000)
                .color(R.color.white)
                .angle(0)
                .show();
        sendNet(true);
    }

    private void sendNet(boolean isShowDialog) {
        presenter.sendNetGetHomeInfo(key, isShowDialog);
    }

    @Override
    protected void initListener() {
        tvMainGood.setOnClickListener(this);
        tvMainMember.setOnClickListener(this);
        tvMainMessage.setOnClickListener(this);
        tvMainWaitMoney.setOnClickListener(this);
        tvMainWaitSendGood.setOnClickListener(this);
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onClickStateListener == null) return;
        onClickStateListener.onClickStateListener(v);
        switch (v.getId()) {
            case R.id.tvMainGood://商品
                GoodActivity.launch(getContext());
                break;
            case R.id.tvMainMember://会员
                MemberIndexActivity.launch(mContext);
                break;
            case R.id.tvMainMessage://消息
                MessageActivity.launch(mContext);
                break;
       /*     case R.id.tvMainWaitMoney://待付款

                break;
            case R.id.tvMainWaitSendGood://待发货

                break;*/
        }
    }

    @Override
    public void onRefresh() {
        sendNet(false);
    }

    @Override
    public void getHomeDetailSuccess(String result) {
        LogUtil.logI("result:" + result);
        if (swipeRefresh.isRefreshing())
            swipeRefresh.setRefreshing(false);
        Type type = new TypeToken<CommonResponse<HomeInfoBean>>() {
        }.getType();
        CommonResponse<HomeInfoBean> response = JSON.parseObject(result, type);
        HomeInfoBean bean = response.datas;
        tvMainMoney.setText(bean.today_amount);
        tvMainOrderCount.setText(String.format("%s笔", bean.today_count));
        tvMainOrderPrice.setText(String.format("本月订单额\n\n%s", bean.month_amount));
        tvMainOrderTotal.setText(String.format("本月订单量\n\n%s", bean.month_count));
        tvMainMemberCount.setText(String.format("会员总数\n\n%s", bean.member_count));
        tvMainOrdering.setText(String.format("交易中：%s", bean.progressing));
        UIUtil.setBadgeView(tvMainWaitMoney).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(45, 5, true)
                .setBadgeTextSize(8, true).setBadgeNumber(Integer.valueOf(bean.no_payment));

        UIUtil.setBadgeView(tvMainWaitSendGood).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(45, 5, true)
                .setBadgeTextSize(8, true).setBadgeNumber(Integer.valueOf(bean.no_delivery));

        skeletonScreen.hide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelNet();
    }

    public void setOnClickStateListener(OnClickStateListener onClickStateListener) {
        this.onClickStateListener = onClickStateListener;
    }

    public interface OnClickStateListener {
        void onClickStateListener(View v);
    }
}
