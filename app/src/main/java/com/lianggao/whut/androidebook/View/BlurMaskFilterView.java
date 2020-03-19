package com.lianggao.whut.androidebook.View;

import android.view.View;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 18:34
 */
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 模糊效果View
 */
public class BlurMaskFilterView extends View {

    private Paint mPaint;

    public BlurMaskFilterView(Context context) {
        super(context);
        PaintInit();
    }

    public BlurMaskFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        PaintInit();
    }

    public BlurMaskFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        PaintInit();
    }

    private void PaintInit() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);          //抗锯齿
        mPaint.setColor(Color.RED);//画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //画笔风格
        mPaint.setTextSize(68);             //绘制文字大小，单位px
        mPaint.setStrokeWidth(5);           //画笔粗细
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*//标准模式
        BlurMaskFilter blurMaskFilter1 = new BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL);
        mPaint.setMaskFilter(blurMaskFilter1);
        canvas.drawText("目前最喜欢的是Android开发！", 100, 100, mPaint);*/

        /*//外模糊模式
        BlurMaskFilter blurMaskFilter2 = new BlurMaskFilter(10f, BlurMaskFilter.Blur.OUTER);
        mPaint.setMaskFilter(blurMaskFilter2);
        canvas.drawText("目前最喜欢的是Android开发！", 100, 200, mPaint);*/

        /*//内模糊模式
        BlurMaskFilter blurMaskFilter3 = new BlurMaskFilter(10f, BlurMaskFilter.Blur.INNER);
        mPaint.setMaskFilter(blurMaskFilter3);
        canvas.drawText("目前最喜欢的是Android开发！", 100, 300, mPaint);*/

        //内部加粗，外部模糊
        BlurMaskFilter blurMaskFilter4 = new BlurMaskFilter(10f, BlurMaskFilter.Blur.SOLID);
        mPaint.setMaskFilter(blurMaskFilter4);
        canvas.drawText("目前最喜欢的是Android开发！", 100, 400, mPaint);

        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
}
