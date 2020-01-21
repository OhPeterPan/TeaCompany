package com.example.administrator.chadaodiancompany.popupWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;

/**
 * Created by Administrator on 2018/4/11 0011.
 */

public class GoodsNamePopupWindow extends PopupWindow {
    private Context mContext;
    private TextView tvPopupWindowName;

    public GoodsNamePopupWindow(Context context) {
        super(context);
        this.mContext = context;
        initStyle();
    }

    private void initStyle() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layput_good_name_popup_window, null);
        tvPopupWindowName = view.findViewById(R.id.tvPopupWindowName);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        setBackgroundDrawable(colorDrawable);
        //setFocusable(true);不加它是因为需要一点击就马上显示
        setOutsideTouchable(true);
    }

    public void setGoodsName(String goodsName) {
        if (StringUtils.isEmpty(goodsName)) return;
        tvPopupWindowName.setText(goodsName);
    }

    public void showLocation(View v, int gravity, int x, int y) {
        showAtLocation(v, gravity, x, y);
    }
}
