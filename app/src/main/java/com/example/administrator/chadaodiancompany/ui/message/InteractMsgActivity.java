package com.example.administrator.chadaodiancompany.ui.message;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.PrivateLetterAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.InteractMessageBean;
import com.example.administrator.chadaodiancompany.bean.PrivateLetterBean;
import com.example.administrator.chadaodiancompany.bean.PrivateLetterBean.DatasBean.PrivateLetterListBean;
import com.example.administrator.chadaodiancompany.presenter.InteractMessagePresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.TimeUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IInteractMessageView;

import java.util.List;

import butterknife.BindView;

public class InteractMsgActivity extends BaseToolbarActivity<InteractMessagePresenter> implements IInteractMessageView,
        BaseQuickAdapter.RequestLoadMoreListener,
        View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.etInteractInfo)
    EditText etInteractInfo;
    @BindView(R.id.ivSendInteractInfo)
    ImageView ivSendInteractInfo;
    private String uId;
    private String uName;
    public boolean hasMore;
    public boolean isRefresh = true;
    public int curPage = 1;
    private PrivateLetterAdapter adapter;

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
        return R.layout.activity_interact_msg;
    }

    @Override
    protected void initPresenter() {
        presenter = new InteractMessagePresenter(this);
    }

    @Override
    protected void initData() {
        uId = getIntent().getStringExtra(CommonUtil.MEMBER_ID);
        uName = getIntent().getStringExtra(CommonUtil.NAME);
        tvActTitle.setText(uName);
        ivActRightSetting.setVisibility(View.GONE);
        initAdapter();
        sendNet(true);
    }

    private void initAdapter() {
        adapter = new PrivateLetterAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recyclerView);
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        sendNet(false);
    }

    private void sendNet(boolean showDialog) {
        if (showDialog) {
            isRefresh = true;
            curPage = 1;
        }
        presenter.sendNetGetInteractDetail(key, uId, curPage, showDialog);
    }

    @Override
    public void getInteractDetailResult(String result) {
        curPage++;
        PrivateLetterBean bean = JSON.parseObject(result, PrivateLetterBean.class);
        hasMore = bean.hasmore;
        List<PrivateLetterBean.DatasBean.PrivateLetterListBean> list = bean.datas.list;
        if (isRefresh) {
            adapter.setNewData(list);
        } else {
            adapter.addData(list);
        }
        if (hasMore) isRefresh = false;

        if (adapter.isLoading() && hasMore) {
            adapter.loadMoreComplete();
        }
        if (!hasMore) {
            adapter.loadMoreEnd();
        }
    }

    @Override
    public void sendMessageResult(String result) {
        InteractMessageBean bean = JSON.parseObject(result, InteractMessageBean.class);
        PrivateLetterListBean msg = bean.datas.msg;
        msg.type = 1;
        msg.time = TimeUtil.getNowTime(TimeUtil.DEFAULT_ALL_FORMAT);
        adapter.addData(msg);
        recyclerView.smoothScrollToPosition(adapter.getData().size() - 1);
        etInteractInfo.setText("");
    }

    @Override
    protected void initListener() {
        ivSendInteractInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (StringUtils.isEmpty(etInteractInfo.getText().toString().trim())) {
            ToastUtil.showError("请输入要发送的内容！");
            return;
        }
        presenter.sendNetContent(key, uId, uName, etInteractInfo.getText().toString().trim());
    }

    public static void launch(Context context, String uId, String uName) {
        Intent intent = new Intent(context, InteractMsgActivity.class);
        intent.putExtra(CommonUtil.MEMBER_ID, uId);
        intent.putExtra(CommonUtil.NAME, uName);
        context.startActivity(intent);
    }
}
