package com.kevin.pictureschoose.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 项目名称：PicturesChoose
 * 创建人：KevinLiu
 * 创建时间：2017/7/3 10:35
 * 描述：
 */
public class AppEditText extends EditText {
    public AppEditText(Context context) {
        super(context);
    }

    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setError(CharSequence error) {
        super.setError(error);
        requestFocus();
    }
}
