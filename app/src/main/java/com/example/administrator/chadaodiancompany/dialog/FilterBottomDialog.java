package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.example.administrator.chadaodiancompany.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterBottomDialog extends BottomSheetDialog implements View.OnClickListener {
    @BindView(R.id.tvFilterDialogMemberSearch)
    TextView tvFilterDialogMemberSearch;
    @BindView(R.id.tvFilterDialogAddMember)
    TextView tvFilterDialogAddMember;
    @BindView(R.id.tvFilterDialogOutTable)
    TextView tvFilterDialogOutTable;
    @BindView(R.id.tvFilterDialogCancel)
    TextView tvFilterDialogCancel;
    @BindView(R.id.tvFilterDialogMemberGrade)
    TextView tvFilterDialogMemberGrade;
    @BindView(R.id.tvFilterDialogMemberAudit)
    TextView tvFilterDialogMemberAudit;
    @BindView(R.id.tvFilterDialogMemberSetting)
    TextView tvFilterDialogMemberSetting;
    private OnChooseMemberListener listener;

    public FilterBottomDialog(@NonNull Context context) {
        super(context, R.style.AlertDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_member_filter);
        getWindow().setLayout(ScreenUtils.getScreenWidth(), -2);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        tvFilterDialogMemberSearch.setOnClickListener(this);
        tvFilterDialogAddMember.setOnClickListener(this);
        tvFilterDialogOutTable.setOnClickListener(this);

        tvFilterDialogCancel.setOnClickListener(this);

        tvFilterDialogMemberGrade.setOnClickListener(this);
        tvFilterDialogMemberAudit.setOnClickListener(this);
        tvFilterDialogMemberSetting.setOnClickListener(this);
    }

    public void setOnChooseMemberStateListener(OnChooseMemberListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener == null)
            return;
        switch (v.getId()) {
            case R.id.tvFilterDialogMemberSearch://会员搜索
                //ToastUtil.showInfoShort("会员搜索");
                listener.chooseMemberStateListener(0);
                break;
            case R.id.tvFilterDialogAddMember://新增会员
                listener.chooseMemberStateListener(1);
                break;
            case R.id.tvFilterDialogOutTable://导出会员表
                listener.chooseMemberStateListener(2);
                break;
            case R.id.tvFilterDialogMemberGrade://会员等级
                listener.chooseMemberStateListener(3);
                break;
            case R.id.tvFilterDialogMemberAudit://会员审核
                listener.chooseMemberStateListener(4);
                break;
            case R.id.tvFilterDialogMemberSetting://会员设置
                listener.chooseMemberStateListener(5);
                break;
        }
        dismiss();
    }

    public interface OnChooseMemberListener {
        /**
         * @param tag 0会员搜索 1新增会员 2导出会员表 3会员等级 4会员审核 5会员设置
         */
        void chooseMemberStateListener(int tag);
    }
}
