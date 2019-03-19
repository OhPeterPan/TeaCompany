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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.MessageAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.dialog.SystemMsgDialog;

import java.util.LinkedList;

import butterknife.BindView;

public class MessageActivity extends BaseToolbarActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.tvMessageSystem)
    TextView tvMessageSystem;
    @BindView(R.id.tvMessageInteract)
    TextView tvMessageInteract;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private int tag = 0;//0代表系统消息  1代表显示互动消息
    private MessageAdapter adapter;

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

    }

    @Override
    protected void initData() {
        showToolbar();
        initAdapter();
    }

    private void initAdapter() {
        LinkedList<String> strs = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            strs.add("jdfsadfj");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(strs);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_gray_line));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (tag == 0) {//系统消息  弹窗
            showMsgDialog();
        } else if (tag == 1) {
            InteractMsgActivity.launch(this);
        }
    }

    private void showMsgDialog() {
        SystemMsgDialog msgDialog = new SystemMsgDialog(this);
        msgDialog.show();
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
        switch (v.getId()) {
            case R.id.tvMessageSystem://系统消息
                tag = 0;
                break;
            case R.id.tvMessageInteract://互动消息
                tag = 1;
                break;
        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);
    }
}
