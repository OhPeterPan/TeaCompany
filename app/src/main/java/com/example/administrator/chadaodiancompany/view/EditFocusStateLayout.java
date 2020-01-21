package com.example.administrator.chadaodiancompany.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.chadaodiancompany.R;
import com.example.administrator.chadaodiancompany.bean.SimpleTextChangedListener;

public class EditFocusStateLayout extends FrameLayout {
    private Context mContext;
    private Drawable norDrawable;
    private Drawable focusDrawable;
    private String rightText;
    private boolean isInteger;
    private LinearLayout llEditFocusState;
    private TextView tvEditStateLeft;
    private EditText etEditStateRight;
    private String textHint;

    public EditFocusStateLayout(@NonNull Context context) {
        this(context, null);
    }

    public EditFocusStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditFocusStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.editFocusState);
        norDrawable = ta.getDrawable(R.styleable.editFocusState_normal_background);
        focusDrawable = ta.getDrawable(R.styleable.editFocusState_focus_background);
        rightText = ta.getString(R.styleable.editFocusState_text_left);
        textHint = ta.getString(R.styleable.editFocusState_text_hint_more);
        isInteger = ta.getBoolean(R.styleable.editFocusState_is_integer, false);
        ta.recycle();
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.edit_focus_state, this, true);
        llEditFocusState = findViewById(R.id.llEditFocusState);
        tvEditStateLeft = findViewById(R.id.tvEditStateLeft);
        etEditStateRight = findViewById(R.id.etEditStateRight);
        etEditStateRight.setInputType(isInteger ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        tvEditStateLeft.setText(rightText);
        etEditStateRight.setHint(textHint);
        llEditFocusState.setBackground(norDrawable);
        etEditStateRight.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                llEditFocusState.setBackground(hasFocus ? focusDrawable : norDrawable);
            }
        });

        etEditStateRight.addTextChangedListener(new SimpleTextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                if (isInteger && !StringUtils.isEmpty(s.toString())) {
                    if (Integer.valueOf(s.toString()) > 6) {
                        etEditStateRight.setText("6");
                        etEditStateRight.setSelection(etEditStateRight.getText().length());
                    }
                }
            }
        });
    }

    public String getText() {
        return etEditStateRight.getText().toString().trim();
    }

    public void setText(String info) {
        etEditStateRight.setText(info);
    }
}
