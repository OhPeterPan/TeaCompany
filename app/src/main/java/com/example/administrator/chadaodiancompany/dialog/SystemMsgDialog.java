package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.example.administrator.chadaodiancompany.R;

public class SystemMsgDialog extends AlertDialog {
    public SystemMsgDialog(@NonNull Context context) {
        super(context, R.style.noDimDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_system_msg);
    }
}
