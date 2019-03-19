package com.example.administrator.chadaodiancompany.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.OrderIndexAdapter;
import com.example.administrator.chadaodiancompany.bean.OrderAdapterType;
import com.example.administrator.chadaodiancompany.bean.OrderGoodBean;
import com.example.administrator.chadaodiancompany.bean.OrderIndexBean;
import com.example.administrator.chadaodiancompany.bean.OrderIndexBean.OrderIndexDataBean.OrderDetailListBean;
import com.example.administrator.chadaodiancompany.dialog.InputGoodNumDialog;
import com.example.administrator.chadaodiancompany.dialog.InputGoodPriceDialog;
import com.example.administrator.chadaodiancompany.dialog.OrderCancelReasonDialog;
import com.example.administrator.chadaodiancompany.event.MessageEvent;
import com.example.administrator.chadaodiancompany.fragment.BaseFragment;
import com.example.administrator.chadaodiancompany.popupWindow.WaitSendGoodPopupWindow;
import com.example.administrator.chadaodiancompany.presenter.OrderIndexPresenter;
import com.example.administrator.chadaodiancompany.ui.OrderDetailActivity;
import com.example.administrator.chadaodiancompany.ui.OrderSendGoodIndexActivity;
import com.example.administrator.chadaodiancompany.ui.order.ConfirmMoneyActivity;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IOrderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderFragment extends BaseFragment<OrderIndexPresenter> implements IOrderView, RadioGroup.OnCheckedChangeListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener, WaitSendGoodPopupWindow.OnPopupWindowClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OrderCancelReasonDialog.OnChooseCancelOrderReasonListener, InputGoodNumDialog.OnConfirmNumberListener, InputGoodPriceDialog.OnConfirmGoodPriceListener {
    public static final int REQUEST_CODE = 0x000;
    public static final int HOME_STATE_RESULT_CODE = 0x001;//首页的待付款以及待发货
    public static final int KEYWORD_RESULT_CODE = 0x002;//关键词
    @BindView(R.id.rbAllOrder)
    RadioButton rbAllOrder;
    @BindView(R.id.rbWaitPayOrder)
    RadioButton rbWaitPayOrder;
    @BindView(R.id.rbWaitSendOrder)
    RadioButton rbWaitSendOrder;
    @BindView(R.id.rbSendingOrder)
    RadioButton rbSendingOrder;
    @BindView(R.id.rbFinishOrder)
    RadioButton rbFinishOrder;
    @BindView(R.id.rgOrder)
    RadioGroup rgOrder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private String orderState = "0";
    private OrderIndexBean orderIndexBean;
    private List<OrderDetailListBean> orderList;
    private List<OrderAdapterType> orderTypeList;
    private String tag;//判断是收银待付款或者待发货
    private String keyword = "";
    private WaitSendGoodPopupWindow popupWindow;
    private String state = "全部";
    private View emptyView;
    private OrderAdapterType adapterItem;
    private int mPosition;
    private OrderCancelReasonDialog cancelDialog;
    private int flag = 0;//0修改运费  1修改金额
    private InputGoodNumDialog inputGoodNumDialog;
    private float changeData;
    private InputGoodPriceDialog inputGoodPriceDialog;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.code == CommonUtil.MessageEventCode.SEND_GOOD_CODE_SUCCESS) {
            sendNet(true);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            keyword = "";
            sendNet(true);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            tag = bundle.getString(CommonUtil.TAG);

        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initPresenter() {
        presenter = new OrderIndexPresenter(this);
    }

    @Override
    protected void initData() {
        initAdapter();
        if (StringUtils.equals("0", tag)) {
            rbWaitSendOrder.setChecked(true);
            orderState = "20";
        } else if (StringUtils.equals("1", tag)) {
            rbWaitPayOrder.setChecked(true);
            orderState = "10";
        }
        sendNet(true);
    }

    private void initAdapter() {
        adapter = new OrderIndexAdapter(null);
        initAdapterParams(recyclerView, adapter);
        initLinearRecyclerView(recyclerView, adapter);
        adapter.setOnLoadMoreListener(this, recyclerView);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {//去订单详情页
        adapterItem = (OrderAdapterType) adapter.getItem(position);
        if (adapterItem == null) return;
        OrderDetailActivity.launch(this, adapterItem.order_id);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter ada, View v, int position) {
        adapterItem = (OrderAdapterType) adapter.getItem(position);
        this.mPosition = position;
        if (adapterItem == null) return;
        switch (v.getId()) {
            case R.id.tvAdapterCancelOrder://取消订单
                cancelOrderDialog();
                break;
            case R.id.tvAdapterChangeOrderExpenses://修改运费
                flag = 0;
                changPriceAndSheep();
                break;
            case R.id.tvAdapterChangeOrderAmount://修改价格
                flag = 1;
                // changPriceAndSheep();
                changeGoodPriceDialog();
                break;
            case R.id.tvAdapterChangeOrderSendGood://设置发货
                OrderSendGoodIndexActivity.launch(mContext, adapterItem.order_id);
                break;
            case R.id.tvAdapterConfirmMoney://确认收款
                //OrderSendGoodIndexActivity.launch(mContext, adapterItem.order_id);
                ConfirmMoneyActivity.launchResultFrag(this, adapterItem.order_id);
                break;
        }
    }

    private void changeGoodPriceDialog() {
        if (inputGoodPriceDialog == null) {
            inputGoodPriceDialog = new InputGoodPriceDialog(mContext);
            inputGoodPriceDialog.setOnConfirmListener(this);
        }
        inputGoodPriceDialog.setNumber(new BigDecimal(adapterItem.payMount).floatValue());
        inputGoodPriceDialog.show();
    }

    @Override
    public void confirmGoodPrice(float number, String remark) {
        this.changeData = number;
        presenter.sendNetChangeOrderAmount(key, adapterItem.order_id, number + "", remark);
    }

    private void changPriceAndSheep() {
        if (inputGoodNumDialog == null) {
            inputGoodNumDialog = new InputGoodNumDialog(mContext);
            inputGoodNumDialog.setOnConfirmListener(this);
        }
        if (flag == 0) {
            inputGoodNumDialog.setNumber(new BigDecimal(adapterItem.shippingFee).floatValue());
        } else {
            inputGoodNumDialog.setNumber(new BigDecimal(adapterItem.payMount).floatValue());
        }
        inputGoodNumDialog.show();
    }

    @Override
    public void confirmNumber(float number) {
        this.changeData = number;
        LogUtil.logI(key + ":::" + adapterItem.order_id + "::::" + number);
        if (flag == 0) {
            presenter.sendNetChangeSheep(key, adapterItem.order_id, number + "");
        } else {
            //presenter.sendNetChangeOrderAmount(key, adapterItem.order_id, number + "");
        }
    }

    @Override
    public void changeOrderAmountSuccess(String result) {
        ToastUtil.showSuccess("修改成功！");
        if (adapter == null) return;
        if (flag == 0) {
            adapterItem.shippingFee = changeData + "";
        } else {
            adapterItem.payMount = changeData + "";
        }
        adapter.notifyItemChanged(mPosition);
    }

    @Override
    public void changeSheepSuccess(String result) {
        ToastUtil.showSuccess("修改成功！");
        if (adapter == null) return;
        if (flag == 0) {
            adapterItem.shippingFee = changeData + "";
        } else {
            adapterItem.payMount = changeData + "";
        }
        adapter.notifyItemChanged(mPosition);
    }

    private void cancelOrderDialog() {
        if (cancelDialog == null) {
            cancelDialog = new OrderCancelReasonDialog(mContext);
            cancelDialog.setOnChooseCancelOrderReasonListener(this);
        }
        cancelDialog.show();
    }

    @Override
    public void reasonDetail(String reason) {
        presenter.sendNetCancelOrder(key, adapterItem.order_id, reason);
    }

    @Override
    public void cancelOrderSuccess(String result) {
        ToastUtil.showSuccess("订单取消成功！");
        swipeRefresh.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cancelDialog != null)
            cancelDialog.onDestroy();

        if (inputGoodPriceDialog != null) inputGoodPriceDialog.destroy();
        if (inputGoodNumDialog != null) inputGoodNumDialog.destroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    private void sendNet(boolean showDialog) {
        isRefresh = true;
        curPage = 1;
        LogUtil.logI("参数：" + key + ":::" + curPage + ":::" + orderState + ":::" + keyword);
        presenter.sendNetGetOrderList(key, curPage, orderState, keyword, showDialog);
    }

    @Override
    protected void initListener() {
        rgOrder.setOnCheckedChangeListener(this);
        swipeRefresh.setOnRefreshListener(this);
        rbWaitSendOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbWaitSendOrder://待发货
                // LogUtil.logI("点击");
                showPopupWindow();
                break;
        }
    }

    private void showPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new WaitSendGoodPopupWindow(mContext);
            popupWindow.setOnclickStateListener(this);
        }
        popupWindow.setOrderSate(state);
        popupWindow.showPop(rbWaitSendOrder);
    }

    @Override
    public void onClickStateListener(String state, String orderState) {
        this.orderState = orderState;
        this.state = state;
        rbWaitSendOrder.setText(state);
        sendNet(true);
    }

    @Override
    public void onRefresh() {
        sendNet(false);
    }

    @Override
    public void getOrderListSuccess(String result) {
        LogUtil.logI("订单列表：" + result);
        curPage++;
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
        orderIndexBean = JSON.parseObject(result, OrderIndexBean.class);
        hasMore = orderIndexBean.hasmore;
        orderList = orderIndexBean.datas.order_list;
        if (orderList != null)
            new Thread(runnable).start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            parseOrderList();
        }
    };

    public synchronized void parseOrderList() {
        orderTypeList = new ArrayList<>();
        OrderAdapterType orderAdapterType;
        OrderDetailListBean orderDetailListBean;
        List<OrderGoodBean> goodList;
        OrderGoodBean goodBean;
        for (int i = 0; i < orderList.size(); i++) {
            orderDetailListBean = orderList.get(i);
            orderAdapterType = new OrderAdapterType();
            orderAdapterType.type = 0;
            orderAdapterType.order_id = orderDetailListBean.order_id;
            orderAdapterType.stateDesc = orderDetailListBean.state_desc;
            orderAdapterType.buyerName = orderDetailListBean.buyer_name;
            orderAdapterType.num = orderDetailListBean.deliver_num;
            orderAdapterType.order_sn = orderDetailListBean.order_sn;
            orderTypeList.add(orderAdapterType);
            goodList = orderDetailListBean.order_goods;
            for (int n = 0; n < goodList.size(); n++) {
                goodBean = goodList.get(n);
                orderAdapterType = new OrderAdapterType();
                orderAdapterType.type = 1;
                orderAdapterType.order_id = orderDetailListBean.order_id;
                orderAdapterType.stateDesc = orderDetailListBean.state_desc;
                orderAdapterType.extendOrderGoods = goodBean;
                orderAdapterType.num = orderDetailListBean.deliver_num;
                orderAdapterType.order_sn = orderDetailListBean.order_sn;
                orderTypeList.add(orderAdapterType);
            }
            orderAdapterType = new OrderAdapterType();
            orderAdapterType.type = 2;
            orderAdapterType.order_id = orderDetailListBean.order_id;
            orderAdapterType.payMount = orderDetailListBean.order_amount;
            orderAdapterType.stateDesc = orderDetailListBean.state_desc;
            orderAdapterType.shippingFee = orderDetailListBean.shipping_fee;
            orderAdapterType.storeShipping = orderDetailListBean.store_shipping;
            orderAdapterType.goodsList = orderDetailListBean.order_goods;
            orderAdapterType.num = orderDetailListBean.deliver_num;
            orderAdapterType.order_sn = orderDetailListBean.order_sn;
            orderTypeList.add(orderAdapterType);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        if (isRefresh) {
            adapter.setNewData(orderTypeList);
            addEmptyView();
        } else {
            adapter.addData(orderTypeList);
        }
        changeAdapterState();
    }

    private void addEmptyView() {
        if (emptyView == null) {
            emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty, null);
            TextView tvNoData = emptyView.findViewById(R.id.tvNoData);
            tvNoData.setText("暂无订单");
            if (adapter != null) adapter.setEmptyView(emptyView);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
        super.showError(throwable);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        keyword = "";
        switch (checkedId) {
            case R.id.rbAllOrder://全部
                orderState = "0";
                break;
            case R.id.rbWaitPayOrder://待付款
                orderState = "10";
                break;
            case R.id.rbWaitSendOrder://待发货
                LogUtil.logI("切换来");
                rbWaitSendOrder.setText("待发货 V");
                state = "全部";
                orderState = "20";
                break;
            case R.id.rbSendingOrder://已发货
                orderState = "30";
                break;
            case R.id.rbFinishOrder://已完成
                orderState = "40";
                break;
        }
        sendNet(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == HOME_STATE_RESULT_CODE) {
                if (data == null) return;
                tag = data.getStringExtra(CommonUtil.TAG);
                if (StringUtils.equals("0", tag)) {
                    rbWaitSendOrder.setChecked(true);
                } else {
                    rbWaitPayOrder.setChecked(true);
                }
            } else if (resultCode == KEYWORD_RESULT_CODE) {
                if (data == null) return;
                keyword = data.getStringExtra(CommonUtil.KEYWORD);
                sendNet(true);
            } else if (resultCode == OrderDetailActivity.ORDER_DETAIL_RESULT_CODE) {
                sendNet(true);
            }
        }
        if (requestCode == ConfirmMoneyActivity.REQUEST_CODE) {//确认收款
            if (resultCode == ConfirmMoneyActivity.RESULT_CODE) {
                sendNet(true);
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        presenter.sendNetGetOrderList(key, curPage, orderState, keyword, false);
    }

    public static Fragment newInstance(String tag) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CommonUtil.TAG, tag);
        fragment.setArguments(bundle);
        return fragment;
    }
}
