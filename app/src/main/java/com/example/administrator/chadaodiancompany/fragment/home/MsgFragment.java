package com.example.administrator.chadaodiancompany.fragment.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.MsgBean;
import com.example.administrator.chadaodiancompany.bean.MsgBean.MsgDataBean.MsgListBean;
import com.example.administrator.chadaodiancompany.dialog.IOSDialog;
import com.example.administrator.chadaodiancompany.dialog.MsgDialog;
import com.example.administrator.chadaodiancompany.fragment.BaseFragment;
import com.example.administrator.chadaodiancompany.presenter.MsgPresenter;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.util.UIUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IMsgView;

import java.util.List;

import butterknife.BindView;

public class MsgFragment extends BaseFragment<MsgPresenter> implements IMsgView, View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.cbHeadAllMessage)
    CheckBox cbHeadAllMessage;
    @BindView(R.id.tvMsgTable)
    TextView tvMsgTable;
    @BindView(R.id.tvMsgDel)
    TextView tvMsgDel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private MsgBean.MsgDataBean msgDataBean;
    private MsgBean msgBean;
    private MsgListBean msgListBean;
    private int mPosition;
    private List<MsgListBean> mData;
    private MsgDialog msgDialog;
    private StringBuilder goodIds = new StringBuilder();
    private IOSDialog delMsgDialog;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initPresenter() {
        presenter = new MsgPresenter(this);
    }

    @Override
    protected void initData() {
        initAdapter();
        sendNet(true);
    }

    private void initAdapter() {
        adapter = new MsgAdapter();
        initAdapterParams(recyclerView, adapter);
        initLinearRecyclerView(recyclerView, adapter);
        adapter.setOnLoadMoreListener(this, recyclerView);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(UIUtil.getDrawable(R.drawable.shape_divider_big));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter a, View v, int position) {
        msgListBean = (MsgListBean) adapter.getItem(position);
        this.mPosition = position;
        if (msgListBean == null) return;
        switch (v.getId()) {
            case R.id.llAdapterChooseMsg://选择数据
                chooseMsgItem();
                break;
            case R.id.tvAdapterMsgContent://查看消息弹窗
                lookMsgContentDialog();
                break;
        }
    }

    private void lookMsgContentDialog() {
        if (TextUtils.equals("0", msgListBean.is_read))
            presenter.sendNetRedMsg(key, msgListBean.sm_id);
        else {
            showContentDialog();
        }
    }

    private void showContentDialog() {
        if (msgDialog == null)
            msgDialog = new MsgDialog(mContext);
        msgDialog.setMessage(msgListBean.sm_content);
        msgDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (msgDialog != null)
            msgDialog.destroy();
    }

    private void chooseMsgItem() {
        msgListBean.choose = !msgListBean.choose;
        checkAllChoose();
        adapter.notifyItemChanged(mPosition);
    }

    private void checkAllChoose() {
        if (adapter == null) return;
        mData = adapter.getData();
        for (int i = 0; i < mData.size(); i++) {
            if (!mData.get(i).choose) {
                cbHeadAllMessage.setChecked(false);
                return;
            }
        }
        cbHeadAllMessage.setChecked(true);
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        presenter.sendNetGetMsgList(key, curPage, false);
    }


    private void sendNet(boolean showDialog) {
        curPage = 1;
        isRefresh = true;
        presenter.sendNetGetMsgList(key, curPage, showDialog);
    }

    @Override
    protected void initListener() {
        cbHeadAllMessage.setOnClickListener(this);
        tvMsgTable.setOnClickListener(this);
        tvMsgDel.setOnClickListener(this);
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbHeadAllMessage://是否全选
                chooseAllMsg();
                break;
            case R.id.tvMsgTable://标记为已读
                goodIds.setLength(0);
                goodIds.append(banChooseMsg());
                LogUtil.logI("选中的消息:" + goodIds);
                presenter.sendNetRedMsgTag(key, goodIds.toString());
                break;
            case R.id.tvMsgDel://删除消息
                goodIds.setLength(0);
                goodIds.append(banChooseMsg());
                LogUtil.logI("选中的消息:" + goodIds);
                showDelMsgDialog();
                break;
        }
    }

    private void showDelMsgDialog() {

        if (StringUtils.isEmpty(goodIds)) {
            ToastUtil.showError("请选择要删除的消息！");
            return;
        }
        if (delMsgDialog == null)
            delMsgDialog = new IOSDialog(mContext).builder().setCancelable(false).setTitle("消息删除")
                    .setMsg("是否将删除选择消息？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.sendNetDeleteMsg(key, goodIds.toString());
                        }
                    });

        delMsgDialog.show();
    }

    private String banChooseMsg() {
        String result = "";
        if (adapter == null) return result;
        mData = adapter.getData();
        goodIds.setLength(0);
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).choose) {
                goodIds.append(mData.get(i).sm_id + ",");
            }
        }

        if (!StringUtils.isEmpty(goodIds) && goodIds.length() > 1)
            result = goodIds.substring(0, goodIds.length() - 1);

        goodIds.setLength(0);
        return result;
    }

    private void chooseAllMsg() {
        if (adapter == null) return;
        mData = adapter.getData();
        for (int i = 0; i < mData.size(); i++) {
            mData.get(i).choose = cbHeadAllMessage.isChecked();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getMsgListSuccess(String result) {
        LogUtil.logI("消息列表：" + result);
        curPage++;
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
        msgBean = JSON.parseObject(result, MsgBean.class);
        hasMore = msgBean.hasmore;
        msgDataBean = msgBean.datas;
        if (isRefresh) {
            adapter.setNewData(msgDataBean.msg_list);
            chooseAllMsg();
        } else {
            adapter.addData(msgDataBean.msg_list);
            cbHeadAllMessage.setChecked(false);
        }
        changeAdapterState();
    }

    @Override
    public void showError(Throwable throwable) {
        super.showError(throwable);
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    @Override
    public void getMsgReadSuccess(String result) {
        LogUtil.logI("查看详情：" + result);
        showContentDialog();
        msgListBean.is_read = "1";
        adapter.notifyItemChanged(mPosition);
    }

    @Override
    public void getMsgReadTagSuccess(String result) {
        ToastUtil.showSuccess("消息标记已读成功！");
        cbHeadAllMessage.setChecked(false);
        swipeRefresh.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void deleteMsgSuccess(String result) {
        ToastUtil.showSuccess("消息删除成功！");
        cbHeadAllMessage.setChecked(false);
        swipeRefresh.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        sendNet(false);
    }

    private static class MsgAdapter extends BaseQuickAdapter<MsgListBean, BaseViewHolder> {

        public MsgAdapter() {
            super(R.layout.adapter_msg, null);
        }

        @Override
        protected void convert(BaseViewHolder holder, MsgListBean item) {
            holder.setChecked(R.id.cbAdapterMsg, item.choose);
            holder.setText(R.id.tvAdapterMsgTime, item.sm_addtime);
            holder.setText(R.id.tvAdapterMsgContent, item.sm_content);
            holder.addOnClickListener(R.id.llAdapterChooseMsg);
            holder.addOnClickListener(R.id.tvAdapterMsgContent);
            if (TextUtils.equals("1", item.is_read)) {
                holder.setTextColor(R.id.tvAdapterMsgTime, UIUtil.getColor(R.color.textLightColor));
                holder.setTextColor(R.id.tvAdapterMsgContent, UIUtil.getColor(R.color.textLightColor));
            } else {
                holder.setTextColor(R.id.tvAdapterMsgTime, UIUtil.getColor(R.color.textDarkColor));
                holder.setTextColor(R.id.tvAdapterMsgContent, UIUtil.getColor(R.color.textDarkColor));
            }
        }
    }

}
