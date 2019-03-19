package com.example.administrator.chadaodiancompany.ui;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.util.SpUtil;
import com.lzy.okgo.OkGo;

import butterknife.BindView;

public class SettingActivity extends BaseToolbarActivity implements View.OnClickListener {
    @BindView(R.id.tvSettingName)
    TextView tvSettingName;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        tvSettingName.setText(storeName);
        ivActRightSetting.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        tvSettingName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSettingName:
                OkGo.getInstance().cancelAll();
                SpUtil.clearAll();
                LoginActivity.launchNewFlag(context);
                break;
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
