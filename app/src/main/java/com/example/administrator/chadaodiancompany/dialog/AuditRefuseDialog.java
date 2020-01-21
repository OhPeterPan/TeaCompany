package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.util.ToastUtil;

public class AuditRefuseDialog extends AlertDialog implements View.OnClickListener {

    private EditText etRefuseReason;
    private TextView tvAuditDialogCancel;
    private TextView tvAuditDialogConfirm;
    private IOnMemberAuditRefuseListener listener;

    public AuditRefuseDialog(@NonNull Context context) {
        super(context, R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_audit_refuse);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        initView();

        initListener();
    }

    private void initView() {
        etRefuseReason = findViewById(R.id.etRefuseReason);
        tvAuditDialogCancel = findViewById(R.id.tvAuditDialogCancel);
        tvAuditDialogConfirm = findViewById(R.id.tvAuditDialogConfirm);
    }

    private void initListener() {
        tvAuditDialogCancel.setOnClickListener(this);
        tvAuditDialogConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.tvAuditDialogConfirm://确定
                if (listener != null) {
                    String reason = etRefuseReason.getText().toString().trim();
                    if (StringUtils.isEmpty(reason)) {
                        ToastUtil.showError("请输入拒绝理由！");
                        return;
                    }
                    listener.refuseListener(reason);
                }
                break;
        }
    }

    public void setOnAuditRefuseListener(IOnMemberAuditRefuseListener listener) {
        this.listener = listener;
    }

    public interface IOnMemberAuditRefuseListener {
        void refuseListener(String reason);
    }
}
