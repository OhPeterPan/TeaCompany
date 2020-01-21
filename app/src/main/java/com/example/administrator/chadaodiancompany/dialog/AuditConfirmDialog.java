package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.MemberGradeBean.DatasBean.GradeListBean;
import com.example.administrator.chadaodiancompany.popupWindow.ChooseGeadePopupWindow;

import java.util.List;

public class AuditConfirmDialog extends AlertDialog implements View.OnClickListener, ChooseGeadePopupWindow.IOnChooseGradeListener {

    private Context mContext;
    private List<GradeListBean> gradeList;
    private TextView tvConfirmGrade;
    private TextView tvAgreeAuditDialogCancel;
    private TextView tvAgreeAuditDialogConfirm;
    private IOnMemberAuditAgreeListener listener;
    private String gradeId = "";
    private ChooseGeadePopupWindow popupWindow;

    public AuditConfirmDialog(@NonNull Context context, List<GradeListBean> gradeList) {
        super(context, R.style.CommonDialog);
        this.gradeList = gradeList;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_audit_confirm);
        initView();
        initListener();
    }

    private void initView() {
        tvConfirmGrade = findViewById(R.id.tvConfirmGrade);
        tvAgreeAuditDialogCancel = findViewById(R.id.tvAgreeAuditDialogCancel);
        tvAgreeAuditDialogConfirm = findViewById(R.id.tvAgreeAuditDialogConfirm);
        if (gradeList != null && !gradeList.isEmpty()) {
            tvConfirmGrade.setText(gradeList.get(0).grade_name);
            gradeId = gradeList.get(0).grade_id;
        }
    }

    private void initListener() {
        tvConfirmGrade.setOnClickListener(this);
        tvAgreeAuditDialogCancel.setOnClickListener(this);
        tvAgreeAuditDialogConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvConfirmGrade:
                showPopupWindow();
                break;
            case R.id.tvAgreeAuditDialogConfirm://确定
//                dismiss();
                agreeAudit();
            case R.id.tvAgreeAuditDialogCancel://取消
                dismiss();
                break;
        }
    }

    private void agreeAudit() {
        if (listener != null) {
            listener.agreeListener(gradeId);
        }
    }

    private void showPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new ChooseGeadePopupWindow(mContext, gradeList);
            popupWindow.setOnGradeListener(this);
        }
        popupWindow.show(tvConfirmGrade);
    }

    @Override
    public void getGradeListener(GradeListBean bean) {
        gradeId = bean.grade_id;
        tvConfirmGrade.setText(bean.grade_name);
    }

    public void setOnAuditAgreeListener(IOnMemberAuditAgreeListener listener) {
        this.listener = listener;
    }

    public interface IOnMemberAuditAgreeListener {
        void agreeListener(String gradeId);
    }
}
