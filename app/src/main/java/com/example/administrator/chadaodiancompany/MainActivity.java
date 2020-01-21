package com.example.administrator.chadaodiancompany;

import android.content.Intent;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.fragment.FragmentFactory;
import com.example.administrator.chadaodiancompany.fragment.home.NewHomeFragment;
import com.example.administrator.chadaodiancompany.fragment.home.OrderFragment;
import com.example.administrator.chadaodiancompany.ui.SettingActivity;
import com.example.administrator.chadaodiancompany.ui.good.GoodSearchActivity;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class MainActivity extends BaseToolbarActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, NewHomeFragment.OnClickStateListener {
    @BindView(R.id.fragmentFrame)
    FrameLayout fragmentFrame;
    @BindView(R.id.rbHomeStore)
    RadioButton rbHomeStore;
    @BindView(R.id.rbOrderStore)
    RadioButton rbOrderStore;
    @BindView(R.id.rbShopStore)
    RadioButton rbShopStore;
    @BindView(R.id.rbMessageStore)
    RadioButton rbMessageStore;
    @BindView(R.id.rgHome)
    RadioGroup rgHome;
    private boolean isPressedBackOnce = false;
    private long firstTime = 0;
    private long secondTime = 0;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mBeginTransaction;
    private Fragment lastFragment;
    private Fragment fragment;
    public static final int REQUEST_CODE = 0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //invalidateOptionsMenu();  重新选择menu
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void onBackPressed() {
        if (!rbHomeStore.isChecked()) {
            rbHomeStore.setChecked(true);
            return;
        }
        if (isPressedBackOnce) {
            // 说明已经点了一次，这是第二次进入
            secondTime = System.currentTimeMillis();

            if (secondTime - firstTime > 2000) {
                // 说明上次点击作废
                ToastUtils.showShort("再点一次退出");
                isPressedBackOnce = true;
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                isPressedBackOnce = false;
                firstTime = 0;
                secondTime = 0;
                Process.killProcess(Process.myPid());
            }
        } else {
            ToastUtils.showShort("再点一次退出");
            isPressedBackOnce = true;
            firstTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        initToolbar(false);
        mFragmentManager = getSupportFragmentManager();
        tvActTitle.setText(storeName);
        checkIndex(0);
    }

    @Override
    protected void initListener() {
        rgHome.setOnCheckedChangeListener(this);
        ivActRightSetting.setOnClickListener(this);
        ivActRightSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivActRightSetting://设置
                SettingActivity.launch(context);
                break;
            case R.id.ivActRightSearch://搜索订单
                GoodSearchActivity.launch(context);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == GoodSearchActivity.SEARCH_RESULT_CODE) {//搜索返回
                fragment = mFragmentManager.findFragmentByTag("1");
                if (fragment instanceof OrderFragment) {
                    fragment.onActivityResult(OrderFragment.REQUEST_CODE, OrderFragment.KEYWORD_RESULT_CODE, data);
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbHomeStore:
                checkIndex(0);
                break;
            case R.id.rbOrderStore:
                checkIndex(1);
                break;
            case R.id.rbShopStore:
                checkIndex(2);
                break;
            case R.id.rbMessageStore:
                checkIndex(3);
                break;
        }
    }

    private void checkIndex(int index) {

        if (index == 1) {
            ivActRightSearch.setVisibility(View.VISIBLE);
            ivActRightSetting.setVisibility(View.GONE);
        } else {
            ivActRightSearch.setVisibility(View.GONE);
            ivActRightSetting.setVisibility(View.VISIBLE);
        }

        mBeginTransaction = mFragmentManager.beginTransaction();
        if (lastFragment != null)
            mBeginTransaction.hide(lastFragment);
        fragment = FragmentFactory.getFragment(index);
        if (fragment.isAdded()) {
            mBeginTransaction.show(fragment);
        } else {
            mBeginTransaction.add(R.id.fragmentFrame, fragment, index + "");
            if (index == 0) {
                ((NewHomeFragment) fragment).setOnClickStateListener(this);
            }
        }
        mBeginTransaction.commit();
        lastFragment = fragment;
        if (index == 2) {
            toolbar.setVisibility(View.GONE);
            baseView.setVisibility(View.GONE);
            ImmersionBar.with(this)
                    .statusBarColor(R.color.status_bar_color)
                    .statusBarDarkFont(true)
                    .init();
        } else {
            toolbar.setVisibility(View.VISIBLE);
            baseView.setVisibility(View.VISIBLE);
            ImmersionBar.with(this)
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true)
                    .init();
        }
    }

    @Override
    public void onClickStateListener(View v) {
        switch (v.getId()) {
            case R.id.tvMainWaitSendGood://待发货   tvFragHomeWaitSendGood
                fragment = mFragmentManager.findFragmentByTag("1");
                if (fragment == null) {
                    fragment = OrderFragment.newInstance("0");
                    FragmentFactory.putFragment(1, fragment);
                    rbOrderStore.setChecked(true);
                } else {
                    rbOrderStore.setChecked(true);
                    fragment.onActivityResult(OrderFragment.REQUEST_CODE, OrderFragment.HOME_STATE_RESULT_CODE, new Intent().putExtra(CommonUtil.TAG, "0"));
                }
                break;
            case R.id.tvFragHomeVending://出售中
                rbShopStore.setChecked(true);
                break;
            case R.id.tvMainWaitMoney://待付款//  tvFragHomeWaitPay
                fragment = mFragmentManager.findFragmentByTag("1");
                if (fragment == null) {
                    fragment = OrderFragment.newInstance("1");
                    FragmentFactory.putFragment(1, fragment);
                    rbOrderStore.setChecked(true);
                } else {
                    rbOrderStore.setChecked(true);
                    fragment.onActivityResult(OrderFragment.REQUEST_CODE, OrderFragment.HOME_STATE_RESULT_CODE, new Intent().putExtra(CommonUtil.TAG, "1"));
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentFactory.destroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


}
