package com.example.administrator.chadaodiancompany.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.MemberGradeBean.DatasBean.GradeListBean;

import java.util.List;

public class MemberGradeDialog extends BottomSheetDialog implements BaseQuickAdapter.OnItemClickListener {

    private Context mContext;
    private List<GradeListBean> gradeList;
    private RecyclerView recyclerView;
    private IChooseGradeListener listener;

    public MemberGradeDialog(@NonNull Context context, List<GradeListBean> gradeList) {
        super(context, R.style.noDimDialogStyle);
        this.mContext = context;
        this.gradeList = gradeList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_member_grade);
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(-1, -2);
        recyclerView = findViewById(R.id.recyclerView);
        initAdapter();
    }

    private void initAdapter() {
        BaseQuickAdapter adapter = new BaseQuickAdapter<GradeListBean, BaseViewHolder>(R.layout.adapter_dialog_grade, gradeList) {

            @Override
            protected void convert(BaseViewHolder helper, GradeListBean item) {
                helper.setText(R.id.tvAdapterDialogGrade, item.grade_name);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    public void setOnChooseGradeListener(IChooseGradeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        dismiss();
        if (listener != null)
            listener.chooseGradeListener((GradeListBean) ada.getItem(position));
    }

    public interface IChooseGradeListener {
        void chooseGradeListener(GradeListBean item);
    }
}
