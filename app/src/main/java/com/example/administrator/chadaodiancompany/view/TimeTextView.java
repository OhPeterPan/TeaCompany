package com.example.administrator.chadaodiancompany.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;

public class TimeTextView extends View {

    private int textSize;
    private Paint paint;
    private Context mContext;
    private Rect mRect = new Rect();
    private String text;
    private int duration;
    private ValueAnimator mValueAnimator;
    private AnimatorEndListener listener;
    private boolean isRunning = true;

    public TimeTextView(Context context) {
        this(context, null);
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.timeTextView, defStyleAttr, 0);
        int index;
        for (int i = 0; i < ta.getIndexCount(); i++) {
            index = ta.getIndex(i);
            if (index == R.styleable.timeTextView_text) {
                text = ta.getString(index);
            }
            if (index == R.styleable.timeTextView_duration) {
                duration = ta.getInt(index, 2);
            }
        }
        ta.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textSize = SizeUtils.sp2px(12);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        if (StringUtils.isEmpty(text))
            text = "2S 点击跳过";

        paint.getTextBounds(text, 0, text.length(), mRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int w = 0;
        int h = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            w = width;
        } else {
            w = getPaddingLeft() + getPaddingRight() + mRect.right - mRect.left;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            h = height;
        } else {
            h = getPaddingTop() + getPaddingBottom() + mRect.bottom - mRect.top;
        }

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, getPaddingLeft(), getMeasuredHeight() - getPaddingBottom(), paint);
    }

    public void startAnim(long time) {
        mValueAnimator = ValueAnimator.ofInt(duration, 0);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                duration = (int) animation.getAnimatedValue();
                text = duration + "S 点击跳过";
                postInvalidate();
            }
        });
        mValueAnimator.setDuration(time);
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.i("wak", "结束？");
                if (listener != null && isRunning) listener.animatorEndListener();
            }
        });
        if (isRunning)
            mValueAnimator.start();
    }

    public void cancel() {
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            isRunning = false;
            mValueAnimator.cancel();
        }
    }

    public void destroy() {
        isRunning = true;
        mValueAnimator = null;
    }

    public void setAnimatorEndListener(AnimatorEndListener listener) {
        this.listener = listener;
    }

    public interface AnimatorEndListener {
        void animatorEndListener();
    }
}
