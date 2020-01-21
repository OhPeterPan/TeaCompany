package com.example.administrator.chadaodiancompany.ui.statistics;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.HistoryBean;
import com.example.administrator.chadaodiancompany.bean.StatisticSearchBean;
import com.example.administrator.chadaodiancompany.bean.StatisticSearchBean.StatisticSearchDatas.GoodsClass;
import com.example.administrator.chadaodiancompany.presenter.StatisticSearchPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IStatisticSearchView;
import com.zhy.view.flowlayout.FlowLayout;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;

public class StatisticSearchActivity extends BaseToolbarActivity<StatisticSearchPresenter> implements View.OnClickListener, TextView.OnEditorActionListener, IStatisticSearchView {
    @BindView(R.id.ivStatisticBack)
    ImageView ivStatisticBack;
    @BindView(R.id.etStatisticSearch)
    EditText etStatisticSearch;
    @BindView(R.id.tvStatisticSearchCancel)
    TextView tvStatisticSearchCancel;
    @BindView(R.id.tvOpenClose)
    TextView tvOpenClose;
    @BindView(R.id.flowGoodGroups)
    FlowLayout flowGoodGroups;
    @BindView(R.id.flowGoodHistory)
    FlowLayout flowGoodHistory;
    @BindView(R.id.clearGoodHistory)
    TextView clearGoodHistory;
    @BindView(R.id.flOpenClose)
    FrameLayout flOpenClose;
    @BindView(R.id.ivOpenClose)
    ImageView ivOpenClose;
    private List<HistoryBean> historyList;
    private int flag;
    private boolean isOpen = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_statistic_search;
    }

    @Override
    protected void initPresenter() {
        presenter = new StatisticSearchPresenter(this);
    }

    @Override
    protected void initData() {
        toolbar.setVisibility(View.GONE);
        baseView.setVisibility(View.GONE);
        flag = getIntent().getIntExtra(CommonUtil.FLAG, 0);
        historyList = DataSupport.findAll(HistoryBean.class);
        sendNet();
    }

    private void sendNet() {
        presenter.sendNet(key);
    }

    @Override
    public void getHistoryResult(String result) {
        StatisticSearchBean statisticSearchBean = JSON.parseObject(result, StatisticSearchBean.class);
        StatisticSearchBean.StatisticSearchDatas datas = statisticSearchBean.datas;
        List<GoodsClass> goodsClass = datas.goods_class;
        initFlowLayout(goodsClass);
    }

    private void initFlowLayout(List<GoodsClass> goodsClass) {

        if (goodsClass == null || goodsClass.size() == 0)
            return;
        flowGoodGroups.removeAllViews();
        for (int i = 0; i < goodsClass.size(); i++) {
            final TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_orange_flow, flowGoodGroups, false);
            textView.setText(goodsClass.get(i).stc_name);
            final String stcId = goodsClass.get(i).stc_id;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initIntent("", stcId);
                }
            });
            flowGoodGroups.addView(textView, i);
        }

        if (historyList == null || historyList.size() == 0)
            return;
        flowGoodHistory.removeAllViews();
        for (int i = 0; i < historyList.size(); i++) {
            final TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_gray_range, flowGoodHistory, false);
            textView.setText(historyList.get(i).keyWord);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String keyword = textView.getText().toString().trim();
                    initIntent(keyword, "");
                }
            });
            flowGoodHistory.addView(textView, i);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (flowGoodGroups != null && flOpenClose != null) {
                int measuredHeight = flowGoodGroups.getMeasuredHeight();
                if (measuredHeight >= SizeUtils.dp2px(190)) {
                    flOpenClose.setVisibility(View.VISIBLE);
            /*        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) flowGoodGroups.getLayoutParams();
                    lp.height = SizeUtils.dp2px(190);
                    flowGoodGroups.requestLayout();*/
                    isOpen = true;
                    executeAnimator();
                }
            }
        }
    }

    private void initIntent(String keyword, String stcId) {
        if (flag == 0)
            SearchDetailActivity.launch(this, keyword, stcId);
        else {
            Intent intent = new Intent(this, SearchDetailActivity.class);
            intent.putExtra(CommonUtil.KEYWORD, keyword);
            intent.putExtra(CommonUtil.STC_ID, stcId);
            setResult(0x00001111, intent);
        }
        finish();
    }

    @Override
    protected void initListener() {
        clearGoodHistory.setOnClickListener(this);
        etStatisticSearch.setOnEditorActionListener(this);
        ivStatisticBack.setOnClickListener(this);
        flOpenClose.setOnClickListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String keyword = etStatisticSearch.getText().toString().trim();
            if (StringUtils.isEmpty(keyword)) {
                ToastUtil.showError("请输入要查询的数据！");
                return false;
            }
            saveDatabase(keyword);
        }
        return false;
    }

    private void saveDatabase(String keyword) {
        if (!checkData(keyword) && !StringUtils.isEmpty(keyword)) {
            HistoryBean historyBean = new HistoryBean();
            historyBean.keyWord = keyword;
            historyBean.save();
        }
        initIntent(keyword, "");
    }

    /**
     * 判断数据库中是否存在这个数据
     *
     * @param keyword
     * @return
     */
    public boolean checkData(String keyword) {
        if (historyList == null)
            return false;
        for (int i = 0; i < historyList.size(); i++) {
            if (TextUtils.equals(keyword, historyList.get(i).keyWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearGoodHistory://清除所有记录
                if (historyList != null) {
                    historyList.clear();
                    DataSupport.deleteAll(HistoryBean.class);
                    flowGoodHistory.removeAllViews();
                }
                break;
            case R.id.ivStatisticBack:
            case R.id.tvStatisticSearchCancel:
                finish();
                break;
            case R.id.flOpenClose://关闭或者开启
                executeAnimator();
                break;
        }
    }

    private void executeAnimator() {
        int startHeight;
        int targetHeight;

        if (isOpen) {
            // 当前是打开 ---> 关闭
            isOpen = false;
            startHeight = getTrueFlowHeight();
            targetHeight = SizeUtils.dp2px(190);// 7行 的高度
        } else {
            // 当前是关闭 -----> 打开
            isOpen = true;
            startHeight = SizeUtils.dp2px(190);
            targetHeight = getTrueFlowHeight();
        }

        ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int height = (Integer) animator.getAnimatedValue();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) flowGoodGroups.getLayoutParams();
                lp.height = height;
                // 重新测量 重新绘制
                flowGoodGroups.requestLayout();
            }
        });

        // 监听动画的执行
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                if (isOpen) {
                    //结束后   打开
                    ivOpenClose.setImageResource(R.drawable.arrow_up);
                    tvOpenClose.setText("点击关闭");
                } else {
                    //结束后是关闭
                    ivOpenClose.setImageResource(R.drawable.arrow_down);
                    tvOpenClose.setText("点击查看更多");
                }
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    private int getTrueFlowHeight() {
        int measuredWidth = flowGoodGroups.getMeasuredWidth();
        int measureWidthSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY);
        int measureHeightSpec = View.MeasureSpec.makeMeasureSpec(20000, View.MeasureSpec.UNSPECIFIED);
        flowGoodGroups.measure(measureWidthSpec, measureHeightSpec);
        return flowGoodGroups.getMeasuredHeight();
    }

    /**
     * @param context
     * @param flag    ==0直接跳向搜索详情主页面   ==1回到搜索详情主页面
     */
    public static void launch(Context context, int flag) {
        Intent intent = new Intent(context, StatisticSearchActivity.class);
        intent.putExtra(CommonUtil.FLAG, flag);
        context.startActivity(intent);
    }
}
