package com.example.administrator.chadaodiancompany.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.util.NumberUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InputGoodPriceDialog implements View.OnClickListener {

    @BindView(R.id.etGoodsPriceDialog)
    EditText etGoodsPriceDialog;
    @BindView(R.id.etGoodsPriceRemarkDialog)
    EditText etGoodsPriceRemarkDialog;
    @BindView(R.id.tvGoodsPriceCancel)
    TextView tvGoodsPriceCancel;
    @BindView(R.id.tvGoodsPriceConfirm)
    TextView tvGoodsPriceConfirm;
    private Context mContext;
    private AlertDialog alertDialog;
    private Unbinder unBinder;
    private OnConfirmGoodPriceListener listener;

    public InputGoodPriceDialog(Context context) {
        this.mContext = context;
        initDialog();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CommonDialog);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_input_good_price_dialog, null);
        unBinder = ButterKnife.bind(this, view);
        alertDialog.setView(view);
        initListener();
    }

    public static void setDialogWindowAttr(Dialog dlg) {
        Window window = dlg.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (ScreenUtils.getScreenWidth() / 1.5 + 0.5);
        window.setAttributes(lp);
    }

    private void initListener() {
        tvGoodsPriceCancel.setOnClickListener(this);
        tvGoodsPriceConfirm.setOnClickListener(this);
    }

    public void setNumber(float number) {
        etGoodsPriceDialog.setText(NumberUtil.replaceZero(number));
        etGoodsPriceDialog.setSelection(NumberUtil.replaceZero(number).length());
    }

    public void show() {
        if (alertDialog != null && !alertDialog.isShowing()) {
            alertDialog.show();
            setDialogWindowAttr(alertDialog);
        }
    }

    private void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public void destroy() {
        if (unBinder != null)
            unBinder.unbind();

        mContext = null;
    }

    public void setOnConfirmListener(OnConfirmGoodPriceListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGoodsPriceCancel://取消
                dismiss();
                break;
            case R.id.tvGoodsPriceConfirm://确认
                dismiss();
                String number = etGoodsPriceDialog.getText().toString().trim();
                String remark = etGoodsPriceRemarkDialog.getText().toString().trim();
                if (StringUtils.isEmpty(number)) {
                    ToastUtil.showError("请输入库存！");
                    return;
                }
                if (StringUtils.isEmpty(remark)) {
                    ToastUtil.showError("请输入修改价格备注！");
                    return;
                }
                if (listener != null) {
                    BigDecimal bigDecimal = new BigDecimal(number);
                    listener.confirmGoodPrice(bigDecimal.floatValue(), remark);
                }
                break;
        }
    }


    public interface OnConfirmGoodPriceListener {
        void confirmGoodPrice(float number, String remark);
    }
}
