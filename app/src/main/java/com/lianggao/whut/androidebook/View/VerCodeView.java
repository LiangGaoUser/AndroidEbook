package com.lianggao.whut.androidebook.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.lianggao.whut.androidebook.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class VerCodeView extends View {
    private String text;
    private int textColor;
    private int textSize;
    private Paint paint=new Paint();
    private Rect rect;
    public VerCodeView(Context context) {
        this(context,null);
    }

    public VerCodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                text=randomText();
                postInvalidate();
            }
        });
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.VerCodeView);
        int n= attrs.getAttributeCount();
        for(int i=0;i<n;i++){
            int attr=typedArray.getIndex(i);
            switch(attr){
                case R.styleable.VerCodeView_text:
                    text=typedArray.getString(attr);
                    break;
                case R.styleable.VerCodeView_textColor:
                    textColor=typedArray.getColor(attr,Color.BLACK);
                    break;

                case R.styleable.VerCodeView_textSize:
                    textSize=typedArray.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();

        paint.setTextSize(textSize);
        paint.setColor(textColor);
        rect=new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(Color.YELLOW);
        //setMeasuredDimension(100,100);
        /*getWidth()和getHeight是view原来配置的大小，getMeasuredWidth和getMeasuredHeight是实际的大小*/
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);
        paint.setColor(textColor);
        canvas.drawText(text, getWidth() / 2 - rect.width() / 2, getHeight() / 2 + rect.height() / 2, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    //生成随机验证码数字
    private String randomText()
    {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }

        return sb.toString();
    }

}
