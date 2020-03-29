package com.lianggao.whut.androidebook.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 22:40
 */
public class LabelGridView extends GridView {
    public LabelGridView(Context context) {
        super(context);
    }
    public LabelGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
