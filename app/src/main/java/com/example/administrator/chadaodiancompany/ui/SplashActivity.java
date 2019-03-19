package com.example.administrator.chadaodiancompany.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.MainActivity;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.util.SpUtil;
import com.example.administrator.chadaodiancompany.view.TimeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements TimeTextView.AnimatorEndListener, View.OnClickListener {
    @BindView(R.id.timeTextView)
    TimeTextView timeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarVisibility(getWindow(), false);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
        if (!isTaskRoot()) {
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        }
    }

    private void initView() {
        timeTextView.setAnimatorEndListener(this);
        timeTextView.startAnim(3000);
        timeTextView.setOnClickListener(this);
    }

    @Override
    public void animatorEndListener() {
        Intent intent = new Intent();
        if (StringUtils.isEmpty(SpUtil.getString(SpUtil.KEY, ""))) {
            intent.setClass(this, LoginActivity.class);
        } else {
            intent.setClass(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeTextView.destroy();
    }

    @Override
    public void onClick(View v) {
        timeTextView.cancel();
        animatorEndListener();
    }
}
