package com.example.administrator.chadaodiancompany.ui.good;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.MainActivity;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.HistoryBean;
import com.example.administrator.chadaodiancompany.fragment.home.GoodFragment;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;

public class GoodSearchActivity extends BaseToolbarActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    public static final int SEARCH_RESULT_CODE = 2;
    public static final int SEARCH_REQUEST_CODE = 0;
    @BindView(R.id.etGoodsSearch)
    EditText etGoodsSearch;
    @BindView(R.id.ivGoodsSearch)
    TextView ivGoodsSearch;
    @BindView(R.id.flowSearchLayout)
    FlowLayout flowSearchLayout;
    @BindView(R.id.clearGoodHistory)
    TextView clearGoodHistory;
    private List<HistoryBean> historyList;
    private String keyword;
    private int flag;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        flag = intent.getIntExtra(CommonUtil.FLAG, 0);
        toolbar.setVisibility(View.GONE);
        baseView.setVisibility(View.GONE);
        if (flag == 1) {
            etGoodsSearch.setHint("买家/商品名称/订单号");
        } else if (flag == 2) {
            etGoodsSearch.setHint("请输入公司名称/创建人");
        } else {
            etGoodsSearch.setHint("商品名称/商家货号/平台货号");
        }
        historyList = DataSupport.findAll(HistoryBean.class);
        initFlowLayout();
    }

    private void initFlowLayout() {
        if (historyList == null || historyList.size() == 0)
            return;

        flowSearchLayout.removeAllViews();
        for (int i = 0; i < historyList.size(); i++) {
            final TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_gray_range, flowSearchLayout, false);
            textView.setText(historyList.get(i).keyWord);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keyword = textView.getText().toString().trim();
                    initIntent(keyword);
                }
            });
            flowSearchLayout.addView(textView, i);
        }
    }

    private void initIntent(String keyWord) {
        Intent intent = new Intent();
        intent.putExtra(CommonUtil.KEYWORD, keyWord);
        setResult(SEARCH_RESULT_CODE, intent);
        finish();
    }

    @Override
    protected void initListener() {
        ivGoodsSearch.setOnClickListener(this);

        etGoodsSearch.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            keyword = etGoodsSearch.getText().toString().trim();
            if (StringUtils.isEmpty(keyword)) {
                ToastUtil.showError("请输入要查询的数据！");
                return false;
            }
            saveDatabase();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivGoodsSearch://取消
                finish();
                break;
            case R.id.clearGoodHistory://清空
                if (historyList != null) {
                    historyList.clear();
                    DataSupport.deleteAll(HistoryBean.class);
                    flowSearchLayout.removeAllViews();
                }
                break;
        }
    }

    private void saveDatabase() {
        if (!checkData() && !StringUtils.isEmpty(keyword)) {
            HistoryBean historyBean = new HistoryBean();
            historyBean.keyWord = keyword;
            historyBean.save();
        }
        initIntent(keyword);
    }

    /**
     * 判断数据库中是否存在这个数据
     *
     * @return
     */
    public boolean checkData() {
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
    protected int getLayoutId() {
        return R.layout.activity_good_search;
    }

    public static void launch(Fragment fragment, Context mContext) {
        Intent intent = new Intent(mContext, GoodSearchActivity.class);
        fragment.startActivityForResult(intent, GoodFragment.REQUEST_CODE);
    }

    public static void launch(Context mContext) {
        Intent intent = new Intent(mContext, GoodSearchActivity.class);
        intent.putExtra(CommonUtil.FLAG, 1);
        ((AppCompatActivity) mContext).startActivityForResult(intent, MainActivity.REQUEST_CODE);
    }

    public static void launch(Context mContext, int flag) {
        Intent intent = new Intent(mContext, GoodSearchActivity.class);
        intent.putExtra(CommonUtil.FLAG, flag);
        ((AppCompatActivity) mContext).startActivityForResult(intent, SEARCH_REQUEST_CODE);
    }
}
