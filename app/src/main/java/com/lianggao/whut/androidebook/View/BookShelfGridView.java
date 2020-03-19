package com.lianggao.whut.androidebook.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

import com.lianggao.whut.androidebook.R;

public class BookShelfGridView extends GridView {
    private Bitmap background;
    public BookShelfGridView(Context context) {
        super(context);
    }

    public BookShelfGridView(Context context, AttributeSet attrs) {
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
