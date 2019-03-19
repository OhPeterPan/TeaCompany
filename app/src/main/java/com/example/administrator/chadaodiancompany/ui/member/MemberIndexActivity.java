package com.example.administrator.chadaodiancompany.ui.member;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.MemberIndexAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.dialog.DelStateDialog;
import com.example.administrator.chadaodiancompany.dialog.FilterBottomDialog;
import com.example.administrator.chadaodiancompany.ui.good.GoodSearchActivity;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;

import java.util.LinkedList;

import butterknife.BindView;

public class MemberIndexActivity extends BaseToolbarActivity implements View.OnClickListener, FilterBottomDialog.OnChooseMemberListener, BaseQuickAdapter.OnItemChildClickListener {

    private static final int REQUEST_CODE = 0x000111;
    @BindView(R.id.tvMemberOrderNumber)
    TextView tvMemberOrderNumber;
    @BindView(R.id.tvMemberCount)
    TextView tvMemberCount;
    @BindView(R.id.tvMemberOrderGoodCount)
    TextView tvMemberOrderGoodCount;
    @BindView(R.id.tvMemberOrderGoodMoney)
    TextView tvMemberOrderGoodMoney;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private FilterBottomDialog dialog;
    private MemberIndexAdapter adapter;

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
        return R.layout.activity_member_index;
    }

    @Override
    protected void initPresenter() {
        tvActTitle.setText("会员列表");
        ivActRightSetting.setImageResource(R.drawable.ic__member_classify_pic);
    }

    @Override
    protected void initData() {
        initAdapter();
    }

    private void initAdapter() {
        LinkedList<String> strs = new LinkedList<>();
        for (int i = 0; i < 2000; i++) {
            strs.add("jdfsadfj");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemberIndexAdapter(strs);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_gray_line));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter ada, View v, int position) {
        switch (v.getId()) {
            case R.id.tvAdapterMemberEdit://编辑会员
                MemberOperateActivity.launch(this, 0, REQUEST_CODE);
                break;
            case R.id.tvAdapterMemberDel://删除会员
                showDelDialog();
                break;
        }
    }

    private void showDelDialog() {
        DelStateDialog dialog = new DelStateDialog(this);
        dialog.show();

    }

    @Override
    protected void initListener() {
        ivActRightSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivActRightSetting://筛选
                showFilterDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GoodSearchActivity.SEARCH_REQUEST_CODE) {
    /*        if (resultCode == GoodClassifyActivity.CLASS_RESULT_CODE) {//分类返回
                stcId = data.getStringExtra(CommonUtil.STC_ID);
                keyWord = "";
                sendNet(true);
            }*/
            if (resultCode == GoodSearchActivity.SEARCH_RESULT_CODE) {
                String keyWord = data.getStringExtra(CommonUtil.KEYWORD);
                LogUtil.logI("hahahaha");
            }
        }
    }

    @Override
    public void chooseMemberStateListener(int tag) {
        switch (tag) {
            case 0:
                GoodSearchActivity.launch(this, 2);
                break;
            case 1:
                MemberOperateActivity.launch(this, 1, REQUEST_CODE);
                break;
            case 2:

                break;
        }
    }

    private void showFilterDialog() {
        if (dialog == null) {
            dialog = new FilterBottomDialog(this);
            dialog.setOnChooseMemberStateListener(this);
        }
        dialog.show();
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MemberIndexActivity.class);
        context.startActivity(intent);
    }
}
