package com.example.administrator.chadaodiancompany.ui.statistics;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.GoodAnalyseAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.GoodAnalyseBean;
import com.example.administrator.chadaodiancompany.presenter.GoodAnalysePresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.ExtraUtils;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.TimeUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IGoodAnalyseView;

import java.io.File;

import butterknife.BindView;

public class GoodAnalyseActivity extends BaseToolbarActivity<GoodAnalysePresenter> implements View.OnClickListener, IGoodAnalyseView,
        BaseQuickAdapter.RequestLoadMoreListener, RadioGroup.OnCheckedChangeListener,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.tvGoodsAnalyseDetailTime)
    TextView tvGoodsAnalyseDetailTime;
    @BindView(R.id.rbGoodsAnalyseSalesVolume)
    RadioButton rbGoodsAnalyseSalesVolume;
    @BindView(R.id.rbGoodsAnalyseSalesAmount)
    RadioButton rbGoodsAnalyseSalesAmount;
    @BindView(R.id.rgGoodsAnalyseState)
    RadioGroup rgGoodsAnalyseState;
    @BindView(R.id.tvGoodAnalyseTotal)
    TextView tvGoodAnalyseTotal;
    @BindView(R.id.tvGoodAnalyseCompare)
    TextView tvGoodAnalyseCompare;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String startTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH) + "-01";
    private String endTime = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH_DAY);
    private int tag = 3;//弹窗应该选择的条目(近一周、近一月、近三月)
    private boolean hasMore = false;
    private int curPage = 1;
    private boolean isRefresh = true;
    private GoodAnalyseAdapter adapter;
    private String type = "2";//1销量升序 2销量降序 3销售额升序 4销售额降序
    private String excelFileName;

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
        return R.layout.activity_good_analyse;
    }

    @Override
    protected void initPresenter() {
        presenter = new GoodAnalysePresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("商品分析");
        ivActRightSearch.setVisibility(View.VISIBLE);
        ivActRightSetting.setVisibility(View.GONE);
        tvGoodsAnalyseDetailTime.setText(startTime + " ~ " + endTime);
        initAdapter();
        sendNet(true);
    }

    private void initAdapter() {
        adapter = new GoodAnalyseAdapter(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recyclerView);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        GoodAnalyseBean.GoodAnalyseDatas.ListBean bean = adapter.getItem(position);
        SingleGoodAnalyseActivity.launch(this, bean.goods_id);
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
        presenter.sendNet(key, type, startTime, endTime, curPage, showDialog);
    }

    @Override
    public void getGoodAnalyseResult(String result) {
        curPage++;
        GoodAnalyseBean goodAnalyseBean = JSON.parseObject(result, GoodAnalyseBean.class);
        GoodAnalyseBean.GoodAnalyseDatas datas = goodAnalyseBean.datas;
        hasMore = goodAnalyseBean.hasmore;
        String sum = datas.sum;
        if (rbGoodsAnalyseSalesAmount.isChecked()) {
            tvGoodAnalyseTotal.setText("总计：" + sum + "件");
        } else {
            tvGoodAnalyseTotal.setText("总计：" + sum + "件");
        }

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
    public void downloadFileExcelSuccess(File file) {

        ToastUtil.showSuccess("下载完成，文件保存在" + file.getAbsolutePath());

        ExtraUtils.sendIntent(this, file);
    }

    @Override
    protected void initListener() {
        rgGoodsAnalyseState.setOnCheckedChangeListener(this);
        tvGoodsAnalyseDetailTime.setOnClickListener(this);
        ivActRightSearch.setOnClickListener(this);
        tvGoodAnalyseCompare.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        tvGoodAnalyseCompare.setSelected(false);
        switch (checkedId) {
            case R.id.rbGoodsAnalyseSalesVolume:
                type = "2";
                tvGoodAnalyseCompare.setText("销售数量");
                break;
            case R.id.rbGoodsAnalyseSalesAmount:
                type = "4";
                tvGoodAnalyseCompare.setText("销售额");
                break;
        }
        sendNet(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGoodsAnalyseDetailTime://时间选择
                GoodSearchTimeDialogActivity.launchResult(this, startTime, endTime, tag, 1);
                break;
            case R.id.ivActRightSearch://搜索
                StatisticSearchActivity.launch(this, 0);
                break;
            case R.id.tvGoodAnalyseCompare://销量或者销售额
                tvGoodAnalyseCompare.setSelected(!tvGoodAnalyseCompare.isSelected());
                LogUtil.logI("type:" + type);
                if (rbGoodsAnalyseSalesVolume.isChecked()) {

                    type = tvGoodAnalyseCompare.isSelected() ? "1" : "2";
                } else {
                    type = tvGoodAnalyseCompare.isSelected() ? "3" : "4";
                }
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

        if (resultCode == CommonUtil.RESULT_CODE_EXPORT) {//选择时间返回
            if (requestCode == CommonUtil.REQUEST_CODE) {
                if (data == null) return;
                tag = data.getIntExtra(CommonUtil.TAG, 3);
                startTime = data.getStringExtra(CommonUtil.START_TIME);
                endTime = data.getStringExtra(CommonUtil.END_TIME);
                excelFileName = "商品分析" + TimeUtil.getNowTime(TimeUtil.DEFAULT_FORMAT_CHINA) + ".xls";
                sendExportNet();
            }
        }
    }

    private void sendExportNet() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
            };

            if (hasPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                onPermissionSuccess();
            } else {
                requestPermissions(permissions, 111);
            }
            for (String str : permissions) {
                if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, 111);
                    break;
                }
            }
        }
    }

    private void onPermissionSuccess() {
        presenter.sendNetDownloadExcel(key, excelFileName, startTime, endTime);
    }

    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPer(grantResults)) {

                        presenter.sendNetDownloadExcel(key, excelFileName, startTime, endTime);

                    } else {
                        //  ToastUtil.showInfoLong("请前往设置页面所需权限");
                    }
                } else {
                    // Permission Denied
                    // ToastUtil.showInfoLong("请前往设置页面所需权限");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean checkPer(int[] grantResults) {

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
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
        tvGoodsAnalyseDetailTime.setText(startTime + " ~ " + endTime);
        sendNet(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.cancelTag();
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, GoodAnalyseActivity.class);
        context.startActivity(intent);
    }
}
