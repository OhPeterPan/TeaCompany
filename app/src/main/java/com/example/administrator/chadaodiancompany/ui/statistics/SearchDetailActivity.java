package com.example.administrator.chadaodiancompany.ui.statistics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.SearchDetailAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.SearchDetailBean;
import com.example.administrator.chadaodiancompany.popupWindow.GoodsNamePopupWindow;
import com.example.administrator.chadaodiancompany.presenter.SearchDetailPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.TimeUtil;
import com.example.administrator.chadaodiancompany.view.ImageCenterTextView;
import com.example.administrator.chadaodiancompany.viewImpl.ISearchDetailView;

import butterknife.BindView;

public class SearchDetailActivity extends BaseToolbarActivity<SearchDetailPresenter> implements View.OnClickListener, ISearchDetailView, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.tvSearchGoodsAnalyseTime)
    TextView tvSearchGoodsAnalyseTime;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private int tag = 3;//弹窗应该选择的条目(近一周、近一月、近三月)
    private String startTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH) + "-01";
    private String endTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH_DAY);
    private String keyword;
    private String stcId;
    private boolean hasMore = false;
    private int curPage = 1;
    private boolean isRefresh = true;
    private String type = "2";
    private View headView;
    private ImageCenterTextView tvGoodSearchVolume;
    private ImageCenterTextView tvGoodSearchMoney;
    private TextView tvGoodSearchTotal;
    private TextView tvGoodSearchNum;
    private TextView tvGoodSearchVolumeMoney;
    private SearchDetailAdapter adapter;
    private GoodsNamePopupWindow popupWindow;
    private int[] location = new int[2];//记录控件在其父控件上的坐标位置  location[0] 左边距  location[1]  上边距
    private int top;

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
        return R.layout.activity_search_detail;
    }

    @Override
    protected void initPresenter() {
        presenter = new SearchDetailPresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("商品分析");
        ivActRightSearch.setVisibility(View.VISIBLE);
        ivActRightSetting.setVisibility(View.GONE);
        tvSearchGoodsAnalyseTime.setText(startTime + " ~ " + endTime);
        keyword = getIntent().getStringExtra(CommonUtil.KEYWORD);
        stcId = getIntent().getStringExtra(CommonUtil.STC_ID);
        initHeadView();
        initAdapter();
        sendNet(true);
    }

    private void initHeadView() {
        headView = LayoutInflater.from(this).inflate(R.layout.adapter_head_search_view, null);
        tvGoodSearchVolume = headView.findViewById(R.id.tvGoodSearchVolume);
        tvGoodSearchMoney = headView.findViewById(R.id.tvGoodSearchMoney);

        tvGoodSearchTotal = headView.findViewById(R.id.tvGoodSearchTotal);
        tvGoodSearchNum = headView.findViewById(R.id.tvGoodSearchNum);
        tvGoodSearchVolumeMoney = headView.findViewById(R.id.tvGoodSearchVolumeMoney);
        tvGoodSearchVolume.setOnClickListener(this);
        tvGoodSearchMoney.setOnClickListener(this);
    }

    private void initAdapter() {
        adapter = new SearchDetailAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setHeaderAndEmpty(true);
        adapter.addHeaderView(headView);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recyclerView);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter ada, View v, int position) {
        switch (v.getId()) {
            case R.id.ivAdapterSearchGoodPic:
                dismissPopupWindow();
                SearchDetailBean.SearchDetailDatas.SearchDetailListBean item = adapter.getItem(position);
                v.getLocationInWindow(location);//一个控件在其父控件上的坐标位置
                top = location[1];
                initShowGoodsPopupWindow(item.goods_name);
                break;
        }
    }

    private void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    private void initShowGoodsPopupWindow(String goodName) {
        if (popupWindow == null) popupWindow = new GoodsNamePopupWindow(this);
        popupWindow.setGoodsName(goodName);
        popupWindow.showLocation(recyclerView, Gravity.LEFT + Gravity.TOP, SizeUtils.dp2px(120), top);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissPopupWindow();
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        SearchDetailBean.SearchDetailDatas.SearchDetailListBean item = adapter.getItem(position);
        SingleGoodAnalyseActivity.launch(this, item.goods_id);
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        sendNet(false);
    }

    private void sendNet(boolean showDialog) {
        if (showDialog) {
            isRefresh = true;
            curPage = 1;
        }
        LogUtil.logI("keyword:" + keyword + ",stcId:" + stcId);
        presenter.sendNet(key, type, keyword, stcId, startTime, endTime, curPage, showDialog);
    }

    @Override
    public void getGoodInfoResult(String result) {
        SearchDetailBean searchDetailBean = JSON.parseObject(result, SearchDetailBean.class);
        hasMore = searchDetailBean.hasmore;
        SearchDetailBean.SearchDetailDatas datas = searchDetailBean.datas;
        // datas.
        tvGoodSearchTotal.setText("总计(" + datas.count + "件)");
        tvGoodSearchNum.setText(datas.count_goods_num);
        tvGoodSearchVolumeMoney.setText("¥ " + datas.count_goods_price);

        if (isRefresh) {
            adapter.setNewData(datas.list);
            adapter.setEmptyView(getEmptyView("暂无数据"));
        } else {
            adapter.addData(datas.list);
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
        tvSearchGoodsAnalyseTime.setOnClickListener(this);
        ivActRightSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSearchGoodsAnalyseTime:
                GoodSearchTimeDialogActivity.launchResult(this, startTime, endTime, tag);
                break;
            case R.id.tvGoodSearchVolume://销售量
                tvGoodSearchMoney.setSelected(false);
                tvGoodSearchVolume.setSelected(!tvGoodSearchVolume.isSelected());
                type = tvGoodSearchVolume.isSelected() ? "1" : "2";
                sendNet(true);
                break;
            case R.id.tvGoodSearchMoney://销售额
                tvGoodSearchVolume.setSelected(false);
                tvGoodSearchMoney.setSelected(!tvGoodSearchMoney.isSelected());
                type = tvGoodSearchMoney.isSelected() ? "3" : "4";
                sendNet(true);
                break;
            case R.id.ivActRightSearch://搜索
                Intent intent = new Intent(this, StatisticSearchActivity.class);
                intent.putExtra(CommonUtil.FLAG, 1);
                startActivityForResult(intent, 0x000001111);
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
        if (resultCode == 0x00001111) {//搜索返回
            if (requestCode == 0x000001111) {
                if (data == null) return;
                keyword = data.getStringExtra(CommonUtil.KEYWORD);
                stcId = data.getStringExtra(CommonUtil.STC_ID);
                sendNet(true);
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
        tvSearchGoodsAnalyseTime.setText(startTime + " ~ " + endTime);
        sendNet(true);
    }

    public static void launch(Context context, String keyword, String stcId) {
        Intent intent = new Intent(context, SearchDetailActivity.class);
        intent.putExtra(CommonUtil.KEYWORD, keyword);
        intent.putExtra(CommonUtil.STC_ID, stcId);
        context.startActivity(intent);
    }
}
