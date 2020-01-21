package com.example.administrator.chadaodiancompany.ui.member;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.adapter.MemberIndexAdapter;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.MemberIndexBean;
import com.example.administrator.chadaodiancompany.bean.MemberIndexBean.MemberInfoBean;
import com.example.administrator.chadaodiancompany.bean.MemberListBean;
import com.example.administrator.chadaodiancompany.dialog.DelStateDialog;
import com.example.administrator.chadaodiancompany.dialog.FilterBottomDialog;
import com.example.administrator.chadaodiancompany.presenter.MemberIndexPresenter;
import com.example.administrator.chadaodiancompany.ui.good.GoodSearchActivity;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.MIMETypeUtils;
import com.example.administrator.chadaodiancompany.util.TimeUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.view.ImageCenterTextView;
import com.example.administrator.chadaodiancompany.viewImpl.IMemberIndexView;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class MemberIndexActivity extends BaseToolbarActivity<MemberIndexPresenter> implements View.OnClickListener, FilterBottomDialog.OnChooseMemberListener, BaseQuickAdapter.OnItemChildClickListener, IMemberIndexView, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, DelStateDialog.IOnConfirmClickListener {

    private static final int REQUEST_CODE = 0x000111;
    @BindView(R.id.tvMemberOrderNumber)
    TextView tvMemberOrderNumber;
    @BindView(R.id.tvMemberCount)
    TextView tvMemberCount;
    @BindView(R.id.tvMemberOrderGoodCount)
    TextView tvMemberOrderGoodCount;
    @BindView(R.id.tvMemberOrderGoodMoney)
    TextView tvMemberOrderGoodMoney;
    @BindView(R.id.tvMemberGrade)
    ImageCenterTextView tvMemberGrade;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private FilterBottomDialog dialog;
    private MemberIndexAdapter adapter;
    private boolean isRefresh = true;
    private int curPage = 1;
    private boolean hasMore = false;
    private String keyword = "";
    private DelStateDialog delDialog;
    private int position;
    private MemberListBean bean;
    private String sort = "2";

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
        return R.layout.activity_member_index;
    }

    @Override
    protected void initPresenter() {
        presenter = new MemberIndexPresenter(this);
    }

    @Override
    public void memberResultSucc(String result) {
        curPage++;
        MemberIndexBean memberIndexBean = JSON.parseObject(result, MemberIndexBean.class);
        hasMore = memberIndexBean.hasmore;
        MemberInfoBean memberInfoBean = memberIndexBean.datas;
        tvMemberCount.setText(fontStyle(memberInfoBean.member_count, "\n会员总数"));
        tvMemberOrderGoodCount.setText(fontStyle(memberInfoBean.year_amount, "\n今年订货额"));
        tvMemberOrderGoodMoney.setText(fontStyle(memberInfoBean.month_amount, "\n本月订货额"));
        List<MemberListBean> memberList = memberInfoBean.member_list;
        if (isRefresh) {
            adapter.setNewData(memberList);
            adapter.setEmptyView(getEmptyView("暂无数据"));
        } else {
            adapter.addData(memberList);
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
    protected void initData() {
        tvActTitle.setText("会员列表");
        ivActRightSetting.setImageResource(R.drawable.ic__member_classify_pic);
        initAdapter();
        sendNet(true);
    }

    private void sendNet(boolean showDialog) {
        if (showDialog) {
            curPage = 1;
            isRefresh = true;
        }
        presenter.sendNet(key, keyword, sort, curPage, showDialog);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemberIndexAdapter();
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_gray_line));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnLoadMoreListener(this, recyclerView);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {


    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        sendNet(false);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter ada, View v, int position) {
        this.position = position;
        bean = adapter.getItem(position);
        switch (v.getId()) {
            case R.id.tvAdapterMemberEdit://编辑会员
                MemberOperateActivity.launch(this, 0, bean, REQUEST_CODE);
                break;
            case R.id.tvAdapterMemberDel://删除会员
                showDelDialog();
                break;
        }
    }

    private void showDelDialog() {
        if (delDialog == null) {
            delDialog = new DelStateDialog(this);
            delDialog.setOnConfirmClickListener(this);
        }
        delDialog.show();
    }

    @Override
    public void confirmClickListener() {
        presenter.sendNetDelMember(key, bean.member_id);
    }

    @Override
    public void delMemberResultSucc(String result) {
        ToastUtil.showSuccess("会员删除成功！");
        adapter.remove(position);
    }

    @Override
    protected void initListener() {
        ivActRightSetting.setOnClickListener(this);
        tvMemberGrade.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivActRightSetting://筛选
                showFilterDialog();
                break;
            case R.id.tvMemberGrade:
                tvMemberGrade.setSelected(!tvMemberGrade.isSelected());
                sort = tvMemberGrade.isSelected() ? "1" : "2";
                sendNet(true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GoodSearchActivity.SEARCH_REQUEST_CODE) {
            if (resultCode == GoodSearchActivity.SEARCH_RESULT_CODE) {
                keyword = data.getStringExtra(CommonUtil.KEYWORD);
                sendNet(true);
            }
        }
        if (requestCode == REQUEST_CODE) {
            if (resultCode == MemberOperateActivity.RESULT_CODE) {
                sendNet(true);
            }
        }
    }

    @Override
    public void chooseMemberStateListener(int tag) {
        switch (tag) {
            case 0:
                GoodSearchActivity.launch(this, 2);
                break;
            case 1:
                MemberOperateActivity.launch(this, 1, null, REQUEST_CODE);
                break;
            case 2://导出会员
                outMemberExcel();
                break;
            case 3:
                MemberGradeActivity.launch(this);
                break;
            case 4://会员审核
                MemberAuditActivity.launch(this);
                break;
            case 5:
                MemberSettingActivity.launch(this);
                break;
        }
    }

    private void outMemberExcel() {
        presenter.sendNetGetFile(key, "excel", excelFileName);
    }

    String excelFileName = "公司会员" + TimeUtil.getNowTime(TimeUtil.DEFAULT_FORMAT_CHINA) + ".xls";

    @Override
    public void downloadFileExcelSucc(File f) {
        ToastUtil.showSuccess("下载完成，文件保存在" + f.getAbsolutePath());
        File file = new File(f.getAbsolutePath());
        Intent intent = new Intent();
        //intent.setAction(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String type = MIMETypeUtils.getMIMEType(file);

        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(context, "com.example.administrator.chadaodiancompany.fileProvider", file);
        } else {
            contentUri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(contentUri, type);
        intent.putExtra(Intent.EXTRA_STREAM, contentUri);
        Intent chooser = Intent.createChooser(intent, "发送文件");
        try {
            startActivity(chooser);
        } catch (Exception e) {
            LogUtil.logE(e);
            ToastUtil.showError("没有能打开文件的应用程序(wps、QQ、邮箱等应用程序)");
        }
    }

    private void showFilterDialog() {
        if (dialog == null) {
            dialog = new FilterBottomDialog(this);
            dialog.setOnChooseMemberStateListener(this);
        }
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.cancelTag();
    }

    public static Spannable fontStyle(String start, String end) {
        SpannableString spa = new SpannableString(String.valueOf(start + end));
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(1.2f);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        spa.setSpan(relativeSizeSpan, 0, start.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spa.setSpan(styleSpan, 0, start.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spa;
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MemberIndexActivity.class);
        context.startActivity(intent);
    }
}
