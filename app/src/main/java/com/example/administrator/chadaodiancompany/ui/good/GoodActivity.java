package com.example.administrator.chadaodiancompany.ui.good;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.GoodIndexBean;
import com.example.administrator.chadaodiancompany.bean.GoodIndexBean.GoodDataBean.GoodsListBean;
import com.example.administrator.chadaodiancompany.dialog.IOSDialog;
import com.example.administrator.chadaodiancompany.dialog.InputGoodNumDialog;
import com.example.administrator.chadaodiancompany.image.ImageLoader;
import com.example.administrator.chadaodiancompany.presenter.GoodIndexPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.NumberUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.util.UIUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IGoodIndexView;

import java.util.List;

import butterknife.BindView;

public class GoodActivity extends BaseToolbarActivity<GoodIndexPresenter> implements IGoodIndexView, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, InputGoodNumDialog.OnConfirmNumberListener, View.OnClickListener {

    public static final int REQUEST_CODE = 0;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private GoodAdapter adapter;
    public boolean hasMore;
    public boolean isRefresh = true;
    public int curPage = 1;
    private String stcId = "";
    private String keyWord = "";
    private View headView;
    private TextView tvHeadGoodClassify;
    private TextView tvHeadGoodSearch;
    private CheckBox cbHeadAllGood;
    private TextView tvHeadGoodBan;
    private int mPosition;
    private GoodsListBean bean;
    private List<GoodsListBean> mData;
    private InputGoodNumDialog inputGoodNumDialog;
    private StringBuilder goodIds = new StringBuilder();
    private IOSDialog banGoodDialog;

    @Override
    protected void initPresenter() {
        presenter = new GoodIndexPresenter(this);
    }

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
    protected void initData() {
        ivActRightSetting.setImageResource(R.drawable.ic_search);
        tvActTitle.setText("商品");

        initHeadView();
        initAdapter();
        sendNet(true);
    }

    private void sendNet(boolean showDialog) {
        curPage = 1;
        isRefresh = true;
        presenter.sendNet(key, curPage, stcId, keyWord, showDialog);
    }

    private void initAdapter() {
        adapter = new GoodAdapter();
        adapter.isFirstOnly(true);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnLoadMoreListener(this, recyclerView);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(UIUtil.getDrawable(R.drawable.shape_divider));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemChildClickListener(this);
        adapter.removeAllHeaderView();
        adapter.addHeaderView(headView);
        adapter.setHeaderAndEmpty(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        presenter.sendNet(key, curPage, stcId, keyWord, false);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View v, int position) {
        GoodsListBean bean = (GoodsListBean) adapter.getItem(position);
        this.mPosition = position;
        if (bean == null) return;
        switch (v.getId()) {
            case R.id.llAdapterChooseGood://翻转选择
                chooseGood(position);
                break;
            case R.id.ivAdapterGoodDes://减
                goodNumberDes();
                break;
            case R.id.tvAdapterGoodNumber://数字
                showGoodStoreDialog(bean.goods_storage_sum);
                break;
            case R.id.ivAdapterGoodAdd://加
                goodNumberAdd();
                break;
            case R.id.tvAdapterGoodOut://下架
                goodIds.setLength(0);
                goodIds.append(bean.goods_commonid);
                showBanGoodDialog();
                break;
        }
    }

    private void showBanGoodDialog() {

        if (StringUtils.isEmpty(goodIds)) {
            ToastUtil.showError("请选择要下架的商品！");
            return;
        }
        if (banGoodDialog == null)
            banGoodDialog = new IOSDialog(this).builder().setCancelable(false).setTitle("商品下架")
                    .setMsg("是否将所有商品下架？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.sendNetBanGood(key, goodIds.toString());
                        }
                    });

        banGoodDialog.show();
    }

    private void showGoodStoreDialog(float number) {
        if (inputGoodNumDialog == null) {
            inputGoodNumDialog = new InputGoodNumDialog(this);
            inputGoodNumDialog.setOnConfirmListener(this);
        }
        inputGoodNumDialog.setNumber(number);
        inputGoodNumDialog.show();
    }

    @Override
    public void confirmNumber(float number) {
        bean.goods_storage_sum = number;
        presenter.sendNetChangeGoodNumber(key, bean.goods_commonid, number + "");
    }

    /**
     * 库存增加
     */
    private void goodNumberAdd() {
        bean.goods_storage_sum += 1;
        presenter.sendNetChangeGoodNumber(key, bean.goods_commonid, bean.goods_storage_sum + "");
    }

    /**
     * 减少
     */
    private void goodNumberDes() {
        bean.goods_storage_sum -= 1;
        if (bean.goods_storage_sum <= 0)
            bean.goods_storage_sum = 0;
        presenter.sendNetChangeGoodNumber(key, bean.goods_commonid, bean.goods_storage_sum + "");
    }

    private void chooseGood(int position) {
        bean.choose = !bean.choose;
        changeGoodState();
        adapter.notifyItemChanged(position + adapter.getHeaderLayoutCount());
    }

    private void changeGoodState() {
        if (adapter == null) return;
        mData = adapter.getData();
        //cbHeadAllGood.setChecked(true);
        for (int i = 0; i < mData.size(); i++) {
            bean = mData.get(i);
            if (!bean.choose) {
                cbHeadAllGood.setChecked(bean.choose);
                return;
            }
        }
        cbHeadAllGood.setChecked(true);
    }

    private void initHeadView() {
        headView = LayoutInflater.from(this).inflate(R.layout.head_good_list, null);
        tvHeadGoodClassify = headView.findViewById(R.id.tvHeadGoodClassify);
        tvHeadGoodSearch = headView.findViewById(R.id.tvHeadGoodSearch);
        cbHeadAllGood = headView.findViewById(R.id.cbHeadAllGood);
        tvHeadGoodBan = headView.findViewById(R.id.tvHeadGoodBan);
    }

    @Override
    protected void initListener() {
        ivActRightSetting.setOnClickListener(this);
        tvHeadGoodClassify.setOnClickListener(this);
        tvHeadGoodSearch.setOnClickListener(this);
        tvHeadGoodBan.setOnClickListener(this);
        swipeRefresh.setOnRefreshListener(this);
        cbHeadAllGood.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivActRightSetting://搜索
                GoodSearchActivity.launch(this, 0);
                break;
            case R.id.tvHeadGoodBan://下架
                goodIds.setLength(0);
                goodIds.append(banChooseGood());
                LogUtil.logI("选中的商品:" + goodIds);
                showBanGoodDialog();
                break;
            case R.id.cbHeadAllGood://是否全选
                chooseAllGood();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GoodSearchActivity.SEARCH_REQUEST_CODE) {
    /*        if (resultCode == GoodClassifyActivity.CLASS_RESULT_CODE) {//分类返回
                stcId = data.getStringExtra(CommonUtil.STC_ID);
                keyWord = "";
                sendNet(true);
            }*/
            if (resultCode == GoodSearchActivity.SEARCH_RESULT_CODE) {
                keyWord = data.getStringExtra(CommonUtil.KEYWORD);
                stcId = "";
                sendNet(true);
            }
        }
    }

    private String banChooseGood() {
        String result = "";
        if (adapter == null) return result;
        mData = adapter.getData();
        goodIds.setLength(0);
        for (int i = 0; i < mData.size(); i++) {
            bean = mData.get(i);
            if (bean.choose) {
                goodIds.append(bean.goods_commonid + ",");
            }
        }

        if (!StringUtils.isEmpty(goodIds) && goodIds.length() > 1)
            result = goodIds.substring(0, goodIds.length() - 1);

        goodIds.setLength(0);
        return result;
    }

    @Override
    public void onRefresh() {
        sendNet(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_good;
    }

    @Override
    public void showError(Throwable throwable) {
        super.showError(throwable);
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    @Override
    public void getGoodListSuccess(String result) {
        curPage++;

        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);

        GoodIndexBean goodIndexBean = JSON.parseObject(result, GoodIndexBean.class);
        hasMore = goodIndexBean.hasmore;
        if (isRefresh) {
            adapter.setNewData(goodIndexBean.datas.goods_list);
            chooseAllGood();
        } else {
            adapter.addData(goodIndexBean.datas.goods_list);
            cbHeadAllGood.setChecked(false);
        }
        if (adapter == null)
            return;
        if (adapter.isLoading() && hasMore) {
            adapter.loadMoreComplete();
        }

        if (!hasMore) {
            adapter.loadMoreEnd();
        }
    }

    private void chooseAllGood() {
        if (adapter == null) return;
        mData = adapter.getData();
        for (int i = 0; i < mData.size(); i++) {
            bean = mData.get(i);
            bean.choose = cbHeadAllGood.isChecked();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getBanGoodDetailSuccess(String result) {

    }

    @Override
    public void changeGoodNumberSuccess(String result) {

    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, GoodActivity.class);
        context.startActivity(intent);
    }

    private static class GoodAdapter extends BaseQuickAdapter<GoodsListBean, BaseViewHolder> {

        public GoodAdapter() {
            super(R.layout.adapter_good_index, null);
        }

        @Override
        protected void convert(BaseViewHolder holder, GoodsListBean item) {
            ImageLoader.with(mContext).url(item.goods_image).into(holder.getView(R.id.ivAdapterGoodsPic));
            holder.setText(R.id.cbAdapterGoodARTNO, "平台货号：" + item.goods_commonid);
            holder.setText(R.id.tvAdapterGoodTime, item.goods_addtime);
            holder.setText(R.id.tvAdapterGoodsName, item.goods_name);
            holder.setText(R.id.tvAdapterGoodsARTNO, "商家货号：" + item.goods_serial);
            holder.setText(R.id.tvAdapterGoodNumber, NumberUtil.replaceZero(item.goods_storage_sum) + "");
            holder.setChecked(R.id.cbAdapterChooseGood, item.choose);
            holder.addOnClickListener(R.id.llAdapterChooseGood);
            holder.addOnClickListener(R.id.ivAdapterGoodDes);
            holder.addOnClickListener(R.id.tvAdapterGoodNumber);
            holder.addOnClickListener(R.id.ivAdapterGoodAdd);
            holder.addOnClickListener(R.id.tvAdapterGoodOut);
        }
    }
}
