package com.example.administrator.chadaodiancompany.ui.member;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.MemberGradeBean;
import com.example.administrator.chadaodiancompany.bean.MemberGradeBean.DatasBean.GradeListBean;
import com.example.administrator.chadaodiancompany.dialog.AuditConfirmDialog;
import com.example.administrator.chadaodiancompany.dialog.AuditRefuseDialog;
import com.example.administrator.chadaodiancompany.presenter.MemberAuditInfoPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberAuditInfoView;

import java.util.List;

import butterknife.BindView;

public class MemberAuditInfoActivity extends BaseToolbarActivity<MemberAuditInfoPresenter> implements View.OnClickListener, AuditRefuseDialog.IOnMemberAuditRefuseListener, IMemberAuditInfoView, AuditConfirmDialog.IOnMemberAuditAgreeListener {

    @BindView(R.id.tvMemberAuditInfoName)
    TextView tvMemberAuditInfoName;
    @BindView(R.id.tvMemberAuditInfo)
    TextView tvMemberAuditInfo;
    @BindView(R.id.tvMemberAuditInfoMemberName)
    TextView tvMemberAuditInfoMemberName;
    @BindView(R.id.tvMemberAuditInfoTime)
    TextView tvMemberAuditInfoTime;
    @BindView(R.id.tvMemberAuditInfoRefuse)
    TextView tvMemberAuditInfoRefuse;
    @BindView(R.id.tvMemberAuditInfoConfirm)
    TextView tvMemberAuditInfoConfirm;
    private String auditId;
    private AuditRefuseDialog refuseDialog;
    private int result = 0;//0拒绝  1同意
    private List<GradeListBean> gradeList;
    private AuditConfirmDialog agreeDialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_audit_info;
    }

    @Override
    protected void initPresenter() {
        presenter = new MemberAuditInfoPresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("会员审核");
        ivActRightSetting.setVisibility(View.GONE);
        auditId = getIntent().getStringExtra(CommonUtil.MEMBER_ID);
        fillPageData();
        sendNet();
    }

    private void sendNet() {
        presenter.sendNetGradeList(key);
    }

    @Override
    public void getMemberGradeList(String result) {
        MemberGradeBean memberGradeBean = JSON.parseObject(result, MemberGradeBean.class);
        MemberGradeBean.DatasBean datas = memberGradeBean.datas;
        List<GradeListBean> gradeList = datas.grade_list;
        this.gradeList = gradeList;
    }

    @Override
    public void auditResult(String re) {
        if (result == 0) {
            ToastUtil.showError("已拒绝该次申请！");
        } else {
            ToastUtil.showError("已同意该次申请！");
        }
        setResult(0x002);
        finish();
    }

    private void fillPageData() {
        String reason = getIntent().getStringExtra(CommonUtil.MEMBER_INFO);
        String cName = getIntent().getStringExtra(CommonUtil.C_NAME);
        String name = getIntent().getStringExtra(CommonUtil.NAME);
        String time = getIntent().getStringExtra(CommonUtil.TIME);
        tvMemberAuditInfoName.setText(cName);
        tvMemberAuditInfo.setText("申请内容：\n" + reason);
        tvMemberAuditInfoMemberName.setText("申请人：" + name);
        tvMemberAuditInfoTime.setText("申请时间：" + time);
    }

    @Override
    protected void initListener() {
        tvMemberAuditInfoRefuse.setOnClickListener(this);
        tvMemberAuditInfoConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMemberAuditInfoRefuse://拒绝
                refuse();
                break;
            case R.id.tvMemberAuditInfoConfirm://同意
                confirm();
                break;
        }
    }

    private void confirm() {
        if (agreeDialog == null) {
            agreeDialog = new AuditConfirmDialog(this, gradeList);
            agreeDialog.setOnAuditAgreeListener(this);
        }
        agreeDialog.show();
    }

    @Override
    public void agreeListener(String gradeId) {
        result = 1;
        presenter.sendNetAuditResult(key, auditId, String.valueOf(result), gradeId, "");
    }

    private void refuse() {
        if (refuseDialog == null) {
            refuseDialog = new AuditRefuseDialog(this);
            refuseDialog.setOnAuditRefuseListener(this);
        }
        refuseDialog.show();
    }

    @Override
    public void refuseListener(String reason) {
        result = 0;
        presenter.sendNetAuditResult(key, auditId, String.valueOf(result), "", reason);
    }

    public static void launchResult(Context context,
                                    String auditId,
                                    String auditReason,
                                    String cName,
                                    String memberName,
                                    String time) {
        Intent intent = new Intent(context, MemberAuditInfoActivity.class);
        intent.putExtra(CommonUtil.MEMBER_ID, auditId);
        intent.putExtra(CommonUtil.MEMBER_INFO, auditReason);
        intent.putExtra(CommonUtil.C_NAME, cName);
        intent.putExtra(CommonUtil.NAME, memberName);
        intent.putExtra(CommonUtil.TIME, time);
        ActivityCompat.startActivityForResult((AppCompatActivity) context, intent, 0x0001, null);
    }
}
