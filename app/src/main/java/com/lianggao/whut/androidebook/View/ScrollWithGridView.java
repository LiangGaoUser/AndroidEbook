package com.lianggao.whut.androidebook.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 9:00
 */
public class ScrollWithGridView extends GridView{
    public ScrollWithGridView(Context context) {
        super(context);
    }

    public ScrollWithGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWithGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
