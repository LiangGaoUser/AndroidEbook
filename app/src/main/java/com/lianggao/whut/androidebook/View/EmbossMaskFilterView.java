package com.lianggao.whut.androidebook.View;

import android.view.View;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 18:35
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lianggao.whut.androidebook.R;

/**
 * 浮雕效果View
 */
public class EmbossMaskFilterView extends View {

    public EmbossMaskFilterView(Context context) {
        super(context);
    }

    public EmbossMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmbossMaskFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float[] direction = new float[]{ 1, 1, 3 };   // 设置光源的方向
        float light = 2f;     //设置环境光亮度
        float specular = 8;     // 定义镜面反射系数
        float blur = 5.0f;      //模糊半径
        EmbossMaskFilter emboss = new EmbossMaskFilter(direction,light,specular,blur);

        Paint paint = new Paint();
        paint.setAntiAlias(true);          //抗锯齿
        paint.setColor(Color.WHITE);//画笔颜色

        paint.setStyle(Paint.Style.FILL);  //画笔风格
        paint.setTextSize(70);             //绘制文字大小，单位px
        paint.setStrokeWidth(8);           //画笔粗细
        paint.setMaskFilter(emboss);

        paint.setMaskFilter(emboss);
        canvas.drawText("这是一张漂亮妹子的图片", 50, 100, paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_bookshelf_everybook);
        canvas.drawBitmap(bitmap, 150, 200, paint);

        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
}
