package com.example.administrator.chadaodiancompany.ui.message;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.InteractMessageAdapter;
import com.example.administrator.chadaodiancompany.adapter.MessageAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.InteractBean;
import com.example.administrator.chadaodiancompany.bean.InteractBean.InteractDatasBean.InteractListBean;
import com.example.administrator.chadaodiancompany.bean.MsgBean;
import com.example.administrator.chadaodiancompany.bean.MsgBean.MsgDataBean.MsgListBean;
import com.example.administrator.chadaodiancompany.dialog.DelStateDialog;
import com.example.administrator.chadaodiancompany.dialog.SystemMsgDialog;
import com.example.administrator.chadaodiancompany.presenter.MessagePresenter;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IMessageView;
import com.example.administrator.chadaodiancompany.viewImpl.IView;

import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseToolbarActivity<MessagePresenter> implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, IView, IMessageView, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemLongClickListener, DelStateDialog.IOnConfirmClickListener {

    @BindView(R.id.tvMessageSystem)
    TextView tvMessageSystem;
    @BindView(R.id.tvMessageInteract)
    TextView tvMessageInteract;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private int tag = 0;//0代表系统消息  1代表显示互动消息
    private BaseQuickAdapter adapter;
    private boolean isRefresh = true;
    private int curPage = 1;
    private boolean hasMore = false;
    private DelStateDialog delDialog;
    private MsgBean.MsgDataBean.MsgListBean msgListBean;
    private int mPosition;

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
        return R.layout.activity_message;
    }

    @Override
    protected void initPresenter() {
        presenter = new MessagePresenter(this);
    }

    @Override
    protected void initData() {
        showToolbar();
        initAdapter();
        sendNet(true);
    }

    private void sendNet(boolean showDialog) {
        if (showDialog) {
            isRefresh = true;
            curPage = 1;
        }
        if (tag == 0)
            presenter.sendNetGetMessageResult(key, curPage, showDialog);
        else
            presenter.sendNetGetInteractMessageResult(key, curPage, showDialog);
    }

    @Override
    public void getMessageListResult(String result) {
        curPage++;
        MsgBean msgBean = JSON.parseObject(result, MsgBean.class);
        hasMore = msgBean.hasmore;
        List<MsgBean.MsgDataBean.MsgListBean> msgList = msgBean.datas.msg_list;
        if (isRefresh) {
            adapter.setNewData(msgList);
            adapter.setEmptyView(getEmptyView("暂无数据"));
        } else {
            adapter.addData(msgList);
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
    public void getInteractMessageResult(String result) {
        curPage++;
        InteractBean msgBean = JSON.parseObject(result, InteractBean.class);
        hasMore = msgBean.hasmore;
        List<InteractBean.InteractDatasBean.InteractListBean> list = msgBean.datas.list;
        if (isRefresh) {
            adapter.setNewData(list);
            adapter.setEmptyView(getEmptyView("暂无数据"));
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
    public void readTagResult(String result) {
        MsgListBean msgListBean = (MsgListBean) adapter.getItem(mPosition);
        msgListBean.is_read = "1";
        adapter.notifyItemChanged(mPosition);
    }

    @Override
    public void readTagsResult(String result) {

    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (tag == 0)
            adapter = new MessageAdapter();
        else
            adapter = new InteractMessageAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recyclerView);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_gray_line));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        sendNet(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        this.mPosition = position;
        if (tag == 0) {//系统消息  弹窗
            MsgListBean msgListBean = (MsgListBean) adapter.getItem(position);
            if (StringUtils.equals("0", msgListBean.is_read)) {

                presenter.sendNetReadMessage(key, msgListBean.sm_id);
            }
            showMsgDialog((MsgListBean) adapter.getItem(position));
        } else if (tag == 1) {
            InteractListBean bean = (InteractListBean) adapter.getItem(position);
            InteractMsgActivity.launch(this, bean.u_id, bean.u_name);
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter ada, View view, int position) {
        this.mPosition = position;
        if (tag == 0) {//系统消息  弹窗
            msgListBean = (MsgListBean) adapter.getItem(position);
            showDelDialog();
        }
        return false;
    }

    private void showDelDialog() {
        if (delDialog == null) {
            delDialog = new DelStateDialog(this);
            delDialog.setOnConfirmClickListener(this);
        }
        delDialog.show();
        delDialog.setDialogInfo("确定删除消息？删除后不可恢复！");
    }

    @Override
    public void confirmClickListener() {
        String msgIds = msgListBean.sm_id + ",";
        presenter.sendNetDelMsg(key, msgIds);
    }

    @Override
    public void delMessageInfoResult(String result) {
        ToastUtil.showSuccess("删除成功！");
        adapter.remove(mPosition);
    }

    private void showMsgDialog(MsgBean.MsgDataBean.MsgListBean item) {
        SystemMsgDialog msgDialog = new SystemMsgDialog(this);
        msgDialog.show();
        msgDialog.setText(item.sm_content);
    }

    private void showToolbar() {
        ivActRightSetting.setVisibility(View.GONE);
        tvActTitle.setText("消息");
    }

    @Override
    protected void initListener() {
        tvMessageSystem.setOnClickListener(this);
        tvMessageInteract.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.cancelTag();
        switch (v.getId()) {
            case R.id.tvMessageSystem://系统消息
                tag = 0;
                break;
            case R.id.tvMessageInteract://互动消息
                tag = 1;
                break;
        }
        initAdapter();
        sendNet(true);
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);
    }
}
