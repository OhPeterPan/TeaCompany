package com.example.administrator.chadaodiancompany.ui.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.chadaodiancompany.NetDialog;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.PayMethodBean;
import com.example.administrator.chadaodiancompany.bean.PayMethodBean.PayMethodData.PayMethodListBean;
import com.example.administrator.chadaodiancompany.popupWindow.ChooseMoneyMethodPopupWindow;
import com.example.administrator.chadaodiancompany.presenter.ConfirmMoneyPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.SpUtil;
import com.example.administrator.chadaodiancompany.util.TimeUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.view.TeaPickView;
import com.example.administrator.chadaodiancompany.viewImpl.IConfirmMoneyView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmMoneyActivity extends AppCompatActivity implements View.OnClickListener, TeaPickView.ISelectPickViewTimeListener, IConfirmMoneyView, ChooseMoneyMethodPopupWindow.IOnChoosePayMethodListener {

    public static final int REQUEST_CODE = 0x001;
    public static final int RESULT_CODE = 0x002;
    @BindView(R.id.tvDialogChooseCalendar)
    TextView tvDialogChooseCalendar;
    @BindView(R.id.tvDialogChoosePayMethod)
    TextView tvDialogChoosePayMethod;
    @BindView(R.id.etDialogPayCodeOrRemark)
    EditText etDialogPayCodeOrRemark;
    @BindView(R.id.tvCancelGetMoney)
    TextView tvCancelGetMoney;
    @BindView(R.id.tvConfirmGetMoney)
    TextView tvConfirmGetMoney;
    private TeaPickView teaPickView;
    private ConfirmMoneyPresenter presenter;
    private NetDialog netDialog;
    private List<PayMethodListBean> list;
    private ChooseMoneyMethodPopupWindow popupWindow;
    private String payCode;
    private String time = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH_DAY);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_money);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }

    @Override
    public void showLoading() {
        if (NetworkUtils.isConnected()) {
            if (netDialog != null && !netDialog.isShowing()) {
                netDialog.show();
            }
        } else {
            ToastUtils.showShort("网络连接失败，请检查网络是否连接！");
        }
    }

    @Override
    public void hideLoading() {
        if (netDialog != null && netDialog.isShowing()) {
            netDialog.dismiss();
        }
    }

    @Override
    public void showError(Throwable throwable) {
        LogUtils.eTag("wak", throwable);
        LogUtil.logI("错误了？");
    }

    private void initData() {
        netDialog = new NetDialog(this);
        presenter = new ConfirmMoneyPresenter(this);
        presenter.sendNet(getKey());
    }

    @Override
    public void getPayMethodResult(String result) {
        PayMethodBean payMethodBean = JSON.parseObject(result, PayMethodBean.class);
        list = payMethodBean.datas.list;
    }

    private String getKey() {
        return SpUtil.getString(SpUtil.KEY, "");
    }

    private void initView() {
        tvDialogChooseCalendar.setText(time);
    }

    private void initListener() {
        tvDialogChooseCalendar.setOnClickListener(this);
        tvDialogChoosePayMethod.setOnClickListener(this);
        tvCancelGetMoney.setOnClickListener(this);
        tvConfirmGetMoney.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancelGetMoney://取消
                finish();
                break;
            case R.id.tvConfirmGetMoney://确定
                confirmGetPay();

                break;
            case R.id.tvDialogChooseCalendar://选择日期
                showTimeDialog();
                break;
            case R.id.tvDialogChoosePayMethod://选择支付方式
                showPayMethodPopupWindow();
                break;
        }
    }

    private void confirmGetPay() {
        String remark = etDialogPayCodeOrRemark.getText().toString().trim();
        if (StringUtils.isEmpty(remark)) {
            ToastUtil.showError("请输入备注！");
            return;
        }
        presenter.sendNetPay(getKey(), time, payCode, remark);
    }

    @Override
    public void confirmPayResult(String result) {
        ToastUtil.showSuccess("确认收款成功！");
        setResult(RESULT_CODE);
        finish();
    }

    private void showPayMethodPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new ChooseMoneyMethodPopupWindow(this, list);
            popupWindow.setOnGradeListener(this);
        }
        popupWindow.show(tvDialogChoosePayMethod);
    }

    @Override
    public void getPayMethodListener(PayMethodListBean bean) {
        this.payCode = bean.payment_code;
        tvDialogChoosePayMethod.setText(bean.payment_name);
    }

    private void showTimeDialog() {
        if (teaPickView == null)
            teaPickView = new TeaPickView(this, Calendar.getInstance().get(Calendar.YEAR)).builder()
                    .setTimeType(new boolean[]{true, true, true, false, false, false})
                    .setPickViewStyle()
                    .build()
                    .setSelectPickTimeListener(this);

        teaPickView.show(tvDialogChooseCalendar);
    }

    @Override
    public void selectTimeListener(Date date, View v) {
        time = TimeUtil.date2String(date, TimeUtil.YEAR_MONTH_DAY);
        tvDialogChooseCalendar.setText(TimeUtil.date2String(date, TimeUtil.YEAR_MONTH_DAY));
    }

    public static void launch(Context context, String orderId) {
        Intent intent = new Intent(context, ConfirmMoneyActivity.class);
        intent.putExtra(CommonUtil.ORDER_ID, orderId);
        context.startActivity(intent);
    }

    public static void launchResult(Context context, String orderId) {
        Intent intent = new Intent(context, ConfirmMoneyActivity.class);
        intent.putExtra(CommonUtil.ORDER_ID, orderId);
        ((AppCompatActivity) context).startActivityForResult(intent, REQUEST_CODE);
    }

    public static void launchResultFrag(Fragment frag, String orderId) {
        Intent intent = new Intent(frag.getContext(), ConfirmMoneyActivity.class);
        intent.putExtra(CommonUtil.ORDER_ID, orderId);
        frag.startActivityForResult(intent, REQUEST_CODE);
    }


}
