package com.example.administrator.chadaodiancompany.ui.member;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.MemberGradeAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.MemberGradeBean;
import com.example.administrator.chadaodiancompany.bean.MemberGradeBean.DatasBean.GradeListBean;
import com.example.administrator.chadaodiancompany.dialog.DelStateDialog;
import com.example.administrator.chadaodiancompany.presenter.MemberGradePresenter;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberGradeView;

import java.util.List;

import butterknife.BindView;

public class MemberGradeActivity extends BaseToolbarActivity<MemberGradePresenter> implements View.OnClickListener, IMemberGradeView, BaseQuickAdapter.OnItemChildClickListener, DelStateDialog.IOnConfirmClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MemberGradeAdapter adapter;
    private DelStateDialog delDialog;
    private int position;
    private GradeListBean bean;

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
        return R.layout.activity_member_grade;
    }

    @Override
    protected void initPresenter() {
        presenter = new MemberGradePresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("会员等级");
        tvTitleRight.setVisibility(View.VISIBLE);
        ivActRightSetting.setVisibility(View.GONE);
        tvTitleRight.setText("新增");
        initAdapter();
        sendNet();
    }

    private void initAdapter() {
        adapter = new MemberGradeAdapter(recyclerView,
                new LinearLayoutManager(this),
                null);
        adapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter ada, View v, int position) {
        bean = adapter.getItem(position);
        this.position = position;
        switch (v.getId()) {
            case R.id.tvSwipeEdit://编辑
                MemberGradeOperateActivity.launchResult(this, 0, bean.grade_id);
                break;
            case R.id.tvSwipeDel://删除
                showDelGradeDialog();
                break;
        }
    }

    private void showDelGradeDialog() {
        if (delDialog == null) {
            delDialog = new DelStateDialog(this);
            delDialog.setOnConfirmClickListener(this);
        }
        delDialog.show();
        delDialog.setDialogInfo("删除等级，会同时删除此等级对应的会员\n确认删除？");
    }

    @Override
    public void confirmClickListener() {
        presenter.sendNetDelMember(key, bean.grade_id);
    }

    private void sendNet() {
        presenter.sendNetMemberList(key);
    }

    @Override
    public void getMemberGradeListResult(String result) {
        MemberGradeBean memberGradeBean = JSON.parseObject(result, MemberGradeBean.class);
        MemberGradeBean.DatasBean datas = memberGradeBean.datas;
        List<MemberGradeBean.DatasBean.GradeListBean> gradeList = datas.grade_list;
        adapter.notifyData(gradeList);
    }

    @Override
    public void delMemberInfoResult(String result) {
        ToastUtil.showSuccess("删除成功！");
        adapter.remove(position);
    }

    @Override
    protected void initListener() {
        tvTitleRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTitleRight://新增
                MemberGradeOperateActivity.launchResult(this, 1, "");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x0200) {
            if (requestCode == 0x00200) {
                sendNet();
            }
        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MemberGradeActivity.class);
        ContextCompat.startActivity(context, intent, null);
    }
}
