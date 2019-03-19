package com.example.administrator.chadaodiancompany.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.chadaodiancompany.R;


public class ImageEditText extends android.support.v7.widget.AppCompatEditText {
    private Context mContext;
    private Bitmap bitmap;
    private float downX;
    private IOnImageClickListener listener;

    public ImageEditText(Context context) {
        this(context, null);
    }

    public ImageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ImageEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.del_icon);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                downX = event.getX();
                if (downX > getWidth() - bitmap.getWidth() - getPaddingRight()) {
                    if (listener != null)
                        listener.onImageClick(ImageEditText.this);
/*
                    setText("");*/
                }
                return false;
            }
        });
    }

    public void setOnImageClickListener(IOnImageClickListener listener) {
        this.listener = listener;
    }

    public interface IOnImageClickListener {
        void onImageClick(View v);
    }
}
