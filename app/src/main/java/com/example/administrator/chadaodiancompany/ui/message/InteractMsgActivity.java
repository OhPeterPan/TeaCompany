package com.example.administrator.chadaodiancompany.ui.message;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.view.ImageEditText;

import butterknife.BindView;

public class InteractMsgActivity extends BaseToolbarActivity implements ImageEditText.IOnImageClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.etInteractInfo)
    ImageEditText etInteractInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_interact_msg;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        etInteractInfo.setOnImageClickListener(this);
    }

    @Override
    public void onImageClick(View v) {
        LogUtil.logI("点击");
        if (StringUtils.isEmpty(etInteractInfo.getText().toString().trim())) {
            ToastUtil.showError("请输入要发送的内容！");
            return;
        }
        etInteractInfo.setText("");
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, InteractMsgActivity.class);
        context.startActivity(intent);
    }

}
