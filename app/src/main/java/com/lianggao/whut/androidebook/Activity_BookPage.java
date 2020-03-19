package com.lianggao.whut.androidebook;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.lianggao.whut.androidebook.View.BookPageView;

public class Activity_BookPage extends FragmentActivity {
    private BookPageView bookPageView;
    private float x,y,width,height;
    private String firstTouchLocation="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookpage);
        bookPageView=(BookPageView)findViewById(R.id.BookPageView);
       /* bookPageView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                      switch (event.getAction()){
                                case MotionEvent.ACTION_DOWN:
                                    x=event.getX();
                                    y=event.getY();

                                    width=bookPageView.getViewWidth();
                                    height=bookPageView.getViewHeight();
                                    if(x>width*2/3&&x<width&&y>height/3&&y<height*2/3){
                                        System.out.println("ppp点击了最右边");
                                        firstTouchLocation="STYLE_RIGHT";
                                        bookPageView.setTouchPoint(x,y,bookPageView.STYLE_RIGHT);
                                    }else if(x<width/3){
                                        firstTouchLocation="STYLE_LEFT";
                                        System.out.println("ppp点击了最左边");
                                    }else if(x<width/3&&x<width*2/3&&y>height/3&&y<height*2/3){
                                        firstTouchLocation="STYLE_MIDDLE";
                                        System.out.println("ppp点击了最中间");
                                    }else if(x>width/3&&x<width&&y>0&&y<height/3){
                                        //上部分进行翻页
                                        System.out.println("ppp点击了右上方");
                                        firstTouchLocation="STYLE_TOP_RIGHT";
                                        bookPageView.setText("heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                                        bookPageView.setTouchPoint(x,y,bookPageView.STYLE_TOP_RIGHT);

                                    }else if(x>width/3&&x<width&&y>height*2/3&&y<height){
                                        //下部分进行翻页
                                        System.out.println("ppp点击了右下方");
                                        firstTouchLocation="STYLE_LOWER_RIGHT";
                                        bookPageView.setTouchPoint(x,y,bookPageView.STYLE_LOWER_RIGHT);
                                    }
                        if(event.getY() < bookPageView.getViewHeight()/2){//从上半部分翻页
                            System.out.println("翻页上部分");
                            bookPageView.setTouchPoint(event.getX(),event.getY(),bookPageView.STYLE_TOP_RIGHT);
                        }else if(event.getY() >= bookPageView.getViewHeight()/2) {//从下半部分翻页
                            System.out.println("翻页下部分");
                            bookPageView.setTouchPoint(event.getX(),event.getY(),bookPageView.STYLE_LOWER_RIGHT);
                        }






                                    break;
                                case MotionEvent.ACTION_MOVE:


                        x=event.getX();
                        y=event.getY();

                        width=bookPageView.getViewWidth();
                        height=bookPageView.getViewHeight();
                        if(x>width*2/3&&x<width&&y>height/3&&y<height*2/3){
                            System.out.println("ppp点击了最右边");
                            bookPageView.setTouchPoint(x,y,bookPageView.STYLE_RIGHT);
                        }else if(x<width/3){
                            System.out.println("ppp点击了最左边");
                        }else if(x<width/3&&x<width*2/3&&y>height/3&&y<height*2/3){
                            System.out.println("ppp点击了最中间");
                        }else if(x>width/3&&x<width&&y>0&&y<height/3){
                            //上部分进行翻页
                            System.out.println("ppp点击了右上方");
                            bookPageView.setTouchPoint(x,y,bookPageView.STYLE_TOP_RIGHT);
                        }else if(x>width/3&&x<width&&y>height*2/3&&y<height){
                            //下部分进行翻页
                            System.out.println("ppp点击了右下方");
                            bookPageView.setTouchPoint(x,y,bookPageView.STYLE_LOWER_RIGHT);
                        }
                                    x=event.getX();
                                    y=event.getY();
                                    switch(firstTouchLocation){

                                        case "STYLE_RIGHT":
                                            bookPageView.setTouchPoint(x,y,bookPageView.STYLE_RIGHT);
                                            break;
                                        case "STYLE_TOP_RIGHT":
                                            bookPageView.setTouchPoint(x,y,bookPageView.STYLE_TOP_RIGHT);
                                            break;
                                        case "STYLE_LOWER_RIGHT":
                                            bookPageView.setTouchPoint(x,y,bookPageView.STYLE_LOWER_RIGHT);
                                            break;
                                    }





                                    //bookPageView.setTouchPoint(event.getX(),event.getY(),"");
                                    break;
                                case MotionEvent.ACTION_UP:

                                    //bookPageView.setDefaultPath();//回到默认状态
                                    if(firstTouchLocation.equals("STYLE_RIGHT")){
                                        bookPageView.setStyle("STYLE_RIGHT");
                                    }else if(firstTouchLocation.equals("STYLE_TOP_RIGHT")){
                                        bookPageView.setStyle("STYLE_TOP_RIGHT");
                                    }else if(firstTouchLocation.equals("STYLE_LOWER_RIGHT")){
                                        bookPageView.setStyle("STYLE_LOWER_RIGHT");
                                    }
                       // bookPageView.setStyle("STYLE_LOWER_RIGHT");
                        bookPageView.startCancelAnim();
                        break;

                }
                return false;
            }
        });*/
    }
}
