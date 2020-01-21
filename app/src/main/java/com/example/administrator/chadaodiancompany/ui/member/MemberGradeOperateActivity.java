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
import com.example.administrator.chadaodiancompany.bean.MemberGradeInfoBean;
import com.example.administrator.chadaodiancompany.bean.MemberGradeInfoBean.MemberGradeInfoData.GradeInfoBean;
import com.example.administrator.chadaodiancompany.presenter.MemberGradeOperatePresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.view.EditFocusStateLayout;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberGradeOperateView;

import butterknife.BindView;

public class MemberGradeOperateActivity extends BaseToolbarActivity<MemberGradeOperatePresenter> implements View.OnClickListener, IMemberGradeOperateView {

    @BindView(R.id.efsGradeName)
    EditFocusStateLayout efsGradeName;
    @BindView(R.id.efsGradeMoney)
    EditFocusStateLayout efsGradeMoney;
    @BindView(R.id.efsGradeSort)
    EditFocusStateLayout efsGradeSort;
    @BindView(R.id.tvMemberGradeStateSave)
    TextView tvMemberGradeStateSave;
    private String gradeId;
    private int flag;

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
        return R.layout.activity_member_grade_operate;
    }

    @Override
    protected void initPresenter() {
        presenter = new MemberGradeOperatePresenter(this);
    }

    @Override
    protected void initData() {
        flag = getIntent().getIntExtra(CommonUtil.FLAG, 0);
        gradeId = getIntent().getStringExtra(CommonUtil.GRADE_ID);
        tvActTitle.setText(flag == 0 ? "编辑会员等级" : "新增会员等级");
        ivActRightSetting.setVisibility(View.GONE);
        sendNet();
    }

    private void sendNet() {
        if (flag == 0) {
            presenter.sendNetGetGradeInfo(key, gradeId);
        }
    }

    @Override
    public void getMemberGradeOperateResult(String result) {
        MemberGradeInfoBean bean = JSON.parseObject(result, MemberGradeInfoBean.class);
        MemberGradeInfoBean.MemberGradeInfoData datas = bean.datas;
        MemberGradeInfoBean.MemberGradeInfoData.GradeInfoBean gradeInfo = datas.grade_info;
        initPageInfo(gradeInfo);
    }

    private void initPageInfo(GradeInfoBean gradeInfo) {
        if (gradeInfo == null) return;
        efsGradeName.setText(gradeInfo.grade_name);
        efsGradeMoney.setText(gradeInfo.money);
        efsGradeSort.setText(gradeInfo.sort);
    }

    @Override
    public void editMemberGradeResult(String result) {
        ToastUtil.showSuccess("会员等级编辑成功！");
        operateGradeResult();
    }

    @Override
    public void addMemberGradeOperateResult(String result) {
        ToastUtil.showSuccess("会员等级添加成功！");
        operateGradeResult();
    }

    private void operateGradeResult() {
        setResult(0x0200);
        finish();
    }

    @Override
    protected void initListener() {
        tvMemberGradeStateSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMemberGradeStateSave://确定提交
                operateGrade();
                break;
        }
    }

    private void operateGrade() {
        String gradeName = efsGradeName.getText();
        String money = efsGradeMoney.getText();
        String sort = efsGradeSort.getText();
        if (presenter.checkParams(gradeName, money, sort)) {
            if (flag == 0) {
                presenter.sendNetEditGrade(key, gradeId, gradeName, money, sort);
            } else {
                presenter.sendNetAddGrade(key, gradeName, money, sort);
            }
        }
    }

    public static void launchResult(Context context, int flag, String gradeId) {
        Intent intent = new Intent(context, MemberGradeOperateActivity.class);
        intent.putExtra(CommonUtil.FLAG, flag);
        intent.putExtra(CommonUtil.GRADE_ID, gradeId);
        ActivityCompat.startActivityForResult((AppCompatActivity) context, intent, 0x00200, null);
    }
}
