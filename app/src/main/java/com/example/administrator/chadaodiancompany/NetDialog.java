package com.example.administrator.chadaodiancompany;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

public class NetDialog extends Dialog {
    public NetDialog(@NonNull Context context) {
        this(context, R.style.loadingDialog);
    }

    public NetDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_net_dialog);
    }
}
