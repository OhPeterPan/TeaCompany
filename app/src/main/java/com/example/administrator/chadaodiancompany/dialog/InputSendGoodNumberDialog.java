package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.OrderGoodBean;
import com.example.administrator.chadaodiancompany.image.ImageLoader;
import com.example.administrator.chadaodiancompany.util.ToastUtil;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InputSendGoodNumberDialog implements View.OnClickListener {
    @BindView(R.id.ivDialogOrderSendGoodsPic)
    ImageView ivDialogOrderSendGoodsPic;
    @BindView(R.id.tvDialogOrderSendGoodName)
    TextView tvDialogOrderSendGoodName;
    @BindView(R.id.tvDialogOrderSendGoodOweNumber)
    TextView tvDialogOrderSendGoodOweNumber;
    @BindView(R.id.etDialogOrderSendGoodNumber)
    EditText etDialogOrderSendGoodNumber;
    @BindView(R.id.tvDialogSendGoodCancel)
    TextView tvDialogSendGoodCancel;
    @BindView(R.id.tvDialogSendGoodConfirm)
    TextView tvDialogSendGoodConfirm;
    private Context mContext;
    private AlertDialog alertDialog;
    private Unbinder unbinder;
    private ChooseGoodNumberListener listener;
    private String oweDeliverNum;

    public InputSendGoodNumberDialog(Context context) {
        this.mContext = context;
        initDialog();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CommonDialog);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_inout_send_good_number, null);
        alertDialog.setView(view);
        unbinder = ButterKnife.bind(this, view);
        initListener();
    }

    private void initListener() {
        tvDialogSendGoodCancel.setOnClickListener(this);
        tvDialogSendGoodConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDialogSendGoodCancel://取消
                dismiss();
                break;
            case R.id.tvDialogSendGoodConfirm://确认

                String numberInput = etDialogOrderSendGoodNumber.getText().toString().trim();

                if (StringUtils.isEmpty(numberInput)) {
                    ToastUtil.showError("请输入数据！");
                    return;
                }
                if (StringUtils.isEmpty(oweDeliverNum)) {
                    oweDeliverNum = "0";
                }

                if (new BigDecimal(numberInput).floatValue() > new BigDecimal(oweDeliverNum).floatValue()) {
                    ToastUtil.showError("不能超过目前所欠数量！");
                    return;
                }
                dismiss();
                if (listener != null) {
                    listener.onChooseGoodNumber(etDialogOrderSendGoodNumber.getText().toString().trim());
                }
                break;
        }
    }

    public void setGoodDetail(OrderGoodBean goodBean) {
        if (goodBean == null) return;
        oweDeliverNum = goodBean.owe_deliver_num;
        ImageLoader.with(mContext)
                .placeHolder(R.mipmap.image_loading)
                .error(R.mipmap.image_load_error)
                .url(goodBean.goods_image)
                .into(ivDialogOrderSendGoodsPic);
        tvDialogOrderSendGoodName.setText(goodBean.goods_name);
        tvDialogOrderSendGoodOweNumber.setText("当前欠：" + goodBean.owe_deliver_num);
        etDialogOrderSendGoodNumber.setText(goodBean.owe_deliver_num);
        if (!StringUtils.isEmpty(goodBean.owe_deliver_num))
            etDialogOrderSendGoodNumber.setSelection(goodBean.owe_deliver_num.length());
    }

    public void destroy() {
        if (unbinder != null) unbinder.unbind();
    }

    public void show() {
        if (alertDialog != null && !alertDialog.isShowing()) alertDialog.show();
    }

    private void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
    }

    public void setOnChooseGoodNumberListener(ChooseGoodNumberListener listener) {
        this.listener = listener;
    }

    public interface ChooseGoodNumberListener {
        void onChooseGoodNumber(String number);
    }
}
