package com.lianggao.whut.androidebook.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Model.MyPoint;
import com.lianggao.whut.androidebook.R;

public class BookPageView extends android.support.v7.widget.AppCompatTextView {
    private MyPoint a,b,c,d,e,f,j,h,i,g,k;
    private int viewWidth,viewHeight;
    private int defaultHeight,defaultwidth;
    private Paint pointPaint;
    private Paint bgPaint;
    private Paint pathAPaint;
    private Path pathA;
    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private Paint pathCPaint;
    private Path pathC;
    private Path pathB;
    private Paint pathBPaint;
    private Scroller mScroller;
    private String style="";
    private TextPaint textPaint;//绘制文字画笔
    private Paint pathCContentPaint;
    private Paint pathBContentPaint;
    private float x,y,width=0,height=0;
    private String firstTouchLocation="";
    public static final String STYLE_TOP_RIGHT="STYLE_TOP_RIGHT";
    public static final String STYLE_LOWER_RIGHT="STYLE_LOWER_RIGHT";
    public static final String STYLE_RIGHT="STYLE_RIGHT";
    public static final String STYLE_LEFT="STYLE_LEFT";
    public static final String STYLE_MIDDLE="STYLE_MIDDLE";


    public BookPageView(Context context) {
        super(context);
        init();
    }

    public BookPageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    //求两线段的交点
    public MyPoint getIntersectionPoint(MyPoint a1,MyPoint a2,MyPoint b1,MyPoint b2){
        float x1=a1.getX();
        float y1=a1.getY();
        float x2=a2.getX();
        float y2=a2.getY();
        float x3=b1.getX();
        float y3=b1.getY();
        float x4=b2.getX();
        float y4=b2.getY();
        float pointX =((x1 - x2) * (x3 * y4 - x4 * y3) - (x3 - x4) * (x1 * y2 - x2 * y1))
                / ((x3 - x4) * (y1 - y2) - (x1 - x2) * (y3 - y4));
        float pointY =((y1 - y2) * (x3 * y4 - x4 * y3) - (x1 * y2 - x2 * y1) * (y3 - y4))
                / ((y1 - y2) * (x3 - x4) - (x1 - x2) * (y3 - y4));
        return new MyPoint(pointX,pointY);
    }
    public void init(){
        this.setText("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        textPaint=new TextPaint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(50);



        pathCContentPaint=new Paint();
        pathCContentPaint.setColor(Color.BLACK);
        pathCContentPaint.setTextSize(25);

        pathBContentPaint=new Paint();
        pathBContentPaint.setColor(Color.BLACK);
        pathBContentPaint.setTextSize(25);

        mScroller=new Scroller(getContext(),new LinearInterpolator());
        defaultHeight=1070;
        defaultHeight=1860;
        a=new MyPoint();
        f=new MyPoint();
        b=new MyPoint();
        c=new MyPoint();
        d=new MyPoint();
        e=new MyPoint();
        g=new MyPoint();
        h=new MyPoint();
        i=new MyPoint();
        j=new MyPoint();
        k=new MyPoint();
        pointPaint=new Paint();
        pointPaint.setColor(Color.BLUE);
        pointPaint.setTextSize(25);
        bgPaint=new Paint();
        bgPaint.setColor(Color.WHITE);

        pathA=new Path();
        pathAPaint=new Paint();
        pathAPaint.setColor(Color.GREEN);
        pathAPaint.setAntiAlias(true);

        pathC=new Path();
        pathCPaint=new Paint();
        pathCPaint.setColor(Color.YELLOW);
        pathCPaint.setAntiAlias(true);
        pathCPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        pathB=new Path();
        pathBPaint=new Paint();
        pathBPaint.setColor(Color.RED);//getResources().getColor(R.color.colorAccent)

        pathBPaint.setAntiAlias(true);
        pathBPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

    }
    private void calcPointsXY(MyPoint a,MyPoint f){
        g.setX((a.getX()+f.getX())/2);
        g.setY((a.getY()+f.getY())/2);
        e.setX(g.getX()-(f.getY()-g.getY())*(f.getY()-g.getY())/(f.getX()-g.getX()));
        e.setY(f.getY());
        h.setX(f.getX());
        h.setY(g.getY()-(f.getX()-g.getX())*(f.getX()-g.getX())/(f.getY()-g.getY()));
        c.setY(f.getY());
        c.setX(e.getX()-(f.getX()-e.getX())/2);
        j.setX(f.getX());
        j.setY(h.getY()-(f.getY()-h.getY())/2);
        b=getIntersectionPoint(a,e,c,j);
        k=getIntersectionPoint(a,h,c,j);
        d.setX((c.getX()+2*e.getX()+b.getX())/4);
        d.setY((2*e.getY()+c.getY()+b.getY())/4);
        i.setX((j.getX()+2*h.getX()+k.getX())/4);
        i.setY((2*h.getY()+j.getY()+k.getY())/4);
        System.out.println(a.getX()+a.getY());
        System.out.println(b.getX()+b.getY());
        System.out.println(c.getX()+c.getY());
        System.out.println(d.getX()+d.getY());
        System.out.println(e.getX()+e.getY());
        System.out.println(f.getX()+f.getY());
        System.out.println(g.getX()+g.getY());
        System.out.println(h.getX()+h.getY());
        System.out.println(i.getX()+i.getY());
        System.out.println(j.getX()+j.getY());
        System.out.println(k.getX()+k.getY());





    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       /* canvas.drawRect(0,0,viewWidth,viewHeight,bgPaint);
        canvas.drawText("a",a.getX(),a.getY(),pointPaint);
        canvas.drawText("b",b.getX(),b.getY(),pointPaint);
        canvas.drawText("c",c.getX(),c.getY(),pointPaint);
        canvas.drawText("d",d.getX(),d.getY(),pointPaint);
        canvas.drawText("e",e.getX(),e.getY(),pointPaint);
        canvas.drawText("f",f.getX(),f.getY(),pointPaint);
        canvas.drawText("g",g.getX(),g.getY(),pointPaint);
        canvas.drawText("h",h.getX(),h.getY(),pointPaint);
        canvas.drawText("i",i.getX(),i.getY(),pointPaint);
        canvas.drawText("j",j.getX(),j.getY(),pointPaint);
        canvas.drawText("k",k.getX(),k.getY(),pointPaint);*/

        bitmap= Bitmap.createBitmap(viewWidth,viewHeight,Bitmap.Config.ARGB_8888);
        bitmapCanvas=new Canvas(bitmap);
        //canvas.drawBitmap(bitmap,0,0,null);
        if(a.getX()==-1&&a.getY()==-1){
            //bitmapCanvas.drawPath(getPathDefault(),pathAPaint);
            drawPathAContent(bitmapCanvas,getPathDefault(),pathAPaint);
        }else{
            if(f.getX()==viewWidth&&f.getY()==viewHeight){
                //bitmapCanvas.drawPath(getPathAFromLowerRight(),pathAPaint);
                drawPathAContent(bitmapCanvas,getPathAFromLowerRight(),pathAPaint);
                bitmapCanvas.drawPath(getPathC(),pathCPaint);
                bitmapCanvas.drawPath(getPathB(),pathBPaint);
                //drawPathBContent(bitmapCanvas,getPathAFromLowerRight(),pathBContentPaint);
            }else if(f.getX()==viewWidth&&f.getY()==0){
                // bitmapCanvas.drawPath(getPathAFromTopRight(),pathAPaint);
                drawPathAContent(bitmapCanvas,getPathAFromTopRight(),pathAPaint);
                bitmapCanvas.drawPath(getPathC(),pathCPaint);
                bitmapCanvas.drawPath(getPathB(),pathBPaint);
                //drawPathBContent(bitmapCanvas,getPathAFromTopRight(),pathBContentPaint);
            }

            //bitmapCanvas.drawPath(getPathC(),pathCPaint);
            //bitmapCanvas.drawPath(getPathB(),pathBPaint);
        }
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.restore();
        //canvas.drawBitmap(bitmap,0,0,null);
       /* bitmapCanvas.drawPath(getPathAFromTopRight(),pathAPaint);
        bitmapCanvas.drawPath(getPathC(),pathCPaint);
        bitmapCanvas.drawPath(getPathB(),pathBPaint);
        canvas.drawBitmap(bitmap,0,0,null);*/

    }


    /**
     * 绘制A区域内容
     * @param canvas
     * @param pathA
     * @param pathPaint
     */
    private void drawPathAContent(Canvas canvas, Path pathA, Paint pathPaint){
        Bitmap contentBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
        Canvas contentCanvas = new Canvas(contentBitmap);

        //下面开始绘制区域内的内容...
        contentCanvas.drawPath(pathA,pathPaint);//绘制一个背景，用来区分各区域
        //contentCanvas.drawText("这是在A区域的内容...AAAA", 10, 100, textPaint);

        String message = "paint,draw paint指用颜色画,如油画颜料、水彩或者水墨画,而draw 通常指用铅笔、钢笔或者粉笔画,后者一般并不涂上颜料。两动词的相应名词分别为p";
        StaticLayout myStaticLayout = new StaticLayout(message, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        contentCanvas.drawText(message, 10, 100, textPaint);
        //myStaticLayout.draw(canvas);





        //结束绘制区域内的内容...

        canvas.save();
        canvas.clipPath(pathA, Region.Op.INTERSECT);//对绘制内容进行裁剪，取和A区域的交集
        canvas.drawBitmap(contentBitmap, 0, 0, null);
        canvas.restore();
    }

    /**
     * 绘制B区域内容
     * @param canvas
     * @param pathPaint
     * @param pathA
     */
    private void drawPathBContent(Canvas canvas, Path pathA, Paint pathPaint){
        Bitmap contentBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
        Canvas contentCanvas = new Canvas(contentBitmap);

        //下面开始绘制区域内的内容...
        contentCanvas.drawPath(getPathB(),pathPaint);
        String message = "paint,draw paint指用颜色画,如油画颜料、水彩或者水墨画,而draw 通常指用铅笔、钢笔或者粉笔画,后者一般并不涂上颜料。两动词的相应名词分别为p";
        StaticLayout myStaticLayout = new StaticLayout(message, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        //contentCanvas.drawText(message, 10, 100, textPaint);
        myStaticLayout.draw(canvas);
        //结束绘制区域内的内容...

        canvas.save();
        canvas.clipPath(pathA);//裁剪出A区域
        canvas.clipPath(getPathC(),Region.Op.UNION);//裁剪出A和C区域的全集
        canvas.clipPath(getPathB(), Region.Op.REVERSE_DIFFERENCE);//裁剪出B区域中不同于与AC区域的部分
        canvas.drawBitmap(contentBitmap, 0, 0, null);
        canvas.restore();
    }



    //每次点击时候调用，根据点击的位置来判断是上翻页，还是下翻页
    public void setTouchPoint(float x,float y,String style){
        a.setX(x);
        a.setY(y);
        MyPoint touchPoint = new MyPoint(x,y);
        switch(style){
            case STYLE_LOWER_RIGHT:
                f.setX(viewWidth);
                f.setY(viewHeight);
                calcPointsXY(a,f);
                //如果大于0则设置a点坐标重新计算各标识点位置，否则a点坐标不变
                touchPoint=new MyPoint(x,y);
                if(calcPointCX(touchPoint,f)<0){
                    calcPointAByTouchPoint();
                    calcPointsXY(a,f);
                }
                postInvalidate();
                break;
            case STYLE_TOP_RIGHT:
                f.setX(viewWidth);
                f.setY(0);
                calcPointsXY(a,f);
                //如果大于0则设置a点坐标重新计算各标识点位置，否则a点坐标不变
                touchPoint=new MyPoint(x,y);
                if(calcPointCX(touchPoint,f)<0){
                    calcPointAByTouchPoint();
                    calcPointsXY(a,f);
                }
                postInvalidate();
                break;
            case STYLE_LEFT:
                break;
            case STYLE_MIDDLE:
                break;
            case STYLE_RIGHT:
                a.setY(viewHeight-1);
                f.setX(viewWidth);
                f.setY(viewHeight);
                calcPointsXY(a,f);
                postInvalidate();
                break;

            default:
                calcPointsXY(a,f);
                //如果大于0则设置a点坐标重新计算各标识点位置，否则a点坐标不变
                if(calcPointCX(touchPoint,f)<0){
                    calcPointAByTouchPoint();
                    calcPointsXY(a,f);
                }
                postInvalidate();
                break;

        }





    }






    /**
     * c点x<0,则会重新得到a的坐标
     */
    private void calcPointAByTouchPoint(){
        float w0=viewWidth-c.getX();
        float w1=Math.abs(f.getX()-a.getX());
        float w2=(viewWidth*w1)/w0;
        a.setX(f.getX()-w2);
        float h1=Math.abs(f.getY()-a.getY());
        float h2=(w2*h1)/w1;
        a.setY(Math.abs(f.getY()-h2));
    }



    /**
     * 计算C点的X值
     * @param a
     * @param f
     * @return
     */
    private float calcPointCX(MyPoint a, MyPoint f){
        MyPoint g,e;
        g = new MyPoint();
        e = new MyPoint();
        g.setX( (a.getX() + f.getX()) / 2);
        g.setY((a.getY() + f.getY()) / 2);

        e.setX( g.getX() - (f.getY() - g.getY()) * (f.getY() - g.getY()) / (f.getX()- g.getX()));
        e.setY(f.getY());

        return e.getX() - (f.getX() - e.getX()) / 2;
    }
    public float getViewWidth(){
        return viewWidth;
    }

    public float getViewHeight(){
        return viewHeight;
    }
    public void setDefaultPath(){
        a.setX(-1);
        a.setY(-1);
        postInvalidate();
    }
    private Path getPathAFromLowerRight(){
        pathA.reset();
        pathA.lineTo(0,viewHeight);
        pathA.lineTo(c.getX(),c.getY());
        pathA.quadTo(e.getX(),e.getY(),b.getX(),b.getY());
        pathA.lineTo(a.getX(),a.getY());
        pathA.lineTo(k.getX(),k.getY());
        pathA.quadTo(h.getX(),h.getY(),j.getX(),j.getY());
        pathA.lineTo(viewWidth,0);
        pathA.close();
        return  pathA;
    }
    private Path getPathAFromTopRight(){
        pathA.reset();
        pathA.lineTo(c.getX(),c.getY());//移动到c点
        pathA.quadTo(e.getX(),e.getY(),b.getX(),b.getY());//从c到b画贝塞尔曲线，控制点为e
        pathA.lineTo(a.getX(),a.getY());//移动到a点
        pathA.lineTo(k.getX(),k.getY());//移动到k点
        pathA.quadTo(h.getX(),h.getY(),j.getX(),j.getY());//从k到j画贝塞尔曲线，控制点为h
        pathA.lineTo(viewWidth,viewHeight);//移动到右下角
        pathA.lineTo(0, viewHeight);//移动到左下角
        pathA.close();
        return pathA;
    }
    private Path getPathC(){
        pathC.reset();
        pathC.moveTo(i.getX(),i.getY());
        pathC.lineTo(d.getX(),d.getY());
        pathC.lineTo(b.getX(),b.getY());
        pathC.lineTo(a.getX(),a.getY());
        pathC.lineTo(k.getX(),k.getY());
        pathC.close();
        return pathC;
    }
    private Path getPathB(){
        pathB.reset();
        pathB.lineTo(0,viewHeight);
        pathB.lineTo(viewWidth,viewHeight);
        pathB.lineTo(viewWidth,0);
        pathB.close();
        return pathB;
    }
    private Path getPathDefault(){
        pathA.reset();
        pathA.lineTo(0,viewHeight);
        pathA.lineTo(viewWidth,viewHeight);
        pathA.lineTo(viewWidth,0);
        pathA.close();
        return pathA;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height=measureSize(defaultHeight,heightMeasureSpec);
        int width=measureSize(defaultwidth,widthMeasureSpec);
        setMeasuredDimension(width,height);
        viewWidth=width;
        viewHeight=height;
        a.setX(-1);
        a.setY(-1);
        f.setX(width);
        f.setY(height);
        //calcPointsXY(a,f);
    }
    private int measureSize(int defaultSize,int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            float x = mScroller.getCurrX();
            float y = mScroller.getCurrY();

            if(style.equals(STYLE_TOP_RIGHT)){
                setTouchPoint(x,y,STYLE_TOP_RIGHT);
            }else {
                setTouchPoint(x,y,STYLE_LOWER_RIGHT);
            }
            if (mScroller.getFinalX() == x && mScroller.getFinalY() == y){
                setDefaultPath();//重置默认界面
            }
        }
    }
    /**
     * 取消翻页动画,计算滑动位置与时间
     */
    public void startCancelAnim(){
        int dx=0,dy=0;
        //让a滑动到f点所在位置，留出1像素是为了防止当a和f重叠时出现View闪烁的情况
        if(style.equals(STYLE_TOP_RIGHT)){
            dx = (int) (viewWidth-1-a.getX());
            dy = (int) (1-a.getY());
        }else{
            dx = (int) (viewWidth-1-a.getX());
            dy = (int) (viewHeight-1-a.getY());
        }
        mScroller.startScroll((int) a.getX(), (int) a.getY(), dx, dy, 400);
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                if(x<=viewWidth/3){//左
                    style = STYLE_LEFT;
                    setTouchPoint(x,y,style);

                }else if(x>viewWidth/3 && y<=viewHeight/3){//上
                    style = STYLE_TOP_RIGHT;
                    setTouchPoint(x,y,style);

                }else if(x>viewWidth*2/3 && y>viewHeight/3 && y<=viewHeight*2/3){//右
                    style = STYLE_RIGHT;
                    setTouchPoint(x,y,style);

                }else if(x>viewWidth/3 && y>viewHeight*2/3){//下
                    style = STYLE_LOWER_RIGHT;
                    setTouchPoint(x,y,style);

                }else if(x>viewWidth/3 && x<viewWidth*2/3 && y>viewHeight/3 && y<viewHeight*2/3){//中
                    style = STYLE_MIDDLE;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                setTouchPoint(event.getX(),event.getY(),style);
                break;
            case MotionEvent.ACTION_UP:
                startCancelAnim();
                break;
        }
        return true;
    }
}
