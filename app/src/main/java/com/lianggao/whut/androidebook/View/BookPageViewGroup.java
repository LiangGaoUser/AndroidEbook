package com.lianggao.whut.androidebook.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BookPageViewGroup extends ViewGroup {
    private BookPageView bookPageView1,bookPageView2,bookPageView3;
    private BookPageView bookPageView;
    private float x,y,width,height;
    private String firstTouchLocation="";
    public BookPageViewGroup(Context context) {
        super(context);
        init();
    }

    public BookPageViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            child.layout(0, 0, width, height);
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }



    void init(){
        bookPageView1=new BookPageView(getContext());
        bookPageView2=new BookPageView(getContext());
        bookPageView3=new BookPageView(getContext());
        this.addView(bookPageView2,10,100);

    }

}
