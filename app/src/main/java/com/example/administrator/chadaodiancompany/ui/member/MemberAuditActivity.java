package com.example.administrator.chadaodiancompany.ui.member;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.MemberAuditAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.MemberAuditBean;
import com.example.administrator.chadaodiancompany.presenter.MemberAuditPresenter;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberAuditView;

import butterknife.BindView;

public class MemberAuditActivity extends BaseToolbarActivity<MemberAuditPresenter> implements IMemberAuditView, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MemberAuditAdapter adapter;
    private int position;

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
        return R.layout.activity_member_audit;
    }

    @Override
    protected void initPresenter() {
        presenter = new MemberAuditPresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("会员审核");
        ivActRightSetting.setVisibility(View.GONE);
        initAdapter();
        sendNet();
    }

    private void initAdapter() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_gray_line));
        adapter = new MemberAuditAdapter(R.layout.adapter_member_audit, recyclerView, new LinearLayoutManager(this), itemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        MemberAuditBean.MemberAuditDatas.AuditListBean listBean = adapter.getItem(position);
        this.position = position;
        MemberAuditInfoActivity.launchResult(this,
                listBean.audit_id,
                listBean.entry_shop_reason,
                listBean.c_name,
                listBean.member_name,
                listBean.entry_shop_time
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x002) {
            if (requestCode == 0x0001) {
                adapter.remove(position);
            }
        }
    }

    private void sendNet() {
        presenter.sendNet(key);
    }

    @Override
    public void getMemberAuditListResult(String result) {
        MemberAuditBean memberAuditBean = JSON.parseObject(result, MemberAuditBean.class);
        MemberAuditBean.MemberAuditDatas datas = memberAuditBean.datas;
        adapter.notifyData(datas.audit_list);
    }

    @Override
    protected void initListener() {

    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MemberAuditActivity.class);
        context.startActivity(intent);
    }
}
