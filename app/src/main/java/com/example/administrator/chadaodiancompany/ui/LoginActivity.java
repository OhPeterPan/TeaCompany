package com.example.administrator.chadaodiancompany.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.example.administrator.chadaodiancompany.MainActivity;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.presenter.LoginPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.DatabaseUtil;
import com.example.administrator.chadaodiancompany.util.SpUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.view.ImageEditText;
import com.example.administrator.chadaodiancompany.viewImpl.ILoginView;

import butterknife.BindView;

public class LoginActivity extends BaseToolbarActivity<LoginPresenter> implements View.OnClickListener, ILoginView, ImageEditText.IOnImageClickListener {
    @BindView(R.id.etLoginUsername)
    ImageEditText etLoginUsername;
    @BindView(R.id.etLoginPwd)
    ImageEditText etLoginPwd;
    @BindView(R.id.tvLoginButton)
    TextView tvLoginButton;
    @BindView(R.id.tvLoginVersionCode)
    TextView tvLoginVersionCode;

    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter(this);
    }

    @Override
    protected void initData() {

        initToolbar();
        tvLoginVersionCode.setText("版本号：" + AppUtils.getAppVersionName());
        Intent intent = getIntent();
        int flag = intent.getIntExtra(CommonUtil.FLAG, 0);
        DatabaseUtil.delAllDatabase();//登录时清空本地数据库
        if (flag == 1) {//遇到意外情况重新登录的
            ToastUtil.showError("请登录！");
        }
    }


    @Override
    protected void initToolbar() {
        toolbar.setVisibility(View.GONE);
        baseView.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        tvLoginButton.setOnClickListener(this);
        etLoginUsername.setOnImageClickListener(this);
        etLoginPwd.setOnImageClickListener(this);
    }

    @Override
    public void onImageClick(View v) {
        switch (v.getId()) {
            case R.id.etLoginUsername:
                etLoginUsername.setText("");
                break;
            case R.id.etLoginPwd:
                etLoginPwd.setText("");
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLoginButton:
                sendNet();
                break;
        }
    }

    private void sendNet() {
        String userName = etLoginUsername.getText().toString().trim();
        String pwd = etLoginPwd.getText().toString().trim();
        presenter.sendNetLogin(userName, pwd);
    }

    @Override
    public void getLoginResult(String result) {
        LogUtils.iTag("wak", "登录：" + result);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject datas = jsonObject.getJSONObject("datas");
        SpUtil.putString(SpUtil.KEY, datas.getString("key"));
        SpUtil.putString(SpUtil.STORE_NAME, datas.getString("store_name"));
        launchActivity(MainActivity.class);
        finish();
    }

    /**
     * 当解析json字符串里面含有"error"这个字段且返回"请登录"时，重新回到登录界面登录(很不常见的一种情况,最好别碰到)
     *
     * @param mContext
     */
    public static void launchNewFlag(Context mContext) {
        Intent in = new Intent(mContext, LoginActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        in.putExtra(CommonUtil.FLAG, 1);
        mContext.startActivity(in);
    }


}
