package com.example.administrator.chadaodiancompany.ui.good;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.GoodChildClassifyAdapter;
import com.example.administrator.chadaodiancompany.adapter.GoodClassifyAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.GoodClassifyBean;
import com.example.administrator.chadaodiancompany.bean.GoodClassifyBean.GoodClassifyData.GoodsClassifyListBean;
import com.example.administrator.chadaodiancompany.bean.GoodClassifyBean.GoodClassifyData.GoodsClassifyListBean.GoodsChildClassify;
import com.example.administrator.chadaodiancompany.fragment.home.GoodFragment;
import com.example.administrator.chadaodiancompany.presenter.GoodClassifyPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IGoodClassifyView;

import java.util.List;

import butterknife.BindView;

public class GoodClassifyActivity extends BaseToolbarActivity<GoodClassifyPresenter> implements IGoodClassifyView {
    public static final int CLASS_RESULT_CODE = 1;
    @BindView(R.id.listViewOne)
    ListView listViewOne;
    @BindView(R.id.listViewTwo)
    ListView listViewTwo;
    private List<GoodsClassifyListBean> goodsClassifyList;
    private GoodClassifyAdapter classifyAdapter;
    private GoodChildClassifyAdapter goodChildClassifyAdapter;
    private List<GoodsChildClassify> goodChildClassifyList;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //invalidateOptionsMenu();  重新选择menu
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initPresenter() {
        presenter = new GoodClassifyPresenter(this);
    }

    @Override
    protected void initData() {
        ivActRightSetting.setVisibility(View.GONE);
        tvActTitle.setText("分类");
        sendNet();
    }

    private void sendNet() {
        presenter.sendNet(key);
    }

    @Override
    public void getGoodClassifySuccess(String result) {
        LogUtil.logI("商品分类：" + result);
        GoodClassifyBean goodClassifyBean = JSON.parseObject(result, GoodClassifyBean.class);
        goodsClassifyList = goodClassifyBean.datas.store_goods_class;
        if (goodsClassifyList != null && goodsClassifyList.size() != 0)
            setAdapter();
    }

    private void setAdapter() {
        classifyAdapter = new GoodClassifyAdapter(context, goodsClassifyList);
        goodChildClassifyList = goodsClassifyList.get(0).child;
        goodChildClassifyAdapter = new GoodChildClassifyAdapter(context, goodChildClassifyList);
        listViewOne.setAdapter(classifyAdapter);
        listViewTwo.setAdapter(goodChildClassifyAdapter);
    }

    @Override
    protected void initListener() {
        listViewOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (classifyAdapter == null || goodChildClassifyAdapter == null) return;
                classifyAdapter.notifyData(position);
                goodChildClassifyList = goodsClassifyList.get(position).child;
                goodChildClassifyAdapter.notifyData(goodChildClassifyList);
            }
        });
        listViewTwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (goodChildClassifyList == null) return;
                setIntentResult(goodChildClassifyList.get(position));
            }
        });
    }

    private void setIntentResult(GoodsChildClassify childBean) {
        Intent intent = new Intent();
        intent.putExtra(CommonUtil.STC_ID, childBean.stc_id);
        setResult(CLASS_RESULT_CODE, intent);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_classify;
    }

    public static void launch(Fragment fragment, Context mContext) {
        Intent intent = new Intent(mContext, GoodClassifyActivity.class);
        fragment.startActivityForResult(intent, GoodFragment.REQUEST_CODE);
    }


}
