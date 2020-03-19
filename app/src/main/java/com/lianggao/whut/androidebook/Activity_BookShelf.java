package com.lianggao.whut.androidebook;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Adapter.BookShelfGridViewAdapter;
import com.lianggao.whut.androidebook.View.BookShelfGridView;

import java.util.LinkedList;
import java.util.List;

//BookShelfGridView.java  activity_bookshelf2.xml  BookShelfGridViewAdapter part_activity_everybook.xml
//activity_bookshelf2.xml使用BookShelfGridView作为布局，part_activity_everybook是每一个书本的布局，通过 BookShelfGridViewAdapter适配器，将书本添加到GridView中去
//类似的将BookShelfGridVIew用在了Fragment_bookshelf.xml中，而在FragmentShelf.java中对Fragment_bookshelf.xml进行从操作
public class Activity_BookShelf extends FragmentActivity {
    private BookShelfGridView bookShelfGridView;//书架布局类
    private Button back_Btn;//书架中的返回按钮
    private BookShelfGridViewAdapter bookShelfGridViewAdapter;
    private List<String> book_name_list;//书名集合
    private List<Integer>book_post_list;//书的封面集合
    private List<Integer>book_percent_list;//书的已读百分比集合
    private List<Boolean>book_download_list;//书是否已经下载的集合
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf2);
        bookShelfGridView=(BookShelfGridView)findViewById(R.id.BookShelfGridView);
        back_Btn=(Button)findViewById(R.id.back_Btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Activity_BookShelf.this,"点击了返回按钮",Toast.LENGTH_LONG).show();
                showPopupMenu(v);
            }
        });
        Typeface iconfont= Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");
        initData();
        bookShelfGridViewAdapter=new BookShelfGridViewAdapter(getApplicationContext(),book_name_list,book_post_list,book_percent_list,book_download_list);

        TextView textView;
        bookShelfGridView.setAdapter(bookShelfGridViewAdapter);
        System.out.println("%%%onCreate");




        bookShelfGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Activity_BookShelf.this,"点击了书本"+position,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();
        System.out.println("%%%onStart");
    }

    @Override
    protected void onResume() {

        super.onResume();
        System.out.println("%%%onResume");
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        System.out.println("%%%onCreateView");
        return super.onCreateView(name, context, attrs);


    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("%%%onPause");
    }

    @Override
    public void onBackPressed() {super.onBackPressed();

    }

    private void showPopupMenu(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_each_book_click,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "关闭popupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }
    /**
     * 初始化要在BookShelfGridView中的适配器中的数据
     */
    private void initData(){
        book_name_list=new LinkedList<>();
        book_post_list=new LinkedList<>();
        book_percent_list=new LinkedList<>();
        book_download_list=new LinkedList<>();
        for(int i=0;i<20;i++){
            book_name_list.add("book"+i);
            book_post_list.add(R.drawable.img_bookshelf_everybook);
            book_percent_list.add(32);
            book_download_list.add(true);
        }
    }

}
