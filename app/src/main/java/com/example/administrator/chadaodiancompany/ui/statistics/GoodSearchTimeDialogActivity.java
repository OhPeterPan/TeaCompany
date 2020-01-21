package com.example.administrator.chadaodiancompany.ui.statistics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.TimeUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.util.UIUtil;
import com.example.administrator.chadaodiancompany.view.TeaPickView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodSearchTimeDialogActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,
        TeaPickView.ISelectPickViewTimeListener {

    @BindView(R.id.rbOneWeek)
    RadioButton rbOneWeek;
    @BindView(R.id.rbOneMonth)
    RadioButton rbOneMonth;
    @BindView(R.id.rbThreeMonth)
    RadioButton rbThreeMonth;
    @BindView(R.id.rgDefaultTime)
    RadioGroup rgDefaultTime;
    @BindView(R.id.dialogStartTime)
    TextView dialogStartTime;
    @BindView(R.id.dialogEndTime)
    TextView dialogEndTime;
    @BindView(R.id.dialogTimeQuery)
    TextView dialogTimeQuery;
    @BindView(R.id.dialogExport)
    TextView dialogExport;
    private String startTime;
    private String endTime;
    private int tag;
    private TeaPickView teaPickView;
    private int flag = 0;//0选择开始时间 1选择结束时间
    private int analy;//1就是商品分析来着

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_good_search_detail_time);
        ButterKnife.bind(this);
        initIntent();
        initView();
        initListener();
    }

    private void initListener() {
        rgDefaultTime.setOnCheckedChangeListener(this);
        dialogStartTime.setOnClickListener(this);
        dialogEndTime.setOnClickListener(this);
        dialogExport.setOnClickListener(this);
    }

    private void initView() {
        dialogStartTime.setText(startTime);
        dialogEndTime.setText(endTime);
        dialogTimeQuery.setOnClickListener(this);
        rbOneWeek.setChecked(tag == 0);
        rbOneMonth.setChecked(tag == 1);
        rbThreeMonth.setChecked(tag == 2);
        dialogExport.setVisibility(analy == 1 ? View.VISIBLE : View.GONE);
    }

    private void initIntent() {
        Intent intent = getIntent();
        startTime = intent.getStringExtra(CommonUtil.START_TIME);
        endTime = intent.getStringExtra(CommonUtil.END_TIME);
        tag = intent.getIntExtra(CommonUtil.TAG, 3);
        analy = intent.getIntExtra(CommonUtil.FLAG, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogStartTime://开始时间
                flag = 0;
                showPickWithDayTime();
                break;
            case R.id.dialogEndTime://结束时间
                flag = 1;
                showPickWithDayTime();
                break;
            case R.id.dialogTimeQuery://查询
                setCheckResult(3);
                break;
            case R.id.dialogExport://导出商品

                startTime = dialogStartTime.getText().toString().trim();
                endTime = dialogEndTime.getText().toString().trim();

                exportGood(3);
                break;
        }
    }

    private void exportGood(int tag) {

        Intent intent = new Intent();
        intent.putExtra(CommonUtil.START_TIME, startTime);
        intent.putExtra(CommonUtil.END_TIME, endTime);
        intent.putExtra(CommonUtil.TAG, tag);
        setResult(CommonUtil.RESULT_CODE_EXPORT, intent);
        finish();

    }

    private void showPickWithDayTime() {
        if (teaPickView == null)
            teaPickView = new TeaPickView(this, Calendar.getInstance().get(Calendar.YEAR)).builder()
                    .setTimeType(new boolean[]{true, true, true, false, false, false})
                    .setPickViewStyle()
                    .build()
                    .setSelectPickTimeListener(this);

        teaPickView.show(dialogTimeQuery);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void selectTimeListener(Date date, View v) {
        if (flag == 0) {
            startTime = TimeUtil.date2String(date, TimeUtil.YEAR_MONTH_DAY);
            if (TimeUtil.date2Millis(date) > TimeUtil.strineg2Millis(endTime, TimeUtil.YEAR_MONTH_DAY)) {
                ToastUtil.showError("结束时间应该大于开始时间！");
                return;
            }

            if (TimeUtil.compareDateYear(TimeUtil.String2Date(endTime, TimeUtil.YEAR_MONTH_DAY), date) > 1) {
                ToastUtil.showError("查询跨度不能超过一年！");
                return;
            }

            dialogStartTime.setText(startTime);
        } else {
            if (TimeUtil.date2Millis(date) < TimeUtil.strineg2Millis(startTime, TimeUtil.YEAR_MONTH_DAY)) {
                ToastUtil.showError("结束时间应该大于开始时间！");
                return;
            }
            if (TimeUtil.compareDateYear(date, TimeUtil.String2Date(startTime, TimeUtil.YEAR_MONTH_DAY)) > 1) {
                ToastUtil.showError("查询跨度不能超过一年！");
                return;
            }
            endTime = TimeUtil.date2String(date, TimeUtil.YEAR_MONTH_DAY);
            dialogEndTime.setText(endTime);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbOneWeek:
                setCheckResult(0);
                break;
            case R.id.rbOneMonth:
                setCheckResult(1);
                break;
            case R.id.rbThreeMonth:
                setCheckResult(2);
                break;
        }
    }

    private void setCheckResult(int tag) {
        Intent intent = new Intent();
        intent.putExtra(CommonUtil.START_TIME, startTime);
        intent.putExtra(CommonUtil.END_TIME, endTime);
        intent.putExtra(CommonUtil.TAG, tag);
        setResult(CommonUtil.RESULT_CODE_COMMON, intent);
        finish();
    }


    public static void launchResult(Context ctx, String startTime, String endTime, int tag) {
        Intent intent = new Intent(ctx, GoodSearchTimeDialogActivity.class);
        intent.putExtra(CommonUtil.START_TIME, startTime);
        intent.putExtra(CommonUtil.END_TIME, endTime);
        intent.putExtra(CommonUtil.TAG, tag);
        ((AppCompatActivity) ctx).startActivityForResult(intent, CommonUtil.REQUEST_CODE);
    }

    public static void launchResult(Context ctx, String startTime, String endTime, int tag, int flag) {
        Intent intent = new Intent(ctx, GoodSearchTimeDialogActivity.class);
        intent.putExtra(CommonUtil.START_TIME, startTime);
        intent.putExtra(CommonUtil.END_TIME, endTime);
        intent.putExtra(CommonUtil.TAG, tag);
        intent.putExtra(CommonUtil.FLAG, flag);
        ((AppCompatActivity) ctx).startActivityForResult(intent, CommonUtil.REQUEST_CODE);
    }
}
