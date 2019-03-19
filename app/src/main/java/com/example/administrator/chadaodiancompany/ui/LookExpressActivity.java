package com.example.administrator.chadaodiancompany.ui;

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
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.ExpressAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.ExpressDetailBean;
import com.example.administrator.chadaodiancompany.bean.LazyResponse;
import com.example.administrator.chadaodiancompany.image.ImageLoader;
import com.example.administrator.chadaodiancompany.presenter.LookExpressPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.viewImpl.ILookExpressView;

import butterknife.BindView;

public class LookExpressActivity extends BaseToolbarActivity<LookExpressPresenter> implements ILookExpressView {

    @BindView(R.id.ivExpressGoodPic)
    ImageView ivExpressGoodPic;
    @BindView(R.id.tvExpressDetail)
    TextView tvExpressDetail;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String orderId;
    private String shopId;
    private int num;
    private ExpressAdapter adapter;
    private View emptyView;

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
    protected void initPresenter() {
        presenter = new LookExpressPresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("物流详情");
        ivActRightSetting.setVisibility(View.GONE);
        initAdapter();
        initIntent();
        sendNet();
    }

    private void initAdapter() {
        adapter = new ExpressAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    private void addEmptyView() {
        if (emptyView == null) {
            emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty, null);
            TextView tvNoData = emptyView.findViewById(R.id.tvNoData);
            tvNoData.setText("暂无物流信息");
            if (adapter != null) adapter.setEmptyView(emptyView);
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra(CommonUtil.ORDER_ID);
        String goodImage = intent.getStringExtra(CommonUtil.GOOD_IMAGE);
        shopId = intent.getStringExtra(CommonUtil.SHOP_ID);
        num = intent.getIntExtra(CommonUtil.SEND_GOOD_NUM, 0);
        ImageLoader.with(context)
                .placeHolder(R.mipmap.image_loading)
                .error(R.mipmap.image_load_error)
                .url(goodImage).into(ivExpressGoodPic);
    }

    private void sendNet() {
        LogUtil.logI("这里来吗？" + key + "::::" + orderId + ":::" + num);
        presenter.sendNetGetExpressList(key, orderId, num + "");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_look_express;
    }

    public static void launch(Context context, String orderId, String shopId, int num, String goodImage) {
        Intent intent = new Intent(context, LookExpressActivity.class);
        intent.putExtra(CommonUtil.ORDER_ID, orderId);
        intent.putExtra(CommonUtil.SHOP_ID, shopId);
        intent.putExtra(CommonUtil.GOOD_IMAGE, goodImage);
        intent.putExtra(CommonUtil.SEND_GOOD_NUM, num);
        context.startActivity(intent);
    }

    @Override
    public void getExpressListSuccess(String result) {
        LazyResponse lazyResponse = JSON.parseObject(result, LazyResponse.class);
        ExpressDetailBean bean = lazyResponse.datas;
        tvExpressDetail.setText("物流状态：" + bean.state + "\n物流公司：" + bean.context + "\n运单号码：" + bean.e_code);
        adapter.setNewData(bean.list);
        addEmptyView();
    }
}
