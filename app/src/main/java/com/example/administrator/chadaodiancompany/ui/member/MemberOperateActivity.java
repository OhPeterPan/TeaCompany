package com.example.administrator.chadaodiancompany.ui.member;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.MemberGradeBean;
import com.example.administrator.chadaodiancompany.bean.MemberListBean;
import com.example.administrator.chadaodiancompany.dialog.MemberGradeDialog;
import com.example.administrator.chadaodiancompany.presenter.MemberOperatePresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberOperateView;

import java.util.List;

import butterknife.BindView;

public class MemberOperateActivity extends BaseToolbarActivity<MemberOperatePresenter>
        implements View.OnClickListener,
        IMemberOperateView,
        MemberGradeDialog.IChooseGradeListener {

    public static int RESULT_CODE = 0x001111;
    @BindView(R.id.etMemberCompanyName)
    EditText etMemberCompanyName;
    @BindView(R.id.tvMemberCompanyGrade)
    TextView tvMemberCompanyGrade;
    @BindView(R.id.tvMemberSaveInfo)
    TextView tvMemberSaveInfo;
    private List<MemberGradeBean.DatasBean.GradeListBean> gradeList;
    private MemberGradeDialog dialog;
    private String gradeId;
    private int flag;
    private String memberId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_operate;
    }

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
    protected void initPresenter() {
        presenter = new MemberOperatePresenter(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        flag = intent.getIntExtra(CommonUtil.FLAG, 0);
        MemberListBean bean = intent.getParcelableExtra(CommonUtil.MEMBER_INFO);
        if (bean != null) {
            memberId = bean.member_id;
            etMemberCompanyName.setText(bean.c_name);
            tvMemberCompanyGrade.setText(bean.grade_name);
        }
        tvActTitle.setText(flag == 0 ? "编辑会员" : "新增会员");
        etMemberCompanyName.setEnabled(flag != 0);
        ivActRightSetting.setVisibility(View.GONE);
        sendNet();
    }

    private void sendNet() {
        presenter.sendNetGradeList(key);
    }

    @Override
    public void getGradeListResult(String result) {
        MemberGradeBean memberGradeBean = JSON.parseObject(result, MemberGradeBean.class);
        MemberGradeBean.DatasBean datas = memberGradeBean.datas;
        gradeList = datas.grade_list;
    }

    @Override
    public void changeMemberGradeResult(String result) {
        ToastUtil.showSuccess("会员编辑成功");
        setResult(RESULT_CODE);
        finish();
    }

    @Override
    public void addMemberResult(String result) {
        ToastUtil.showSuccess("会员添加成功");
        setResult(RESULT_CODE);
        finish();
    }

    @Override
    protected void initListener() {
        tvMemberCompanyGrade.setOnClickListener(this);
        tvMemberSaveInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMemberCompanyGrade://弹出会员等级
                showGradeDialog();
                break;
            case R.id.tvMemberSaveInfo://保存
                if (flag == 0) {
                    if (presenter.checkParams("dfhdfhh", gradeId)) {
                        presenter.sendNetChangeMember(key, memberId, gradeId);
                    }
                } else {
                    String companyName = etMemberCompanyName.getText().toString().trim();
                    if (presenter.checkParams(companyName, gradeId)) {
                        presenter.sendNetAddMember(key, companyName, gradeId);
                    }
                }
                break;
        }
    }

    private void showGradeDialog() {
        if (dialog == null) {
            dialog = new MemberGradeDialog(this, gradeList);
            dialog.setOnChooseGradeListener(this);
        }
        dialog.show();
    }

    @Override
    public void chooseGradeListener(MemberGradeBean.DatasBean.GradeListBean item) {
        tvMemberCompanyGrade.setText(item.grade_name);
        this.gradeId = item.grade_id;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.cancelTag();
    }

    public static void launch(Context context, int flag, MemberListBean bean, int requestCode) {
        Intent intent = new Intent(context, MemberOperateActivity.class);
        intent.putExtra(CommonUtil.FLAG, flag);
        intent.putExtra(CommonUtil.MEMBER_INFO, bean);
        ((AppCompatActivity) context).startActivityForResult(intent, requestCode);
    }
}
