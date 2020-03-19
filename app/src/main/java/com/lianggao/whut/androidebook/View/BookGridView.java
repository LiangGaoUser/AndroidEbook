package com.lianggao.whut.androidebook.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.GridView;

import com.lianggao.whut.androidebook.R;

/**
 * 用来作为商城中类似书架中的布局，和BookShelfGridView类似
 */
public class BookGridView extends GridView {
    private Bitmap background;

    public BookGridView(Context context) {
        super(context);
    }

    public BookGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        background= BitmapFactory.decodeResource(getResources(), R.drawable.img_book_gridview_background);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        int count=getChildCount();
        int top=count>0?getChildAt(0).getTop():0;
        int backgroundWidth=background.getWidth();
        int backgroundHeight=background.getHeight();
        int width=getWidth();
        int height=getHeight();
        for(int y=top;y<height;y+=backgroundWidth){
            for(int x=0;x<width;x+=backgroundHeight){
                canvas.drawBitmap(background,x,y,null);
            }
        }


        super.dispatchDraw(canvas);
    }
}
