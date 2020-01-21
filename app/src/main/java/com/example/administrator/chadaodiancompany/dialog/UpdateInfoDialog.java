package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;

public class UpdateInfoDialog extends AlertDialog implements View.OnClickListener {

    private String url;
    private Context mContext;
    private String info;
    private TextView tvUpdateInfo;
    private TextView tvConfirmUpdate;

    public UpdateInfoDialog(@NonNull Context context, String info, String url) {
        super(context, R.style.CommonDialog);
        this.mContext = context;
        this.info = info;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_info);
        setCanceledOnTouchOutside(false);
        initView();
        initListener();
    }

    private void initView() {
        tvUpdateInfo = findViewById(R.id.tvUpdateInfo);
        tvConfirmUpdate = findViewById(R.id.tvConfirmUpdate);
    }

    private void initListener() {
        tvConfirmUpdate.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        tvUpdateInfo.setText(info);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        dismiss();
        UpdateDialog updateDialog = new UpdateDialog(mContext, url);
        updateDialog.show();
    }
}
