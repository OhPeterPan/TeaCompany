package com.example.administrator.chadaodiancompany.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.util.UIUtil;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18 0018.
 */

public class MagicAdapter extends CommonNavigatorAdapter {
    private List<String> list;
    private IOnPageChangeListener listener;

    public MagicAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        final BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
        SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
        simplePagerTitleView.setText(list.get(index));
        simplePagerTitleView.setTextSize(13);
        simplePagerTitleView.setNormalColor(UIUtil.getColor(R.color.backgroundRed));
        simplePagerTitleView.setSelectedColor(UIUtil.getColor(R.color.backgroundLightYellow));
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) return;
                listener.onPageChangeListener(index);
            }
        });
        badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

        return badgePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setColors(UIUtil.getColor(R.color.backgroundLightYellow));
        return indicator;
    }

    public void setOnPageChangeListener(IOnPageChangeListener listener) {
        this.listener = listener;

    }

    public void notifyData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface IOnPageChangeListener {
        void onPageChangeListener(int index);
    }
}
