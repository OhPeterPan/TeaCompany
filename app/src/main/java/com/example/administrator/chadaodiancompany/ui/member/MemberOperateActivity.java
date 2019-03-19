package com.example.administrator.chadaodiancompany.ui.member;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.dialog.MemberGradeDialog;
import com.example.administrator.chadaodiancompany.util.CommonUtil;

import butterknife.BindView;

public class MemberOperateActivity extends BaseToolbarActivity implements View.OnClickListener {

    @BindView(R.id.etMemberCompanyName)
    EditText etMemberCompanyName;
    @BindView(R.id.tvMemberCompanyGrade)
    TextView tvMemberCompanyGrade;
    @BindView(R.id.tvMemberSaveInfo)
    TextView tvMemberSaveInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_operate;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        int flag = intent.getIntExtra(CommonUtil.FLAG, 0);
        tvActTitle.setText(flag == 0 ? "编辑会员" : "新增会员");
        ivActRightSetting.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        tvMemberCompanyGrade.setOnClickListener(this);
    }

    public static void launch(Context context, int flag, int requestCode) {
        Intent intent = new Intent(context, MemberOperateActivity.class);
        intent.putExtra(CommonUtil.FLAG, flag);
        ((AppCompatActivity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMemberCompanyGrade:
                new MemberGradeDialog(this).show();
                break;
        }
    }
}
