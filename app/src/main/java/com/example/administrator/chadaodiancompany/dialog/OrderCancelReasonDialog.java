package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderCancelReasonDialog implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    @BindView(R.id.rbCancelOrderReasonOne)
    RadioButton rbCancelOrderReasonOne;
    @BindView(R.id.rbCancelOrderReasonTwo)
    RadioButton rbCancelOrderReasonTwo;
    @BindView(R.id.rbCancelOrderReasonThree)
    RadioButton rbCancelOrderReasonThree;
    @BindView(R.id.rbCancelOrderReasonFour)
    RadioButton rbCancelOrderReasonFour;
    @BindView(R.id.rgCancelOrder)
    RadioGroup rgCancelOrder;
    @BindView(R.id.tvCancelOrderCancel)
    TextView tvCancelOrderCancel;
    @BindView(R.id.tvCancelOrderConfirm)
    TextView tvCancelOrderConfirm;
    private Context mContext;
    private AlertDialog alertDialog;
    private Unbinder unbinder;
    private OnChooseCancelOrderReasonListener listener;
    private String state;

    public OrderCancelReasonDialog(Context context) {
        mContext = context;
        initDialog();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CommonDialog);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_order_cancel_reason, null);
        unbinder = ButterKnife.bind(this, view);
        alertDialog.setView(view);
        initListener();
    }

    private void initListener() {
        rgCancelOrder.setOnCheckedChangeListener(this);
        tvCancelOrderCancel.setOnClickListener(this);
        tvCancelOrderConfirm.setOnClickListener(this);
    }

    public void show() {
        if (alertDialog != null && !alertDialog.isShowing()) alertDialog.show();
    }

    private void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
    }

    public void onDestroy() {
        if (unbinder != null) unbinder.unbind();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (listener == null) return;
        switch (checkedId) {
            case R.id.rbCancelOrderReasonOne:
                state = rbCancelOrderReasonOne.getText().toString().trim();
                break;
            case R.id.rbCancelOrderReasonTwo:
                state = rbCancelOrderReasonTwo.getText().toString().trim();
                break;
            case R.id.rbCancelOrderReasonThree:
                state = rbCancelOrderReasonThree.getText().toString().trim();
                break;
            case R.id.rbCancelOrderReasonFour:
                state = rbCancelOrderReasonFour.getText().toString().trim();
                break;
        }
    }

    public void setOnChooseCancelOrderReasonListener(OnChooseCancelOrderReasonListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancelOrderCancel:
                dismiss();
                break;
            case R.id.tvCancelOrderConfirm:
                if (listener == null) return;
                dismiss();
                listener.reasonDetail(state);
                break;
        }
    }


    public interface OnChooseCancelOrderReasonListener {
        void reasonDetail(String reason);
    }
}
