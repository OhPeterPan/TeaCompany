package com.example.administrator.chadaodiancompany.fragment.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.StatisticIndexBean;
import com.example.administrator.chadaodiancompany.bean.StatisticIndexBean.StatisticDataBean.StatisticList;
import com.example.administrator.chadaodiancompany.chart_formatter.LinearChart;
import com.example.administrator.chadaodiancompany.fragment.BaseFragment;
import com.example.administrator.chadaodiancompany.presenter.StatisticIndexPresenter;
import com.example.administrator.chadaodiancompany.ui.statistics.GoodAnalyseActivity;
import com.example.administrator.chadaodiancompany.ui.statistics.GoodsAnalyseActivity;
import com.example.administrator.chadaodiancompany.ui.statistics.StatisticsSellActivity;
import com.example.administrator.chadaodiancompany.viewImpl.IStatisticIndexView;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StatisticsFragment extends BaseFragment<StatisticIndexPresenter> implements RadioGroup.OnCheckedChangeListener, IStatisticIndexView, View.OnClickListener {
    @BindView(R.id.rbStatisticsWeek)
    RadioButton rbStatisticsWeek;
    @BindView(R.id.rbStatisticsMonth)
    RadioButton rbStatisticsMonth;
    @BindView(R.id.rbStatisticsQuarter)
    RadioButton rbStatisticsQuarter;
    @BindView(R.id.rgStatistics)
    RadioGroup rgStatistics;
    @BindView(R.id.tvWeekRevenue)
    TextView tvWeekRevenue;
    @BindView(R.id.tvDayBig)
    TextView tvDayBig;
    @BindView(R.id.tvDayAvg)
    TextView tvDayAvg;
    @BindView(R.id.tvStatisticSell)
    TextView tvStatisticSell;
    @BindView(R.id.tvStatisticGood)
    TextView tvStatisticGood;
    @BindView(R.id.flStatistic)
    FrameLayout flStatistic;
    private String searchType = "day6";
    private ArrayList<Float> yValues;
    private List<String> xValues;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.frag_statistics;
    }

    @Override
    protected void initPresenter() {
        presenter = new StatisticIndexPresenter(this);
    }

    @Override
    protected void initData() {
        sendNet();
    }

    private void sendNet() {
        presenter.sendNet(key, searchType);
    }

    @Override
    protected void initListener() {
        rgStatistics.setOnCheckedChangeListener(this);
        tvStatisticSell.setOnClickListener(this);
        tvStatisticGood.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStatisticSell://销售统计
                startActivity(new Intent(getContext(), StatisticsSellActivity.class));
                break;
            case R.id.tvStatisticGood://商品分析
                  GoodAnalyseActivity.launch(getContext());
               // GoodsAnalyseActivity.launch(getContext());
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbStatisticsWeek://6天
                searchType = "day6";
                break;
            case R.id.rbStatisticsMonth://30天
                searchType = "day30";
                break;
            case R.id.rbStatisticsQuarter://90天
                searchType = "day90";
                break;
        }
        sendNet();
    }

    @Override
    public void getStatisticResult(String result) {
        StatisticIndexBean bean = JSON.parseObject(result, StatisticIndexBean.class);
        StatisticIndexBean.StatisticDataBean datas = bean.datas;
        List<StatisticList> list = datas.list;
        if (rbStatisticsWeek.isChecked())
            tvWeekRevenue.setText(fontStyle("¥ " + datas.total, "\n6天营收"));
        if (rbStatisticsMonth.isChecked())
            tvWeekRevenue.setText(fontStyle("¥ " + datas.total, "\n30天营收"));
        if (rbStatisticsQuarter.isChecked())
            tvWeekRevenue.setText(fontStyle("¥ " + datas.total, "\n90天营收"));
        tvDayBig.setText(fontStyle("¥ " + datas.daymax, "\n单日最高"));
        tvDayAvg.setText(fontStyle("¥ " + datas.avg, "\n日均"));
        initTest(list);
    }

    private void initTest(List<StatisticList> list) {
        flStatistic.removeAllViews();
        if (list == null) return;
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                yValues.add(Float.valueOf(list.get(i).total));
                xValues.add(list.get(i).time);
            }
        }
        LineChart lineChart = new LinearChart(getContext(), yValues, xValues).getLineChart();
        //flStatistic.removeAllViews();
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-1, -1);
        flStatistic.addView(lineChart, lp);
    }

    public static Spannable fontStyle(String start, String end) {
        SpannableString spa = new SpannableString(String.valueOf(start + end));
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(1.2f);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        spa.setSpan(relativeSizeSpan, 0, start.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spa.setSpan(styleSpan, 0, start.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spa;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelTag();
    }
}
