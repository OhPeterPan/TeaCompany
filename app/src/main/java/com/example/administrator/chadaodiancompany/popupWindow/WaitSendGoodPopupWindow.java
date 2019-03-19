package com.example.administrator.chadaodiancompany.popupWindow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.util.UIUtil;

import java.util.Arrays;
import java.util.List;

public class WaitSendGoodPopupWindow extends PopupWindow implements BaseQuickAdapter.OnItemClickListener {
    private Context mContext;
    private View view;
    private RecyclerView recyclerView;
    private String state = "全部";//
    private List<String> orderStr = Arrays.asList(UIUtil.getResources().getStringArray(R.array.order_state));
    private OrderStateAdapter adapter;
    private OnPopupWindowClickListener listener;

    public WaitSendGoodPopupWindow(Context context) {
        super(context);
        this.mContext = context;
        initPopupWindow();
    }

    private void initPopupWindow() {
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_popup_window, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        setBackgroundDrawable(colorDrawable);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.popupAnim);
        initAdapter();
    }

    private void initAdapter() {
        adapter = new OrderStateAdapter(orderStr);
        adapter.isFirstOnly(true);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(UIUtil.getDrawable(R.drawable.shape_divider_gray));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void showPop(View v) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            v.getGlobalVisibleRect(rect);
            int h = ScreenUtils.getScreenHeight() - rect.bottom;
            setHeight(h);
        }
        showAsDropDown(v, 0, 1);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        if (listener == null) return;

        state = orderStr.get(position);
        switch (state) {
            case "未发货":
                listener.onClickStateListener(state, "1");
                break;
            case "只看一次发货":
                listener.onClickStateListener(state, "2");
                break;
            case "只看二次发货":
                listener.onClickStateListener(state, "3");
                break;
            case "只看三次发货":
                listener.onClickStateListener(state, "4");
                break;
            case "只看四次发货":
                listener.onClickStateListener(state, "5");
                break;
            case "未发完":
                listener.onClickStateListener(state, "6");
                break;
        }
        adapter.notifyDataSetChanged();
        dismiss();
    }

    public void setOnclickStateListener(OnPopupWindowClickListener listener) {
        this.listener = listener;
    }

    public void setOrderSate(String state) {
        this.state = state;
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    public interface OnPopupWindowClickListener {
        void onClickStateListener(String state, String orderState);
    }

    private class OrderStateAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public OrderStateAdapter(@Nullable List<String> data) {
            super(R.layout.adapter_class, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, String item) {
            TextView tvAdapterClassify = holder.getView(R.id.tvAdapterClassify);
            holder.setText(R.id.tvAdapterClassify, item);
            if (StringUtils.equals(state, item)) {
                tvAdapterClassify.setSelected(true);
            } else {
                tvAdapterClassify.setSelected(false);
            }
        }
    }
}
