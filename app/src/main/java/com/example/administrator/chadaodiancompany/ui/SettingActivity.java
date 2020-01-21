package com.example.administrator.chadaodiancompany.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.UpdateAppBean;
import com.example.administrator.chadaodiancompany.dialog.UpdateInfoDialog;
import com.example.administrator.chadaodiancompany.manager.PermissionManager;
import com.example.administrator.chadaodiancompany.presenter.SettingPresenter;
import com.example.administrator.chadaodiancompany.util.SpUtil;
import com.example.administrator.chadaodiancompany.viewImpl.ISettingView;
import com.lzy.okgo.OkGo;

import butterknife.BindView;

public class SettingActivity extends BaseToolbarActivity<SettingPresenter> implements View.OnClickListener, ISettingView {
    @BindView(R.id.tvSettingName)
    TextView tvSettingName;
    @BindView(R.id.tvExitApp)
    TextView tvExitApp;

    @Override
    protected void initPresenter() {
        presenter = new SettingPresenter(this);
    }

    @Override
    protected void initData() {
        tvSettingName.setText(AppUtils.getAppVersionName());
        ivActRightSetting.setVisibility(View.GONE);
        tvActTitle.setText("设置");
    }

    @Override
    protected void initListener() {
        tvSettingName.setOnClickListener(this);
        tvExitApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSettingName://检测新版本
                checkNewUpdateApp();
                break;
            case R.id.tvExitApp:
                OkGo.getInstance().cancelAll();
                SpUtil.clearAll();
                LoginActivity.launchNewFlag(context);
                break;
        }
    }

    private void checkNewUpdateApp() {

        PermissionManager.getInstance().requestPermission(new Runnable() {
            @Override
            public void run() {
                presenter.sendNetUpdateApp();
            }
        }, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    @Override
    public void updateVersionResult(String result) {
        // LogUtil.logI("当前版本：" + result);
        UpdateAppBean updateAppBean = JSON.parseObject(result, UpdateAppBean.class);
        UpdateAppBean.UpdateDatasBean datas = updateAppBean.datas;
        if (!StringUtils.equals(datas.version_code, AppUtils.getAppVersionName())) {//不一样去更新
            UpdateInfoDialog updateInfoDialog = new UpdateInfoDialog(this, datas.version_info, datas.version_url);
            updateInfoDialog.show();
        }
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
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }
}
