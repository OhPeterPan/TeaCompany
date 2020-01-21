package com.example.administrator.chadaodiancompany.chart_formatter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;

import com.blankj.utilcode.util.SizeUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinearChart {

    private LineChart lineChart;

    public LinearChart(Context context,
                       ArrayList<Float> yValues,
                       List<String> xValues) {
        lineChart = new LineChart(context);
  /*      Drawable drawables[] = new Drawable[]{
                ContextCompat.getDrawable(context, R.drawable.chart_statistic_white),
        };
        int[] colors = {Color.WHITE, Color.TRANSPARENT};*/
        initLineChart(yValues, xValues, null, null);
    }

    private void initLineChart(ArrayList<Float> yValues, List<String> xValues, Drawable[] drawables, int[] colors) {
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);

        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否可以拖动
        lineChart.setDragEnabled(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setExtraBottomOffset(5);
        lineChart.setExtraLeftOffset(SizeUtils.dp2px(10));
        lineChart.setNoDataText("暂无数据");
        lineChart.animateY(1000);

        XAxis xAxis = lineChart.getXAxis();
        // xAxis.setDrawGridLines(true);
        xAxis.setGridColor(Color.WHITE);
        xAxis.setGridDashedLine(new DashPathEffect(new float[]{5, 50}, 0));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum(xValues.size() - 0.5f);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setLabelCount(xValues.size(), false);
        xAxis.setTextSize(10);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);
        //设置x轴label
        xAxis.setValueFormatter(new XAxisValueFormatter(xValues));

        lineChart.getAxisRight().setEnabled(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setEnabled(false);
        yAxis.setDrawGridLines(true);
        yAxis.setGranularity(1f);
        yAxis.setLabelCount(6, true);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(Collections.max(yValues) + 1);
        yAxis.setDrawAxisLine(false);
        yAxis.setTextColor(Color.WHITE);
 /*       yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.format("%.2f", value);
            }
        });*/
        yAxis.setValueFormatter(new CustomFormatter());

        /***折线图例 标签 设置***/
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
        showLineChart(yValues, "交易数据趋势图", Color.WHITE, drawables, colors);
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
/*        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);*/
    }

    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public void showLineChart(List<Float> dataList, String name, int color, Drawable[] drawables, int[] colors) {
        List<Entry> entries = new ArrayList<>();
        Entry entry;
        for (int i = 0; i < dataList.size(); i++) {
            entry = new Entry(i, dataList.get(i));
            entries.add(entry);
        }

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.HORIZONTAL_BEZIER, drawables, colors);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            toggleFilled(drawables, null, false);
        } else {
            toggleFilled(null, colors, false);
        }
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode, Drawable[] drawables, int[] colors) {

        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        // lineDataSet.setDrawCircles(false);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        // lineDataSet.setDrawValues(false);
        lineDataSet.setHighlightEnabled(true);
        //设置折线图填充
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(13.f);
        lineDataSet.setValueTextColor(color);
        lineDataSet.setValueFormatter(new DefaultValueFormatter(2));
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }

    /**
     * <p>填充曲线以下区域</p>
     *
     * @param drawable    填充drawable
     * @param filledColor 填充颜色值
     * @param fill        true:填充
     */
    public void toggleFilled(Drawable[] drawable, int[] filledColor, boolean fill) {
        List<ILineDataSet> sets = lineChart.getData().getDataSets();

        for (int index = 0, len = sets.size(); index < len; index++) {
            LineDataSet set = (LineDataSet) sets.get(index);
            if (drawable != null) {
                set.setFillDrawable(drawable[index]);
            } else if (filledColor != null) {
                set.setFillColor(filledColor[index]);
            }
            set.setDrawFilled(fill);
        }
        lineChart.invalidate();
    }

    public LineChart getLineChart() {

        return lineChart;
    }
}
