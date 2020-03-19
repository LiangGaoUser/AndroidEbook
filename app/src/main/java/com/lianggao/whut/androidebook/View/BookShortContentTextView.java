package com.lianggao.whut.androidebook.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 16:20
 */
@SuppressLint("AppCompatCustomView")
public class BookShortContentTextView extends TextView {
    public BookShortContentTextView(Context context) {
        super(context);
    }

    public BookShortContentTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setTypeface(Typeface tf) {
        tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/fangzhengzhengyuan.ttf");
        super.setTypeface(tf);
    }
}
