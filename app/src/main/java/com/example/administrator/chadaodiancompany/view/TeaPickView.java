package com.example.administrator.chadaodiancompany.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.util.UIUtil;

import java.util.Calendar;
import java.util.Date;

public class TeaPickView implements TimePickerView.OnTimeSelectListener {
    private Context mContext;
    private Calendar startInstance;
    private Calendar endInstance;
    private TimePickerView.Builder builder;
    private TimePickerView build;
    private ISelectPickViewTimeListener listener;

    public TeaPickView(Context context, int startTime) {
        this.mContext = context;
        startInstance = Calendar.getInstance();
        endInstance = Calendar.getInstance();
        startInstance.set(Calendar.YEAR, startTime - 10);
        endInstance.set(Calendar.YEAR, startTime + 10);
    }

    public TeaPickView builder() {
        builder = new TimePickerView.Builder(mContext, this);
        builder.setRangDate(startInstance, endInstance);
        return this;
    }

    public TeaPickView setTimeType(boolean[] boo) {
        if (builder == null) throw new NullPointerException("builder不能为null");
        builder.setType(boo);
        return this;
    }

    public TeaPickView setPickViewStyle() {
        builder.setBgColor(Color.WHITE)
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setDate(Calendar.getInstance())// 默认是系统时间*/
                .setSubmitColor(UIUtil.getColor(R.color.skyBlue))//确定按钮文字颜色
                .setCancelColor(UIUtil.getColor(R.color.skyBlue))//取消按钮文字颜色;
                .isCenterLabel(true);//是否只显示中间选中项的label文字，false则每项item全部都带有label。

        return this;
    }

    public TeaPickView build() {
        if (builder == null) throw new IllegalStateException("builder不能为null");
        build = builder.build();
        return this;
    }

    public TeaPickView setSelectPickTimeListener(ISelectPickViewTimeListener listener) {
        this.listener = listener;
        return this;
    }

    public void show(View v) {
        if (build == null) throw new IllegalStateException("需要先构造对象！！");
        KeyboardUtils.hideSoftInput(v);
        build.show(v);
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        listener.selectTimeListener(date, v);
    }

    public interface ISelectPickViewTimeListener {
        void selectTimeListener(Date date, View v);
    }
}
