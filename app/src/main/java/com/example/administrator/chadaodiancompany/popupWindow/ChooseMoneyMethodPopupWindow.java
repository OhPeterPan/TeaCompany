package com.example.administrator.chadaodiancompany.popupWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.administrator.chadaodiancompany.R;

public class ChooseMoneyMethodPopupWindow extends PopupWindow {
    private Context mContext;

    public ChooseMoneyMethodPopupWindow(Context context) {
        super(context);
        this.mContext = context;
        initPopupWindow();
        initPopupView();
    }

    private void initPopupView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_popup_pay_method_window, null);
        setContentView(view);
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
}
