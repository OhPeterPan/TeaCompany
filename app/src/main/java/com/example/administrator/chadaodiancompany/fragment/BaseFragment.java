package com.example.administrator.chadaodiancompany.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.NetDialog;
import com.example.administrator.chadaodiancompany.presenter.BasePresenter;
import com.example.administrator.chadaodiancompany.util.SpUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    public Context mContext;
    private Unbinder mBinder;
    public T presenter;
    private NetDialog netDialog;
    public String storeName;
    public String key;
    public BaseQuickAdapter adapter;
    public boolean hasMore;
    public boolean isRefresh = true;
    public int curPage = 1;

    public void changeAdapterState() {
        if (adapter == null)
            return;
        if (adapter.isLoading() && hasMore) {
            adapter.loadMoreComplete();
        }

        if (!hasMore) {
            adapter.loadMoreEnd();
        }
    }

    /**
     * @param recyclerView
     */
    public void initAdapterParams(RecyclerView recyclerView, BaseQuickAdapter adapter) {
        if (adapter == null || recyclerView == null)
            return;
        adapter.isFirstOnly(true);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    public void initLinearRecyclerView(RecyclerView recyclerView, BaseQuickAdapter adapter) {
        if (recyclerView == null || adapter == null)
            return;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.iTag("wak", getClass().getSimpleName() + "onCreate()");
        mContext = getActivity();
        netDialog = new NetDialog(mContext);
        key = SpUtil.getString(SpUtil.KEY, "");
        storeName = SpUtil.getString(SpUtil.STORE_NAME, "");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.iTag("wak", getClass().getSimpleName() + "onAttach()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.iTag("wak", getClass().getSimpleName() + "onCreateView()");
        View view = inflater.inflate(getFragmentLayoutId(), container, false);
        mBinder = ButterKnife.bind(this, view);
        initPresenter();
        initData();
        initListener();

        return view;
    }

    protected abstract int getFragmentLayoutId();

    protected abstract void initPresenter();

    protected abstract void initData();

    protected abstract void initListener();


    @Override
    public void onStart() {
        super.onStart();
        LogUtils.iTag("wak", getClass().getSimpleName() + "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.iTag("wak", getClass().getSimpleName() + "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.iTag("wak", getClass().getSimpleName() + "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.iTag("wak", getClass().getSimpleName() + "onStop()");
    }

    @Override
    public void onDestroyView() {//视图销毁
        super.onDestroyView();
        LogUtils.iTag("wak", getClass().getSimpleName() + "onDestroyView()");
    }

    @Override
    public void onDestroy() {//实例销毁
        super.onDestroy();
        if (mBinder != null)
            mBinder.unbind();

        if (presenter != null)
            presenter.detach();
        LogUtils.iTag("wak", getClass().getSimpleName() + "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.iTag("wak", getClass().getSimpleName() + "onDetach()");
    }

    public void showLoading() {
        if (NetworkUtils.isConnected()) {
            if (netDialog != null && !netDialog.isShowing()) {
                netDialog.show();
            }
        } else {
            ToastUtils.showShort("网络连接失败，请检查网络是否连接！");
        }
    }

    public void hideLoading() {
        if (netDialog != null && netDialog.isShowing()) {
            netDialog.dismiss();
        }
    }

    public void showError(Throwable throwable) {

        ToastUtil.showError("网络连接失败，请检查网络是否连接！");
        LogUtils.eTag("wak", throwable);
    }

}
