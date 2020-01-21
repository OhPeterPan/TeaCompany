package com.example.administrator.chadaodiancompany.ui.statistics;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.StatisticsSellAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.StatisticSellBean;
import com.example.administrator.chadaodiancompany.presenter.StatisticsSellPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.TimeUtil;
import com.example.administrator.chadaodiancompany.view.ImageCenterTextView;
import com.example.administrator.chadaodiancompany.viewImpl.IStatisticsSellView;

import java.util.List;

import butterknife.BindView;

public class StatisticsSellActivity extends BaseToolbarActivity<StatisticsSellPresenter> implements View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener, IStatisticsSellView {
    @BindView(R.id.tvGoodsSearchDetailTime)
    TextView tvGoodsSearchDetailTime;
    @BindView(R.id.tvGoodsSearchDetailTitle)
    TextView tvGoodsSearchDetailTitle;
    @BindView(R.id.ictMemberSellNum)
    ImageCenterTextView ictMemberSellNum;
    @BindView(R.id.ictMemberSellMoney)
    ImageCenterTextView ictMemberSellMoney;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String startTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH) + "-01";
    private String endTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH_DAY);
    private int tag = 3;//弹窗应该选择的条目(近一周、近一月、近三月)
    private boolean hasMore = false;
    private int curPage = 1;
    private boolean isRefresh = true;
    private StatisticsSellAdapter adapter;
    private String sort = "2";//1数量升序 2数量降序 3金额升序 4金额降序

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
        return R.layout.activity_statistic_sell;
    }

    @Override
    protected void initPresenter() {
        presenter = new StatisticsSellPresenter(this);
    }

    @Override
    protected void initData() {
        ivActRightSetting.setVisibility(View.GONE);
        tvActTitle.setText("销售统计");
        tvGoodsSearchDetailTime.setText(startTime + " ~ " + endTime);
        initAdapter();
        sendNet(true);
    }

    private void initAdapter() {
        adapter = new StatisticsSellAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_gray_line));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnLoadMoreListener(this, recyclerView);
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        sendNet(false);
    }

    private void sendNet(boolean showDialog) {
        if (showDialog) {
            curPage = 1;
            isRefresh = true;
        }
        LogUtil.logI("sort:" + sort + ",key:" + key + ",startTime:" + startTime + ",endTime:" + endTime);
        presenter.sendNet(key, curPage, sort, startTime, endTime, showDialog);
    }

    @Override
    public void getSellResult(String result) {
        curPage++;
        StatisticSellBean statisticSellBean = JSON.parseObject(result, StatisticSellBean.class);
        hasMore = statisticSellBean.hasmore;
        StatisticSellBean.StatisticSellData datas = statisticSellBean.datas;
        List<StatisticSellBean.StatisticSellData.StatisticSellList> orderList = datas.order_list;
        if (isRefresh) {
            adapter.setNewData(orderList);
            adapter.setEmptyView(getEmptyView("暂无数据"));
        } else {
            adapter.addData(orderList);
        }
        if (hasMore) isRefresh = false;

        if (adapter.isLoading() && hasMore) {
            adapter.loadMoreComplete();
        }
        if (!hasMore) {
            adapter.loadMoreEnd();
        }
    }

    @Override
    protected void initListener() {
        ictMemberSellNum.setOnClickListener(this);
        ictMemberSellMoney.setOnClickListener(this);
        tvGoodsSearchDetailTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGoodsSearchDetailTime://选择时间
                GoodSearchTimeDialogActivity.launchResult(this, startTime, endTime, tag);
                break;
            case R.id.ictMemberSellNum:
                ictMemberSellMoney.setSelected(false);
                ictMemberSellNum.setSelected(!ictMemberSellNum.isSelected());
                sort = ictMemberSellNum.isSelected() ? "1" : "2";
                sendNet(true);
                break;
            case R.id.ictMemberSellMoney:
                ictMemberSellNum.setSelected(false);
                ictMemberSellMoney.setSelected(!ictMemberSellMoney.isSelected());
                sort = ictMemberSellMoney.isSelected() ? "3" : "4";
                sendNet(true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CommonUtil.RESULT_CODE_COMMON) {//选择时间返回
            if (requestCode == CommonUtil.REQUEST_CODE) {
                if (data == null) return;
                tag = data.getIntExtra(CommonUtil.TAG, 3);
                checkTime(data);
            }
        }
    }

    private void checkTime(Intent intent) {
        switch (tag) {
            case 0://近一周
                endTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH_DAY);
                startTime = TimeUtil.getAppointDay(6, TimeUtil.YEAR_MONTH_DAY);
                break;
            case 1://近一月
                endTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH_DAY);
                startTime = TimeUtil.getTime(-1, TimeUtil.YEAR_MONTH_DAY);
                break;
            case 2://近三月
                endTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH_DAY);
                startTime = TimeUtil.getTime(-3, TimeUtil.YEAR_MONTH_DAY);
                break;
            case 3:
                startTime = intent.getStringExtra(CommonUtil.START_TIME);
                endTime = intent.getStringExtra(CommonUtil.END_TIME);
                break;
        }
        tvGoodsSearchDetailTime.setText(startTime + " ~ " + endTime);
        sendNet(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.cancelTag();
    }
}
