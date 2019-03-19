package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;

import com.example.administrator.chadaodiancompany.R;

public class MemberGradeDialog extends BottomSheetDialog {
    public MemberGradeDialog(@NonNull Context context) {
        super(context, R.style.noDimDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_member_grade);
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(-1, -2);
    }
}
