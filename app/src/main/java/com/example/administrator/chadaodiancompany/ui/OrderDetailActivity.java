package com.example.administrator.chadaodiancompany.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.MagicAdapter;
import com.example.administrator.chadaodiancompany.adapter.OrderDetailAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.AddressBean;
import com.example.administrator.chadaodiancompany.bean.DeliverBean;
import com.example.administrator.chadaodiancompany.bean.DeliverBean.DeliverGoodsList;
import com.example.administrator.chadaodiancompany.bean.InvoiceInfoBean;
import com.example.administrator.chadaodiancompany.bean.OrderDetailBean;
import com.example.administrator.chadaodiancompany.bean.OrderDetailBean.OrderDetailData.OrderInfoBean;
import com.example.administrator.chadaodiancompany.bean.OrderGoodBean;
import com.example.administrator.chadaodiancompany.dialog.InputGoodNumDialog;
import com.example.administrator.chadaodiancompany.dialog.InputGoodPriceDialog;
import com.example.administrator.chadaodiancompany.dialog.OrderCancelReasonDialog;
import com.example.administrator.chadaodiancompany.fragment.home.OrderFragment;
import com.example.administrator.chadaodiancompany.presenter.OrderDetailPresenter;
import com.example.administrator.chadaodiancompany.ui.order.ConfirmMoneyActivity;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.NumberUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.util.UIUtil;
import com.example.administrator.chadaodiancompany.view.GiftLinearLayout;
import com.example.administrator.chadaodiancompany.viewImpl.IOrderDetailView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class OrderDetailActivity extends BaseToolbarActivity<OrderDetailPresenter> implements IOrderDetailView, MagicAdapter.IOnPageChangeListener, View.OnClickListener, OrderCancelReasonDialog.OnChooseCancelOrderReasonListener, InputGoodNumDialog.OnConfirmNumberListener, InputGoodPriceDialog.OnConfirmGoodPriceListener {
    public static final int ORDER_DETAIL_RESULT_CODE = 0x004;
    @BindView(R.id.magicOrderDetail)
    MagicIndicator magicOrderDetail;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvAdapterCancelOrder)
    TextView tvAdapterCancelOrder;
    @BindView(R.id.tvAdapterChangeOrderExpenses)
    TextView tvAdapterChangeOrderExpenses;
    @BindView(R.id.tvAdapterChangeOrderAmount)
    TextView tvAdapterChangeOrderAmount;
    @BindView(R.id.llAdapterWaitPay)
    LinearLayout llAdapterWaitPay;
    @BindView(R.id.tvAdapterChangeOrderSendGood)
    TextView tvAdapterChangeOrderSendGood;
    @BindView(R.id.tvOrderLookExpress)
    TextView tvOrderLookExpress;
    @BindView(R.id.tvAdapterConfirmMoney)
    TextView tvAdapterConfirmMoney;
    @BindView(R.id.llAdapterWaitSend)
    LinearLayout llAdapterWaitSend;
    private List<String> indexList = Arrays.asList(UIUtil.getStringArray(R.array.take_good_index));//最大的发货次数集合
    private List<String> useIndexList = new ArrayList<>();//实际发货次数的集合
    private String orderId;
    private OrderDetailAdapter adapter;
    private View headView;
    private CommonNavigator commonNavigator;
    private TextView tvPurchaseOrderDetailState;
    private TextView tvHeadExpressInfo;
    private RelativeLayout rlHeadAddressInfo;
    private TextView tvHeadAddressName;
    private TextView tvHeadAddressPhone;
    private TextView tvHeadAddressAddress;
    private TextView tvHeadInvoiceInfo;
    private LinearLayout llHeadOrderMsg;
    private TextView tvHeadOrderMsg;
    private TextView tvOrderDetailStoreName;
    private int index = 1;//标记几次发货
    private MagicAdapter magicAdapter;
    private List<DeliverBean> deliverList;
    private int orderState;//0已作废  10待付款  20已经付款（待发货）  30已发货  其它代表已经完成
    private List<OrderGoodBean> goodsList;
    private String expressName;
    private String expressCode;
    private String stateDesc;
    private View footView;

    private GiftLinearLayout llOrderDetailBottomGift;
    private LinearLayout llOrderDetailBottomGiftInfo;
    private String orderCreateTime;
    private String orderSn;
    private String realPayAmount;
    private String orderAmount;
    private String shippingFee;
    private String paymentTime;
    private String shippingTime;
    private String promotionInfo;
    private TextView tvOrderDetailBottomTotal;
    private TextView tvExpressMoney;
    private TextView tvStoreFavourableMoney;
    private TextView tvExtraMoney;
    private TextView tvPurchaseOrderDetailInfo, tvOrderDetailRemark;
    private LinearLayout llStoreFavourableMoney;
    private DeliverBean deliverBean;
    private List<DeliverGoodsList> deliverGoodList;
    private DeliverGoodsList deliverGoodBean;
    private OrderGoodBean purchaseGoodBean;
    private String deliveryTime;
    private String deliverShipingTime;
    private OrderInfoBean orderInfo;
    private String finnshedTime;
    private List<OrderGoodBean> giftList;
    private OrderCancelReasonDialog cancelDialog;
    private int flag;
    private InputGoodNumDialog inputGoodNumDialog;
    private float changeData;
    private InputGoodPriceDialog inputGoodPriceDialog;
    private String storeShipping;
    private TextView tvOrderDetailGoodPriceRemark;

    @Override
    protected void initPresenter() {
        presenter = new OrderDetailPresenter(this);
    }

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
    protected void initData() {
        initIntent();
        tvActTitle.setText("订单详情");
        ivActRightSetting.setVisibility(View.GONE);
        initHeadView();
        initFootView();
        initAdapter();
        useIndexList.addAll(indexList);
        initMagic();
        sendNet();
    }


    private void initFootView() {
        footView = LayoutInflater.from(context).inflate(R.layout.foot_purchase_order_detail, null);
        llOrderDetailBottomGiftInfo = footView.findViewById(R.id.llOrderDetailBottomGiftInfo);
        llOrderDetailBottomGift = footView.findViewById(R.id.llOrderDetailBottomGift);
        tvOrderDetailBottomTotal = footView.findViewById(R.id.tvOrderDetailBottomTotal);
        tvExpressMoney = footView.findViewById(R.id.tvExpressMoney);
        tvStoreFavourableMoney = footView.findViewById(R.id.tvStoreFavourableMoney);
        tvExtraMoney = footView.findViewById(R.id.tvExtraMoney);
        tvOrderDetailGoodPriceRemark = footView.findViewById(R.id.tvOrderDetailGoodPriceRemark);

        llStoreFavourableMoney = footView.findViewById(R.id.llStoreFavourableMoney);
        tvPurchaseOrderDetailInfo = footView.findViewById(R.id.tvPurchaseOrderDetailInfo);
        tvOrderDetailRemark = footView.findViewById(R.id.tvOrderDetailRemark);
    }

    private void initMagic() {
        if (magicAdapter == null) {
            commonNavigator = new CommonNavigator(this);
            commonNavigator.setAdjustMode(true);
            magicAdapter = new MagicAdapter(useIndexList);
            commonNavigator.setAdapter(magicAdapter);
            magicOrderDetail.setNavigator(commonNavigator);
            magicAdapter.setOnPageChangeListener(this);
            LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
            titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            titleContainer.setDividerPadding(SizeUtils.dp2px(6));
            titleContainer.setDividerDrawable(UIUtil.getDrawable(R.drawable.magic_divider));
        } else {
   /*         magicAdapter.notifyData(useIndexList);
            magicOrderDetail.onPageSelected(0);
            commonNavigator.notifyDataSetChanged();*/
        }
    }

    @Override
    public void onPageChangeListener(int index) {
        this.index = index + 1;
        LogUtil.logI("点击：" + index + ",当前选择：" + this.index);
        magicOrderDetail.onPageSelected(index);
        commonNavigator.notifyDataSetChanged();
        initGoodList();
    }

    private void initHeadView() {
        headView = LayoutInflater.from(context).inflate(R.layout.head_purchase_order_detail, null);
        tvPurchaseOrderDetailState = headView.findViewById(R.id.tvPurchaseOrderDetailState);
        tvHeadExpressInfo = headView.findViewById(R.id.tvHeadExpressInfo);
        rlHeadAddressInfo = headView.findViewById(R.id.rlHeadAddressInfo);
        tvHeadAddressName = headView.findViewById(R.id.tvHeadAddressName);
        tvHeadAddressPhone = headView.findViewById(R.id.tvHeadAddressPhone);
        tvHeadAddressAddress = headView.findViewById(R.id.tvHeadAddressAddress);
        tvHeadInvoiceInfo = headView.findViewById(R.id.tvHeadInvoiceInfo);
        llHeadOrderMsg = headView.findViewById(R.id.llHeadOrderMsg);
        tvHeadOrderMsg = headView.findViewById(R.id.tvHeadOrderMsg);
        tvOrderDetailStoreName = headView.findViewById(R.id.tvOrderDetailStoreName);
    }

    private void initAdapter() {
        adapter = new OrderDetailAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initIntent() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra(CommonUtil.ORDER_ID);
    }

    private void sendNet() {
        LogUtil.logI(key + ":::参数:::" + orderId);
        presenter.sendNetGetOrderDetail(key, orderId);
    }

    @Override
    public void getOrderDetailSuccess(String result) {
        LogUtil.logI("订单详情：" + result);
        OrderDetailBean orderDetailBean = JSON.parseObject(result, OrderDetailBean.class);
        orderInfo = orderDetailBean.datas.order_info;
        if (orderInfo == null) return;
        LogUtil.logI("这里可以吗？");
        goodsList = orderInfo.goods_list;

        orderState = orderInfo.order_state;
        stateDesc = orderInfo.state_desc;
        deliverList = orderInfo.deliver;
        String priceRemark = orderInfo.price_remark;
        String payRemark = orderInfo.pay_remark;

        tvPurchaseOrderDetailState.setText("订单状态：" + stateDesc);
        if (priceRemark == null || StringUtils.isEmpty(priceRemark.trim())) {
            tvOrderDetailGoodPriceRemark.setVisibility(View.GONE);
            tvOrderDetailGoodPriceRemark.setText("修改价格原因：无");
        } else {
            tvOrderDetailGoodPriceRemark.setVisibility(View.VISIBLE);
            tvOrderDetailGoodPriceRemark.setText("修改价格原因：" + priceRemark);
        }

        if (payRemark == null || StringUtils.isEmpty(payRemark.trim())) {
            tvOrderDetailRemark.setVisibility(View.GONE);
        } else {
            tvOrderDetailRemark.setVisibility(View.VISIBLE);
            tvOrderDetailRemark.setText("确认收款备注：" + payRemark);
        }

        if (orderState == 10) {
            tvHeadExpressInfo.setVisibility(View.GONE);
        } else {
            tvHeadExpressInfo.setVisibility(View.VISIBLE);
        }

        //留言
        String orderMessage = orderInfo.order_message;
        tvHeadOrderMsg.setText(orderMessage);
        if (StringUtils.isEmpty(orderMessage)) {
            llHeadOrderMsg.setVisibility(View.GONE);
        } else {
            llHeadOrderMsg.setVisibility(View.VISIBLE);
        }

        AddressBean reciverInfo = orderInfo.reciver_info;
        if (reciverInfo != null) {
            //地址信息
            String address = reciverInfo.address;
            //收货电话
            String orderPhone = reciverInfo.phone;

            tvHeadAddressName.setText(reciverInfo.reciver_name);
            tvHeadAddressPhone.setText(orderPhone);
            tvHeadAddressAddress.setText(address);
        }
        tvOrderDetailStoreName.setText("购买人：" + orderInfo.buyer_name);
        //发票信息
        InvoiceInfoBean invoice = orderInfo.invoice_info;
        if (StringUtils.isEmpty(invoice.info)) {
            tvHeadInvoiceInfo.setVisibility(View.GONE);
        } else {
            tvHeadInvoiceInfo.setVisibility(View.VISIBLE);
            tvHeadInvoiceInfo.setText("发票信息\n发票抬头:" + invoice.title + "\n发票内容:" + invoice.info);
        }

        //订单货号
        orderSn = orderInfo.order_sn;
        //创建时间
        orderCreateTime = orderInfo.add_time;
        //支付时间
        paymentTime = orderInfo.payment_time;
        //发货时间
        shippingTime = orderInfo.shipping_time;
        //收货时间
        finnshedTime = orderInfo.finnshed_time;
        //店铺优惠
        promotionInfo = orderInfo.promotion_info;

        //原来总价
        orderAmount = orderInfo.goods_amount;
        //实际付款
        realPayAmount = orderInfo.order_amount;
        //运费
        shippingFee = orderInfo.shipping_fee;
        storeShipping = orderInfo.store_shipping;
        tvOrderDetailBottomTotal.setText("¥ " + orderAmount);
        if (TextUtils.equals("运费到付", shippingFee))
            tvExpressMoney.setText(shippingFee);
        else
            tvExpressMoney.setText("¥ " + shippingFee);
        tvExtraMoney.setText("¥ " + realPayAmount);

        if (!StringUtils.isEmpty(promotionInfo)) {
            llStoreFavourableMoney.setVisibility(View.VISIBLE);
            tvStoreFavourableMoney.setText(promotionInfo);
        } else {
            llStoreFavourableMoney.setVisibility(View.GONE);
        }

        if (deliverList != null) index = deliverList.size();

        initTopMagic();
        initGoodList();
        giftList = orderInfo.zengpin_list;
        if (giftList == null || giftList.size() == 0) {
            llOrderDetailBottomGiftInfo.setVisibility(View.GONE);
        } else {
            llOrderDetailBottomGiftInfo.setVisibility(View.VISIBLE);
            llOrderDetailBottomGift.setDataList(giftList);
        }
        adapter.removeAllHeaderView();
        adapter.removeAllFooterView();
        adapter.addHeaderView(headView);
        adapter.addFooterView(footView);

        magicOrderDetail.onPageSelected(index - 1);
        commonNavigator.notifyDataSetChanged();
    }

    /**
     * 判断顶部布局是否显示
     */
    private void initTopMagic() {

        if (deliverList == null || deliverList.size() < 2) {
            if (!(orderState == 20 && (deliverList == null || deliverList.size() == 1))) {
                magicOrderDetail.setVisibility(View.GONE);
                return;
            }
        }
        if (deliverList != null) {
            magicOrderDetail.setVisibility(View.VISIBLE);
            if (useIndexList == null) {
                useIndexList = new ArrayList<>();
            }
            useIndexList.clear();
            for (int i = 0; i < deliverList.size(); i++) {
                useIndexList.add(indexList.get(i));
            }
            initMagic();
        }
    }

    private void initGoodList() {

        if (orderState == 0) {//已作废
            adapter.setNewData(goodsList);
            tvPurchaseOrderDetailInfo.setText("订货单号：" + orderSn + "\n创建时间：" + orderCreateTime);
            // llOrderDetailCancelState.setVisibility(View.VISIBLE);
        } else if (orderState == 10) {
            adapter.setNewData(goodsList);
            tvPurchaseOrderDetailInfo.setText("订货单号：" + orderSn + "\n创建时间：" + orderCreateTime);

            if (StringUtils.equals("1", storeShipping)) {
                tvExpressMoney.setText("运费：运费到付");
                tvAdapterChangeOrderExpenses.setVisibility(View.GONE);
            } else {
                if (StringUtils.isEmpty(shippingFee)) {

                    tvExpressMoney.setText("运费：免运费");
                    tvAdapterChangeOrderExpenses.setVisibility(View.GONE);
                } else if (Double.valueOf(shippingFee) == 0) {

                    tvExpressMoney.setText("运费：免运费");
                    tvAdapterChangeOrderExpenses.setVisibility(View.GONE);
                } else {
                    tvExpressMoney.setText("运费：¥" + shippingFee);
                    tvAdapterChangeOrderExpenses.setVisibility(View.VISIBLE);
                }
            }

            llAdapterWaitPay.setVisibility(View.VISIBLE);
        } else if (orderState == 20) {//待发货  两种情况  一次也没有发货  发货超过1次，但是还没有发完
            llAdapterWaitSend.setVisibility(View.VISIBLE);
            tvOrderLookExpress.setVisibility(View.VISIBLE);
            if (deliverList == null || deliverList.size() == 0) {//一次也没有发货
                adapter.setNewData(goodsList);
                tvPurchaseOrderDetailInfo.setText("订货单号：" + orderSn + "\n创建时间：" + orderCreateTime + "\n付款时间：" + paymentTime);

            } else {// 发货超过1次，但是还没有发完
                initDeliverGoodList();
            }

        } else if (orderState == 30) {//已发货  两种情况  一次全部发完  发货超过1次，全部发完
            if (deliverList == null || deliverList.size() == 0 || deliverList.size() == 1) {//一次发完
                index = 0;
                adapter.setNewData(goodsList);
                tvPurchaseOrderDetailInfo.setText("订货单号：" +
                        orderSn + "\n创建时间：" + orderCreateTime +
                        "\n付款时间：" + paymentTime + "\n发货时间：" +
                        shippingTime);
                // llOrderDetailWaitGetGood.setVisibility(View.VISIBLE);
                if (deliverList != null && deliverList.size() == 1) {
                    deliverBean = deliverList.get(0);
                    if (deliverBean == null) return;
                    deliverGoodList = deliverBean.goods_list;
                    stateDesc = orderInfo.state_desc;
                }

            } else {// 发货超过1次发完 使用index展示第几次发货
                initDeliverGoodList();
            }
        } else {

            if (deliverList == null || deliverList.size() == 0 || deliverList.size() == 1) {//一次收完
                adapter.setNewData(goodsList);
                tvPurchaseOrderDetailInfo.setText("订货单号：" + orderSn + "\n创建时间：" + orderCreateTime + "\n付款时间："
                        + paymentTime + "\n发货时间：" + shippingTime + "\n收货时间：" + finnshedTime);
                //llOrderDetailFinish.setVisibility(View.VISIBLE);
            } else {// 发货超过1次，但是还没有发完 使用index展示第几次发货
                initDeliverGoodList();
            }
        }
        tvPurchaseOrderDetailState.setText("订单状态：" + stateDesc);
        if (StringUtils.isEmpty(expressName)) {
            tvHeadExpressInfo.setText("无物流信息");
        } else {
            tvHeadExpressInfo.setText("物流：" + expressName + "\n" + "物流单号：" + expressCode);
        }
    }


    private void initDeliverGoodList() {
        if (deliverList == null) return;
        if (goodsList == null || goodsList.size() == 0) return;

        deliverBean = deliverList.get(index - 1);
        deliverGoodList = deliverBean.goods_list;
        //快递单号
        expressCode = deliverBean.shipping_code;
        stateDesc = deliverBean.delivery_state;
        //快递名
        expressName = deliverBean.e_name;

        //第index次发货时间
        deliverShipingTime = deliverBean.shipping_time;

        //第index次收货时间
        deliveryTime = deliverBean.delivery_time;

        for (int i = 0; i < deliverGoodList.size(); i++) {
            deliverGoodBean = deliverGoodList.get(i);
            for (int j = 0; j < goodsList.size(); j++) {
                purchaseGoodBean = goodsList.get(j);
                if (TextUtils.equals(deliverGoodBean.rec_id, purchaseGoodBean.rec_id)) {
                    purchaseGoodBean.deliver_num = deliverGoodBean.goods_num;
                }
            }
        }

        adapter.setNewDataIndex(useIndexList.get(index - 1), goodsList);
        LogUtil.logI("能来吗？");
        if (StringUtils.isEmpty(deliveryTime) || TextUtils.equals("0", deliveryTime)) {
            tvPurchaseOrderDetailInfo.setText("订货单号：" + orderSn + "\n创建时间：" + orderCreateTime
                    + "\n付款时间：" + paymentTime + "\n第" + useIndexList.get(index - 1) + "发货时间：" + deliverShipingTime);
        } else {
            tvPurchaseOrderDetailInfo.setText("订货单号：" + orderSn + "\n创建时间："
                    + orderCreateTime + "\n付款时间：" + paymentTime
                    + "\n第" + useIndexList.get(index - 1) + "发货时间：" + deliverShipingTime
                    + "\n第" + useIndexList.get(index - 1) + "收货时间：" + deliveryTime);
        }

        //  llOrderDetailWaitGetGood.setVisibility(View.VISIBLE);


    }

    @Override
    protected void initListener() {
        tvOrderLookExpress.setOnClickListener(this);
        tvAdapterChangeOrderSendGood.setOnClickListener(this);
        tvAdapterCancelOrder.setOnClickListener(this);
        tvAdapterChangeOrderExpenses.setOnClickListener(this);
        tvAdapterChangeOrderAmount.setOnClickListener(this);
        tvAdapterConfirmMoney.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOrderLookExpress://查看物流
                if (orderInfo == null) return;
                LookExpressActivity.launch(context, orderInfo.order_id, orderInfo.shop_id, orderInfo.num, goodsList.get(0).goods_image);
                break;
            case R.id.tvAdapterChangeOrderSendGood://设置发货
                if (orderInfo != null) {
                    OrderSendGoodIndexActivity.launch(context, orderId);
                    finish();
                }
                break;
            case R.id.tvAdapterCancelOrder://取消订单
                cancelOrderDialog();
                break;
            case R.id.tvAdapterChangeOrderExpenses://修改运费
                try {
                    flag = 0;
                    changPriceAndSheep();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvAdapterChangeOrderAmount://修改价格

                try {
                    flag = 1;
                    // changPriceAndSheep();
                    changeGoodPriceDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvAdapterConfirmMoney://确认收款
                ConfirmMoneyActivity.launchResult(this, orderId);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConfirmMoneyActivity.RESULT_CODE) {
            if (requestCode == ConfirmMoneyActivity.REQUEST_CODE) {//确认收款完成
                sendNet();
            }
        }
    }

    private void changeGoodPriceDialog() {
        if (inputGoodPriceDialog == null) {
            inputGoodPriceDialog = new InputGoodPriceDialog(context);
            inputGoodPriceDialog.setOnConfirmListener(this);
        }
        inputGoodPriceDialog.setNumber(new BigDecimal(realPayAmount).floatValue());
        inputGoodPriceDialog.show();
    }

    @Override
    public void confirmGoodPrice(float number, String remark) {
        this.changeData = number;
        presenter.sendNetChangeOrderAmount(key, orderId, number + "", remark);
    }

    /**
     * 修改运费或者价格
     */
    private void changPriceAndSheep() throws Exception {
        if (inputGoodNumDialog == null) {
            inputGoodNumDialog = new InputGoodNumDialog(context);
            inputGoodNumDialog.setOnConfirmListener(this);
        }
        if (flag == 0) {
            if (!TextUtils.equals("运费到付", shippingFee))
                inputGoodNumDialog.setNumber(new BigDecimal(shippingFee).floatValue());
        } else {
            inputGoodNumDialog.setNumber(new BigDecimal(realPayAmount).floatValue());
        }
        inputGoodNumDialog.show();
    }

    @Override
    public void confirmNumber(float number) {
        this.changeData = number;
        LogUtil.logI(key + ":::" + orderId + "::::" + number);
        if (flag == 0) {
            presenter.sendNetChangeSheep(key, orderId, number + "");
        } else {
            // presenter.sendNetChangeOrderAmount(key, orderId, number + "");
        }
    }

    /**
     * 取消订单
     */
    private void cancelOrderDialog() {
        if (cancelDialog == null) {
            cancelDialog = new OrderCancelReasonDialog(context);
            cancelDialog.setOnChooseCancelOrderReasonListener(this);
        }
        cancelDialog.show();
    }

    @Override
    public void reasonDetail(String reason) {
        presenter.sendNetCancelOrder(key, orderId, reason);
    }

    @Override
    public void cancelOrderSuccess(String result) {
        ToastUtil.showSuccess("订单取消成功！");
        setResult(ORDER_DETAIL_RESULT_CODE);
        finish();
    }

    @Override
    public void changeOrderSuccess(String result) {
        ToastUtil.showSuccess("价格修改成功！");
        realPayAmount = changeData + "";
        tvExtraMoney.setText("¥ " + realPayAmount);
        setResult(ORDER_DETAIL_RESULT_CODE);
    }

    @Override
    public void changeSheepSuccess(String result) {
        shippingFee = result + "";
        if (!StringUtils.isEmpty(realPayAmount)) {
            tvExtraMoney.setText("¥ " + NumberUtil.add(shippingFee, realPayAmount, 2, BigDecimal.ROUND_HALF_EVEN));
        }
        setResult(ORDER_DETAIL_RESULT_CODE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cancelDialog != null)
            cancelDialog.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    public static void launch(Fragment fragment, String orderId) {
        Intent intent = new Intent(fragment.getActivity(), OrderDetailActivity.class);
        intent.putExtra(CommonUtil.ORDER_ID, orderId);
        fragment.startActivityForResult(intent, OrderFragment.REQUEST_CODE);
    }
}
