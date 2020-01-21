package com.example.administrator.chadaodiancompany.popupWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.BaseAdapter;
import com.example.administrator.chadaodiancompany.bean.PayMethodBean.PayMethodData.PayMethodListBean;

import java.util.List;

public class ChooseMoneyMethodPopupWindow extends PopupWindow implements BaseQuickAdapter.OnItemClickListener {
    private List<PayMethodListBean> list;
    private Context mContext;
    private RecyclerView recyclerView;
    private IOnChoosePayMethodListener listener;

    public ChooseMoneyMethodPopupWindow(Context context, List<PayMethodListBean> list) {
        super(context);
        this.mContext = context;
        this.list = list;
        initPopupWindow();
        initPopupView();
        initAdapter();
    }

    private void initAdapter() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.shape_gray_line));
        PayMethodAdapter adapter = new PayMethodAdapter(R.layout.item_common, recyclerView, new LinearLayoutManager(mContext), itemDecoration);
        adapter.notifyData(list);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        if (listener != null) {
            listener.getPayMethodListener((PayMethodListBean) ada.getItem(position));
            dismiss();
        }
    }

    private void initPopupView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_popup_pay_method_window, null);
        setContentView(view);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void initPopupWindow() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(00000000));
        setOutsideTouchable(true);
    }

    public void show(View v) {
        setWidth(v.getWidth());
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        showAtLocation(v, Gravity.LEFT | Gravity.TOP, location[0], location[1] + v.getHeight());
    }

    public void setOnGradeListener(IOnChoosePayMethodListener listener) {
        this.listener = listener;
    }

    public interface IOnChoosePayMethodListener {
        void getPayMethodListener(PayMethodListBean bean);
    }


    public class PayMethodAdapter extends BaseAdapter<PayMethodListBean> {

        public PayMethodAdapter(int layoutResId, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, RecyclerView.ItemDecoration decoration) {
            super(layoutResId, recyclerView, layoutManager, decoration);
        }

        @Override
        protected void bind(BaseViewHolder helper, PayMethodListBean item) {
            helper.setText(R.id.tvAdapterCommon, item.payment_name);
        }

        public void notifyData(List<PayMethodListBean> list) {
            setNewData(list);
        }
    }
}
