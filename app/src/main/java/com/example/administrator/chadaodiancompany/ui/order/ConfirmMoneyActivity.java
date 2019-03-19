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

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.popupWindow.ChooseMoneyMethodPopupWindow;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.TimeUtil;
import com.example.administrator.chadaodiancompany.view.TeaPickView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmMoneyActivity extends AppCompatActivity implements View.OnClickListener, TeaPickView.ISelectPickViewTimeListener {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_money);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        tvDialogChooseCalendar.setText(TimeUtil.getNowTime(TimeUtil.YEAR_MONTH_DAY));
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
                setResult(RESULT_CODE);
                finish();
                break;
            case R.id.tvDialogChooseCalendar://选择日期
                showTimeDialog();
                break;
            case R.id.tvDialogChoosePayMethod://选择支付方式
                showPayMethodPopupWindow();
                break;
        }
    }

    private void showPayMethodPopupWindow() {
        ChooseMoneyMethodPopupWindow popupWindow = new ChooseMoneyMethodPopupWindow(this);
        popupWindow.show(tvDialogChoosePayMethod);
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
