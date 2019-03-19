package com.example.administrator.chadaodiancompany.fragment.home;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.fragment.BaseFragment;

import butterknife.BindView;

public class StatisticsFragment extends BaseFragment {
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

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.frag_statistics;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
