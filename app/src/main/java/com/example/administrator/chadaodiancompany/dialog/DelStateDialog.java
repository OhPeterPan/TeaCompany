package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DelStateDialog extends AlertDialog implements View.OnClickListener {
    @BindView(R.id.tvDelDialogInfo)
    TextView tvDelDialogInfo;
    @BindView(R.id.tvDelDialogCancel)
    TextView tvDelDialogCancel;
    @BindView(R.id.tvDelDialogConfirm)
    TextView tvDelDialogConfirm;

    public DelStateDialog(@NonNull Context context) {
        super(context, R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_del_state);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        tvDelDialogConfirm.setOnClickListener(this);
        tvDelDialogCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (v.getId() == R.id.tvDelDialogConfirm) {//确认

        }
    }
}
