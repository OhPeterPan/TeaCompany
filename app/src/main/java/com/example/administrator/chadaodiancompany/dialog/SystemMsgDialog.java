package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;

public class SystemMsgDialog extends AlertDialog implements View.OnClickListener {

    private TextView tvConfirmLookMsg;
    private TextView tvDialogMsgInfo;

    public SystemMsgDialog(@NonNull Context context) {
        super(context, R.style.noDimDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_system_msg);
        initView();
        initListener();
    }

    private void initView() {
        tvDialogMsgInfo = findViewById(R.id.tvDialogMsgInfo);
        tvConfirmLookMsg = findViewById(R.id.tvConfirmLookMsg);
    }

    private void initListener() {
        tvConfirmLookMsg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvConfirmLookMsg:
                dismiss();
                break;
        }
    }

    public void setText(String message) {
        tvDialogMsgInfo.setText(message);
    }
}
