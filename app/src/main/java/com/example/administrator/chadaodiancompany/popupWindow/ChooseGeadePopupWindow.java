package com.example.administrator.chadaodiancompany.popupWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.BaseAdapter;
import com.example.administrator.chadaodiancompany.bean.MemberGradeBean.DatasBean.GradeListBean;

import java.util.List;

public class ChooseGeadePopupWindow extends PopupWindow implements BaseQuickAdapter.OnItemClickListener {

    private List<GradeListBean> gradeList;
    private Context mContext;
    private IOnChooseGradeListener listener;

    public ChooseGeadePopupWindow(Context context, List<GradeListBean> gradeList) {
        super(context);
        this.mContext = context;
        this.gradeList = gradeList;
        initPopupWindow();
        initPopupView();
    }

    private void initPopupView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_grade_layout, null);
        setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.shape_gray_line));
        GradeAdapter adapter = new GradeAdapter(R.layout.item_common, recyclerView, new LinearLayoutManager(mContext), itemDecoration);
        adapter.notifyData(gradeList);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        if (listener != null) {
            listener.getGradeListener((GradeListBean) ada.getItem(position));
            dismiss();
        }
    }

    private void initPopupWindow() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(00000000));
        setOutsideTouchable(true);
        setClippingEnabled(false);
    }

    public void show(View v) {
        setWidth(v.getWidth());
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //showAtLocation(v, Gravity.LEFT | Gravity.TOP, location[0], v.getHeight());
        //showAtLocation(v, Gravity.LEFT | Gravity.TOP, location[0], 0);
        showAsDropDown(v);
    }

    private class GradeAdapter extends BaseAdapter<GradeListBean> {

        public GradeAdapter(int layoutResId, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, RecyclerView.ItemDecoration decoration) {
            super(layoutResId, recyclerView, layoutManager, decoration);
        }

        @Override
        protected void bind(BaseViewHolder helper, GradeListBean item) {
            helper.setText(R.id.tvAdapterCommon, item.grade_name);
        }

        public void notifyData(List<GradeListBean> gradeList) {
            setNewData(gradeList);
        }
    }

    public void setOnGradeListener(IOnChooseGradeListener listener) {
        this.listener = listener;
    }

    public interface IOnChooseGradeListener {
        void getGradeListener(GradeListBean bean);
    }
}
