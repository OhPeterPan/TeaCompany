package com.example.administrator.chadaodiancompany.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * create by wak 2016/9/23
 * 与scrollview同时存在时重新计算GridView的大小
 */
public class NestGridView extends GridView {

	public NestGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NestGridView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expendSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expendSpec);
	}
}
