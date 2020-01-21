package com.example.administrator.chadaodiancompany.ui.statistics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.SingleGoodAnalyseAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.SingleGoodInfoBean;
import com.example.administrator.chadaodiancompany.presenter.SingleGoodAnalysePresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.TimeUtil;
import com.example.administrator.chadaodiancompany.viewImpl.ISingleGoodAnalyseView;

import butterknife.BindView;

public class SingleGoodAnalyseActivity extends BaseToolbarActivity<SingleGoodAnalysePresenter> implements ISingleGoodAnalyseView, View.OnClickListener {

    @BindView(R.id.tvSingleGoodsAnalyseTime)
    TextView tvSingleGoodsAnalyseTime;
    @BindView(R.id.ivSingleGoodsAnalysePic)
    ImageView ivSingleGoodsAnalysePic;
    @BindView(R.id.tvSingleGoodsName)
    TextView tvSingleGoodsName;
    @BindView(R.id.tvSingleGoodsVolume)
    TextView tvSingleGoodsVolume;
    @BindView(R.id.tvSingleGoodsAmount)
    TextView tvSingleGoodsAmount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String startTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH) + "-01";
    private String endTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH_DAY);
    private View headView;
    private SingleGoodAnalyseAdapter adapter;
    private String goodId;
    private int tag = 3;//弹窗应该选择的条目(近一周、近一月、近三月)

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
        return R.layout.activity_single_good_analyse;
    }

    @Override
    protected void initPresenter() {
        presenter = new SingleGoodAnalysePresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("单品分析");
        ivActRightSetting.setVisibility(View.GONE);
        tvSingleGoodsAnalyseTime.setText(startTime + " ~ " + endTime);
        goodId = getIntent().getStringExtra(CommonUtil.GOOD_ID);
        initHeadView();
        initAdapter();
        sendNet();
    }

    private void initHeadView() {
        headView = LayoutInflater.from(context).inflate(R.layout.adapter_head_single_view, null);
    }

    private void initAdapter() {
        adapter = new SingleGoodAnalyseAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setHeaderAndEmpty(true);
        adapter.addHeaderView(headView);
        recyclerView.setAdapter(adapter);
    }

    private void sendNet() {
        presenter.sendNet(key, goodId, startTime, endTime);
    }

    @Override
    public void getGoodInfoResult(String result) {
        SingleGoodInfoBean singleGoodInfoBean = JSON.parseObject(result, SingleGoodInfoBean.class);
        SingleGoodInfoBean.SingleDatas datas = singleGoodInfoBean.datas;
        Glide.with(this).asDrawable().load(datas.goods_info.goods_image).into(ivSingleGoodsAnalysePic);
        tvSingleGoodsName.setText(datas.goods_info.goods_name);
        tvSingleGoodsVolume.setText("销量：" + datas.goods_info.goods_num);
        tvSingleGoodsAmount.setText("销售额：¥ " + datas.goods_info.pay_price);
        adapter.setNewData(datas.order_goods);
    }

    @Override
    protected void initListener() {
        tvSingleGoodsAnalyseTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSingleGoodsAnalyseTime:
                GoodSearchTimeDialogActivity.launchResult(this, startTime, endTime, tag);
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
        tvSingleGoodsAnalyseTime.setText(startTime + " ~ " + endTime);
        sendNet();
    }

    public static void launch(Context context, String goodId) {
        Intent intent = new Intent(context, SingleGoodAnalyseActivity.class);
        intent.putExtra(CommonUtil.GOOD_ID, goodId);
        context.startActivity(intent);
    }


}
