package com.example.administrator.chadaodiancompany.ui.member;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.presenter.MemberSettingPresenter;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberSettingView;
import com.suke.widget.SwitchButton;

import butterknife.BindView;

public class MemberSettingActivity extends BaseToolbarActivity<MemberSettingPresenter> implements IMemberSettingView, View.OnClickListener {

    @BindView(R.id.switchAutoAddMember)
    SwitchButton switchAutoAddMember;
    @BindView(R.id.etMemberSettingNumber)
    EditText etMemberSettingNumber;
    @BindView(R.id.etMemberSettingMoney)
    EditText etMemberSettingMoney;
    @BindView(R.id.switchIntegral)
    SwitchButton switchIntegral;
    @BindView(R.id.tvMemberSettingSave)
    TextView tvMemberSettingSave;

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
        return R.layout.activity_member_setting;
    }

    @Override
    protected void initPresenter() {
        presenter = new MemberSettingPresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("会员设置");
        ivActRightSetting.setVisibility(View.GONE);
        sendNet();
    }

    private void sendNet() {
        presenter.sendNetGetSetInfo(key);
    }

    @Override
    public void getMemberSettingInfoResult(String result) {
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject datas = jsonObject.getJSONObject("datas");
        JSONObject setting = datas.getJSONObject("setting");
        String up = setting.getString("up");
        String down = setting.getString("down");
        switchAutoAddMember.setChecked(StringUtils.equals("1", up));
        switchIntegral.setChecked(StringUtils.equals("1", down));
        etMemberSettingNumber.setText(setting.getString("num"));
        etMemberSettingMoney.setText(setting.getString("money"));
    }

    @Override
    public void getSaveMemberSettingInfoResult(String result) {
        ToastUtil.showSuccess("设置保存成功！");
        finish();
    }

    @Override
    protected void initListener() {
        tvMemberSettingSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMemberSettingSave:
                saveSetInfo();
                break;
        }
    }

    private void saveSetInfo() {
        String up = switchAutoAddMember.isChecked() ? "1" : "0";
        String down = switchIntegral.isChecked() ? "1" : "0";
        String num = etMemberSettingNumber.getText().toString().trim();
        String money = etMemberSettingMoney.getText().toString().trim();
        if (StringUtils.isEmpty(num)) {
            ToastUtil.showError("请输入消费次数！");
            return;
        }
        if (StringUtils.isEmpty(money)) {
            ToastUtil.showError("请输入消费金额！");
            return;
        }
        presenter.sendNetSaveInfo(key, up, num, money, down);
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MemberSettingActivity.class);
        ContextCompat.startActivity(context, intent, null);
    }


}
