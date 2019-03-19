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

public class InputGoodNumDialog implements View.OnClickListener {
    @BindView(R.id.etGoodsCarNumDialog)
    EditText etGoodsCarNumDialog;
    @BindView(R.id.tvGoodsCarNumDialogCancel)
    TextView tvGoodsCarNumDialogCancel;
    @BindView(R.id.tvGoodsCarNumDialogConfirm)
    TextView tvGoodsCarNumDialogConfirm;
    private Context mContext;
    private AlertDialog alertDialog;
    private Unbinder unBinder;
    private OnConfirmNumberListener listener;

    public InputGoodNumDialog(Context context) {
        this.mContext = context;
        initDialog();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CommonDialog);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_input_dialog, null);
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
        tvGoodsCarNumDialogCancel.setOnClickListener(this);
        tvGoodsCarNumDialogConfirm.setOnClickListener(this);
    }

    public void setNumber(float number) {
        etGoodsCarNumDialog.setText(NumberUtil.replaceZero(number));
        etGoodsCarNumDialog.setSelection(NumberUtil.replaceZero(number).length());
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

    public void setOnConfirmListener(OnConfirmNumberListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGoodsCarNumDialogCancel://取消
                dismiss();
                break;
            case R.id.tvGoodsCarNumDialogConfirm://确认
                dismiss();
                String number = etGoodsCarNumDialog.getText().toString().trim();
                if (StringUtils.isEmpty(number)) {
                    ToastUtil.showError("请输入库存！");
                    return;
                }
                if (listener != null) {
                    BigDecimal bigDecimal = new BigDecimal(number);
                    listener.confirmNumber(bigDecimal.floatValue());
                }
                break;
        }
    }


    public interface OnConfirmNumberListener {
        void confirmNumber(float number);
    }
}
