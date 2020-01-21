package com.example.administrator.chadaodiancompany.baseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.chadaodiancompany.NetDialog;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.event.MessageEvent;
import com.example.administrator.chadaodiancompany.presenter.BasePresenter;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.SpUtil;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public abstract class BaseToolbarActivity<T extends BasePresenter> extends AppCompatActivity {
    public Context context;
    private FrameLayout baseFrame;
    public Toolbar toolbar;
    public T presenter;
    public TextView tvActTitle;
    public ImageView ivActRightSetting;
    public ImageView ivActRightSearch;
    private NetDialog netDialog;
    protected String key;
    protected String storeName;
    public TextView tvTitleRight;
    public View baseView;
    private View emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setStateBar();
        context = this;
        netDialog = new NetDialog(context);
        initFrameView();
    }

    public void setStateBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init();
    }

    private void initFrameView() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        toolbar = findViewById(R.id.toolbar);
        baseView = findViewById(R.id.baseView);
        tvActTitle = findViewById(R.id.tvActTitle);
        ivActRightSetting = findViewById(R.id.ivActRightSetting);
        ivActRightSearch = findViewById(R.id.ivActRightSearch);
        baseFrame = findViewById(R.id.baseFrame);
        tvTitleRight = findViewById(R.id.tvTitleRight);
        initSpDetail();
        initToolbar();
        inflateFrameLayout();
    }

    private void initSpDetail() {
        key = SpUtil.getString(SpUtil.KEY, "");
        storeName = SpUtil.getString(SpUtil.STORE_NAME, "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        hookMessage(event);
    }

    //让子类自己实现
    public void hookMessage(MessageEvent event) {

    }

    protected void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void initToolbar(boolean show) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(show);
    }

    private void clearAllView() {
        if (baseFrame != null) baseFrame.removeAllViews();
    }

    public void inflateFrameLayout() {
        clearAllView();
        View view = LayoutInflater.from(context).inflate(getLayoutId(), baseFrame, false);
        baseFrame.addView(view);
        ButterKnife.bind(this, view);
        initPresenter();
        initData();
        initListener();
    }

    protected abstract int getLayoutId();

    protected abstract void initPresenter();

    protected abstract void initData();

    protected abstract void initListener();

    public void showLoading() {
        if (NetworkUtils.isConnected()) {
            if (netDialog != null && !netDialog.isShowing()) {
                netDialog.show();
            }
        } else {
            ToastUtils.showShort("网络连接失败，请检查网络是否连接！");
        }
    }

    public void hideLoading() {
        if (netDialog != null && netDialog.isShowing()) {
            netDialog.dismiss();
        }
    }

    public void showError(Throwable throwable) {
        LogUtils.eTag("wak", throwable);
        LogUtil.logI("错误了？");
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }

    public void launchActivity(Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }

    public View getEmptyView(String msg) {

        View emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty, null);
        TextView tvNoData = emptyView.findViewById(R.id.tvNoData);
        tvNoData.setText(msg);

        return emptyView;
    }

    //解决fragment崩溃重影问题
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.logI(getClass().getSimpleName() + "：：：：onSaveInstanceState()");
        outState.putParcelable("android:support:fragments", null);
    }
}
