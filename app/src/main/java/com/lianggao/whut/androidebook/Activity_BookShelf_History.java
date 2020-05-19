package com.lianggao.whut.androidebook;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Adapter.BookShelfHistoryRecyclerViewAdapter;

import com.lianggao.whut.androidebook.Adapter.BookStarRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Fragment.MyAlertDialogFragment;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Utils.Util;
import com.lianggao.whut.androidebook.Utils.bookShelfHistoryTableManger;

import java.util.LinkedList;
import java.util.List;


public class Activity_BookShelf_History extends FragmentActivity {

    //adpter
    private RecyclerView recyclerView;
    private BookShelfHistoryRecyclerViewAdapter bookShelfHistoryRecyclerViewAdapter;
    //数据类型
    private List<String> book_post_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_detail_kind_list;
    private List<String>book_main_kind_list;
    private List<Book> bookList;

    //xml页面元素
    private TextView deleteTextView;
    private Button deleteBtn;
    //数据库操作相关
    private bookShelfHistoryTableManger bookShelfHistoryTableManger;

    //handler变量
    private final int MSG_GET_LOCAL_BOOKS_HISTORY_SUCCESS=1;



    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_LOCAL_BOOKS_HISTORY_SUCCESS:
                    Log.i("获取图片","批量获取图片成功");


                    bookShelfHistoryRecyclerViewAdapter=new BookShelfHistoryRecyclerViewAdapter(getApplicationContext(),book_post_list,book_name_list,book_author_list,book_main_kind_list,book_detail_kind_list,book_shortcontent_list);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(bookShelfHistoryRecyclerViewAdapter);
                    Log.i("初始化","初始化结束");
                    break;
            }
        }
    };


   /* private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_delete_all:
                    MyAlertDialogFragment myAlertDialogFragment=MyAlertDialogFragment.newInstance();
                    myAlertDialogFragment.setOnClickListener(onClickListener);
                    myAlertDialogFragment.show(getSupportFragmentManager(),"MyAlertDialogFragment");
                    return true;
            }
            return false;
        }
    };*/


    private DialogInterface.OnClickListener onClickListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which){
                case DialogInterface.BUTTON_POSITIVE:
                    deleteLocalBookShelfHistory();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf_history);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);

        //返回
        deleteTextView=(TextView)findViewById(R.id.histrory_textView);
        Drawable back= getResources().getDrawable(R.drawable.icon_book_return2);
        back.setBounds(0, 0, 50, 50);
        deleteTextView.setCompoundDrawables(back, null, null, null);
        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deleteBtn=(Button)findViewById(R.id.clear_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialogFragment myAlertDialogFragment=MyAlertDialogFragment.newInstance();
                myAlertDialogFragment.setOnClickListener(onClickListener);
                myAlertDialogFragment.show(getSupportFragmentManager(),"MyAlertDialogFragment");
            }
        });

        getLocalBookShelfHistory();

       /* BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/

    }


    private void getLocalBookShelfHistory(){
        new Thread(){
            @Override
            public void run() {
                bookShelfHistoryTableManger=new bookShelfHistoryTableManger(getApplicationContext());
                bookShelfHistoryTableManger.createDb();
                bookList=bookShelfHistoryTableManger.findAllBook();
                System.out.println("开始获取书架历史图书");

                book_name_list=new LinkedList<>();
                book_post_list=new LinkedList<>();
                book_author_list=new LinkedList<>();
                book_shortcontent_list=new LinkedList<>();
                book_main_kind_list=new LinkedList<>();
                book_detail_kind_list=new LinkedList<>();

                for(int i=0;i<bookList.size();i++){

                    book_name_list.add(bookList.get(i).getBook_name());
                    book_post_list.add(bookList.get(i).getBook_cover_path());
                    book_author_list.add(bookList.get(i).getBook_author());
                    book_shortcontent_list.add(" ");
                    book_main_kind_list.add(bookList.get(i).getBook_main_kind());
                    book_detail_kind_list.add(bookList.get(i).getBook_detail_kind());
                }
                Message message=new Message();
                message.what=MSG_GET_LOCAL_BOOKS_HISTORY_SUCCESS;
                handler.sendMessage(message);

            }
        }.start();

    }


    private void deleteLocalBookShelfHistory(){
        new Thread(){
            @Override
            public void run() {
                //删除书架中所有的书籍以及本地保存的内容
                bookShelfHistoryTableManger=new bookShelfHistoryTableManger(getApplicationContext());
                bookShelfHistoryTableManger.createDb();
                Util.deleteAllCover(bookShelfHistoryTableManger.findAllCoverList());//删除本地所有封面文件内容
                bookShelfHistoryTableManger.deleteTable();//删除表中内容
                getLocalBookShelfHistory();

                Message message=new Message();
                message.what=MSG_GET_LOCAL_BOOKS_HISTORY_SUCCESS;
                handler.sendMessage(message);

            }
        }.start();

    }







}
