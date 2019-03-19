package com.example.administrator.chadaodiancompany.ui;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.SendGoodIndexAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.AddressBean;
import com.example.administrator.chadaodiancompany.bean.OrderDetailBean;
import com.example.administrator.chadaodiancompany.bean.OrderDetailBean.OrderDetailData.OrderInfoBean;
import com.example.administrator.chadaodiancompany.bean.OrderGoodBean;
import com.example.administrator.chadaodiancompany.dialog.InputSendGoodNumberDialog;
import com.example.administrator.chadaodiancompany.presenter.SendGoodIndexPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.TimeUtil;
import com.example.administrator.chadaodiancompany.viewImpl.ISendGoodIndexView;

import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

public class OrderSendGoodIndexActivity extends BaseToolbarActivity<SendGoodIndexPresenter>
        implements ISendGoodIndexView, BaseQuickAdapter.OnItemChildClickListener,
        InputSendGoodNumberDialog.ChooseGoodNumberListener, View.OnClickListener {
    @BindView(R.id.tvSendGoodIndexTime)
    TextView tvSendGoodIndexTime;
    @BindView(R.id.tvSendGoodIndexOrderSn)
    TextView tvSendGoodIndexOrderSn;
    @BindView(R.id.tvSendGoodIndexOrderState)
    TextView tvSendGoodIndexOrderState;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvNextButton)
    TextView tvNextButton;
    private String orderId;
    private OrderInfoBean orderInfo;
    private int num;
    private SendGoodIndexAdapter adapter;
    private List<OrderGoodBean> goodsList;
    private TextView tvSendGoodIndexSheep;
    private TextView tvSendGoodIndexAmount;
    private TextView tvSendGoodBuyerDetail, etSendGoodRemark;
    private View view;
    private InputSendGoodNumberDialog inputSendGoodNumberDialog;
    private OrderGoodBean goodBean;
    private int mPosition;
    private LinkedHashMap<String, String> paramsMap;
    private AddressBean reciverInfo;
    private List<OrderGoodBean> zengpinList;

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
        presenter = new SendGoodIndexPresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("设置发货");
        ivActRightSetting.setVisibility(View.GONE);
        tvSendGoodIndexTime.setText(TimeUtil.getNowTime(TimeUtil.DEFAULT_FORMAT));
        initIntent();
        initFootView();
        initAdapter();
        sendNet();
    }

    private void initFootView() {
        view = LayoutInflater.from(context).inflate(R.layout.foot_send_good_index, null);
        tvSendGoodIndexSheep = view.findViewById(R.id.tvSendGoodIndexSheep);
        tvSendGoodIndexAmount = view.findViewById(R.id.tvSendGoodIndexAmount);
        tvSendGoodBuyerDetail = view.findViewById(R.id.tvSendGoodBuyerDetail);
        etSendGoodRemark = view.findViewById(R.id.etSendGoodRemark);
    }

    private void initAdapter() {
        adapter = new SendGoodIndexAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter ada, View v, int position) {
        goodBean = adapter.getItem(position);
        if (goodBean == null) return;
        this.mPosition = position;
        switch (v.getId()) {
            case R.id.tvAdapterSendOrderSendNumber://填写发货数量
                showNumberDialog();
                break;
        }
    }

    private void showNumberDialog() {
        if (inputSendGoodNumberDialog == null) {
            inputSendGoodNumberDialog = new InputSendGoodNumberDialog(context);
            inputSendGoodNumberDialog.setOnChooseGoodNumberListener(this);
        }
        inputSendGoodNumberDialog.setGoodDetail(goodBean);
        inputSendGoodNumberDialog.show();
    }

    @Override
    public void onChooseGoodNumber(String number) {
        goodBean.send_num = number;
        //
        adapter.notifyItemChanged(mPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (inputSendGoodNumberDialog != null) inputSendGoodNumberDialog.destroy();
    }

    private void sendNet() {
        if (presenter == null) initPresenter();
        if (StringUtils.isEmpty(orderId)) return;
        presenter.sendNetOrderDetail(key, orderId);
    }

    @Override
    public void getOrderDetailSuccess(String result) {
        LogUtil.logI("发货：" + result);
        OrderDetailBean orderDetailBean = JSON.parseObject(result, OrderDetailBean.class);
        orderInfo = orderDetailBean.datas.order_info;
        if (orderInfo == null) return;
        num = orderInfo.num;
        goodsList = orderInfo.goods_list;
        zengpinList = orderInfo.zengpin_list;
        reciverInfo = orderInfo.reciver_info;
        tvSendGoodIndexOrderSn.setText(orderInfo.order_sn);
        tvSendGoodIndexOrderState.setText(orderInfo.state_desc);
        tvSendGoodIndexSheep.setText("运费 ¥ " + orderInfo.shipping_fee);
        tvSendGoodIndexAmount.setText("总金额 ¥ " + orderInfo.order_amount);
        tvSendGoodBuyerDetail.setText("买家： " + orderInfo.buyer_name + "\n收货人：" + reciverInfo.reciver_name + " " + reciverInfo.phone + "\n" + reciverInfo.address);
        adapter.removeAllFooterView();
        adapter.setFooterView(view);
        if (zengpinList != null && zengpinList.size() != 0) {
            goodsList.addAll(zengpinList);
        }
        adapter.setNewDataIndex(num + 1 + "", goodsList);
    }

    private void initIntent() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra(CommonUtil.ORDER_ID);
    }

    @Override
    protected void initListener() {
        tvNextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNextButton://下一步
                checkParamsMap();
                break;
        }
    }

    private void checkParamsMap() {
        if (reciverInfo == null) return;
        if (paramsMap == null) paramsMap = new LinkedHashMap<>();
        else paramsMap.clear();
        if (adapter == null) return;
        List<OrderGoodBean> mData = adapter.getData();
        if (mData == null) return;
        OrderGoodBean goodBean;
        for (int i = 0; i < mData.size(); i++) {
            goodBean = mData.get(i);
            paramsMap.put("deliver_num_" + goodBean.rec_id, goodBean.send_num);
            paramsMap.put("owe_deliver_num_" + goodBean.rec_id, goodBean.owe_deliver_num + "");
        }
        String remark = etSendGoodRemark.getText().toString().trim();
        paramsMap.put("num", num + 1 + "");

        paramsMap.put("reciver_area", reciverInfo.area);
        paramsMap.put("reciver_name", reciverInfo.reciver_name);
        paramsMap.put("reciver_street", reciverInfo.street);
        paramsMap.put("reciver_mob_phone", reciverInfo.mob_phone);
        paramsMap.put("reciver_tel_phone", reciverInfo.phone);
        paramsMap.put("deliver_explain", remark);
        paramsMap.put("order_id", orderInfo.order_id);

        AddressActivity.launch(context, paramsMap);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_good_index;
    }

    public static void launch(Context mContext, String orderId) {
        Intent intent = new Intent(mContext, OrderSendGoodIndexActivity.class);
        intent.putExtra(CommonUtil.ORDER_ID, orderId);
        mContext.startActivity(intent);
    }


}
