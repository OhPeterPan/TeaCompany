package com.example.administrator.chadaodiancompany.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.baseActivity.BaseToolbarActivity;
import com.example.administrator.chadaodiancompany.bean.AplListBean;
import com.example.administrator.chadaodiancompany.bean.ExpressBean;
import com.example.administrator.chadaodiancompany.event.MessageEvent;
import com.example.administrator.chadaodiancompany.presenter.ExpressManagerPresenter;
import com.example.administrator.chadaodiancompany.util.CommonUtil;
import com.example.administrator.chadaodiancompany.util.LogUtil;
import com.example.administrator.chadaodiancompany.util.ToastUtil;
import com.example.administrator.chadaodiancompany.viewImpl.IExpressManagerView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ExpressManagerActivity extends BaseToolbarActivity<ExpressManagerPresenter> implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, IExpressManagerView {
    @BindView(R.id.gbExpressChoose)
    RadioButton gbExpressChoose;
    @BindView(R.id.tvExpressDetail)
    TextView tvExpressDetail;
    @BindView(R.id.etExpressCode)
    EditText etExpressCode;
    @BindView(R.id.llExpressDetail)
    LinearLayout llExpressDetail;
    @BindView(R.id.gbExpressNoChoose)
    RadioButton gbExpressNoChoose;
    @BindView(R.id.tvAddressConfirm)
    TextView tvAddressConfirm;
    @BindView(R.id.rgExpress)
    RadioGroup rgExpress;
    private HashMap<String, String> hasMap;
    private List<AplListBean> aplList;
    private BottomSheetDialog bottomSheetDialog;
    private ExpressAdapter expressAdapter;
    private String expressId;
    private String expressCode;

    @Override
    protected void initPresenter() {
        presenter = new ExpressManagerPresenter(this);
    }

    @Override
    protected void initData() {
        tvActTitle.setText("设置发货");
        ivActRightSetting.setVisibility(View.GONE);
        initIntent();

    }

    private void initIntent() {
        Intent intent = getIntent();
        hasMap = (HashMap<String, String>) intent.getSerializableExtra(CommonUtil.SEND_GOOD_MAP);
    }

    @Override
    protected void initListener() {
        rgExpress.setOnCheckedChangeListener(this);
        tvExpressDetail.setOnClickListener(this);
        tvAddressConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvExpressDetail://选择快递
                if (aplList == null || aplList.size() == 0)
                    sendNetExpress();
                else
                    showBottomDialog();
                break;
            case R.id.tvAddressConfirm://确认发货
                confirmSendGood();
                break;
        }
    }

    private void confirmSendGood() {
        if (hasMap == null) {
            ToastUtil.showError("数据错误，请重新发货！");
            return;
        }
        if (gbExpressChoose.isChecked()) {
            expressCode = etExpressCode.getText().toString().trim();
            if (StringUtils.isEmpty(expressCode) || StringUtils.isEmpty(expressId)) {
                ToastUtil.showError("请完善物流信息！");
                return;
            }
        } else {
            expressId = "";
            expressCode = "";
        }
        hasMap.put("shipping_express_id", expressId);
        hasMap.put("shipping_code", expressCode);

        for (Map.Entry<String, String> entry : hasMap.entrySet()) {
            LogUtil.logI(entry.getKey() + "::::" + entry.getValue());
        }

        presenter.sendNetSendGood(key, hasMap);
    }

    private void showBottomDialog() {
        if (bottomSheetDialog == null) {
            bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setCanceledOnTouchOutside(false);
            bottomSheetDialog.setTitle("选择物流");
            bottomSheetDialog.setContentView(R.layout.dialog_bottom_list);
            RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.recyclerView);

            expressAdapter = new ExpressAdapter();
            expressAdapter.setNewData(aplList);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(expressAdapter);
            expressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    AplListBean aplListBean = expressAdapter.getItem(position);
                    tvExpressDetail.setText(aplListBean.e_name);
                    expressId = aplListBean.id;
                    bottomSheetDialog.dismiss();
                }
            });
        }
        bottomSheetDialog.show();
    }

    @Override
    public void getExpressDetailSuccess(String result) {
        LogUtil.logI("快递列表：" + result);
        ExpressBean expressBean = JSON.parseObject(result, ExpressBean.class);
        aplList = expressBean.datas.apl_list;
        showBottomDialog();
    }

    @Override
    public void sendGoodSuccess(String result) {
        LogUtil.logI("发送商品：" + result);
        ToastUtil.showSuccess("发货成功！");
        EventBus.getDefault().post(new MessageEvent("发货成功", CommonUtil.MessageEventCode.SEND_GOOD_CODE_SUCCESS));
        finish();
    }

    private void sendNetExpress() {
        if (hasMap == null) return;
        LogUtil.logI("orderId:" + hasMap.get("order_id"));
        presenter.sendNetGetExpressList(key, hasMap.get("order_id"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_express;
    }

    public static void launch(Context context, HashMap<String, String> paramsMap) {
        Intent intent = new Intent(context, ExpressManagerActivity.class);
        intent.putExtra(CommonUtil.SEND_GOOD_MAP, paramsMap);
        context.startActivity(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.gbExpressChoose:
                llExpressDetail.setVisibility(View.VISIBLE);
                expressId = "";
                etExpressCode.setText("");
                tvExpressDetail.setText("");
                break;
            case R.id.gbExpressNoChoose:
                llExpressDetail.setVisibility(View.GONE);
                break;
        }
    }

    private static class ExpressAdapter extends BaseQuickAdapter<AplListBean, BaseViewHolder> {

        public ExpressAdapter() {
            super(R.layout.adapter_express, null);
        }

        @Override
        protected void convert(BaseViewHolder holder, AplListBean item) {
            holder.setText(R.id.tvAdapterExpress, item.e_name);
        }
    }
}
