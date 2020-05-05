package com.lianggao.whut.androidebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

//import cn.pda.serialport.Tools;

public class Activity_Read_Pdf extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener, View.OnClickListener {

    private PDFView pdfView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pdf);

        //搭建环境的API Level必须大于等于9
        /*if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/
        String path=getIntent().getStringExtra("path");
        String name=getIntent().getStringExtra("name");
        setTitle(name);
        pdfView =  findViewById( R.id.pdfView);
        Log.i("shuxue",path);
        System.out.println("*******************************"+path);
        displayFromFile(new File(path));


    }




    private void displayFromFile( File file ) {

        try{
            pdfView.fromFile(file)   //设置pdf文件地址
                    .defaultPage(1)
                    .onPageChange(this)
                    .enableSwipe(false)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .spacing(10) // in dp
                    .onPageError(this)
//                .pageFitPolicy(FitPolicy.BOTH)
                    .load();






        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
//        Toast.makeText( MainActivity.this , "page= " + page +
//                " pageCount= " + pageCount , Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载完成回调
     * @param nbPages  总共的页数
     */
    @Override
    public void loadComplete(int nbPages) {
//        Toast.makeText( MainActivity.this ,  "加载完成" + nbPages  , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.i("error", "Cannot load page " + page);
    }


    @Override
    public void onClick(View v) {

    }
}