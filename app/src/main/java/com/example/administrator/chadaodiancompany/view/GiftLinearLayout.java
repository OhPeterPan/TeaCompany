package com.example.administrator.chadaodiancompany.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.OrderGoodBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class GiftLinearLayout extends LinearLayout {
    private Context context;//上下文环境引用

    public GiftLinearLayout(Context context) {
        this(context, null);
    }

    public GiftLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setDataList(List<OrderGoodBean> giftList) {
        if (giftList == null || giftList.size() == 0) return;
        removeAllViews();
        initViewWithAll(giftList);
    }

    /**
     * 初始化所有的View
     */
    private void initViewWithAll(List<OrderGoodBean> giftList) {
        for (int i = 0; i < giftList.size(); i++) {
            View commentView = getView(giftList.get(i));
            addView(commentView);
        }
    }

    private View getView(OrderGoodBean goodsCarGiftBean) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gift, this, false);
        TextView giftName = view.findViewById(R.id.tvItemGiftName);
        TextView giftNumber = view.findViewById(R.id.tvItemGiftNum);
        giftName.setText(goodsCarGiftBean.goods_name);
        giftNumber.setText("X" + goodsCarGiftBean.goods_num);
        return view;
    }
}
