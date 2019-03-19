package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MsgDialog implements View.OnClickListener {
    @BindView(R.id.txtMsg)
    TextView txtMsg;
    @BindView(R.id.btnPos)
    Button btnPos;
    private Context mContext;
    private AlertDialog alertDialog;
    private Unbinder unBinder;

    public MsgDialog(Context context) {
        this.mContext = context;
        initDialog();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CommonDialog);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_msg_dialog, null);
        unBinder = ButterKnife.bind(this, view);
        alertDialog.setView(view);
        initListener();
    }

    private void initListener() {
        btnPos.setOnClickListener(this);
    }

    public void setMessage(String msg) {
        txtMsg.setText(msg);
    }

    public void show() {
        if (alertDialog != null && !alertDialog.isShowing()) {
            alertDialog.show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPos:
                dismiss();
                break;
        }
    }
}
