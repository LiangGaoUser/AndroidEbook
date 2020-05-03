package com.lianggao.whut.androidebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.google.gson.Gson;
import com.lianggao.whut.androidebook.Adapter.DeleteBookShelfRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Adapter.LoadMoreBookAdapter;
import com.lianggao.whut.androidebook.Adapter.NetRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Fragment.MyAlertDialogFragment;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.Utils.Util;
import com.lianggao.whut.androidebook.Utils.bookShelfTableManger;
import com.lianggao.whut.androidebook.View.LoadMoreRankBookRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;

public class Activity_BookShelf_Delete extends FragmentActivity {
    private RecyclerView recyclerView;
    private DeleteBookShelfRecyclerViewAdapter deleteBookShelfRecyclerViewAdapter;

    private List<String> book_post_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_check_list;//书的种类集合
    private List<Integer>book_id_list;
    private List<Book> bookList;

    private List<String> book_chosed_path_list;//书的封面集合

    private List<String> book_chosed_cover_list;//书的封面集合

    private List<String> book_chosed_name_list;//书的封面集合


    private TextView deleteTextView;
    private bookShelfTableManger bookShelfTableManger;
    private final int MSG_GET_LOCAL_BOOKS_SUCCESS=1;
    private FragmentActivity fragmentActivity;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_LOCAL_BOOKS_SUCCESS:
                    Log.i("获取图片","批量获取图片成功");
                    /*recyclerViewAdapter=new NetRecyclerViewAdapter(getApplicationContext(),book_post_path_list,book_name_list,book_author_list,book_kind_list,book_shortcontent_list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(recyclerViewAdapter);*/

                    deleteBookShelfRecyclerViewAdapter=new DeleteBookShelfRecyclerViewAdapter(getApplicationContext(),book_post_list, book_name_list,book_author_list,book_check_list,book_shortcontent_list);

                    deleteBookShelfRecyclerViewAdapter.setOnItemClickListener(new DeleteBookShelfRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if(deleteBookShelfRecyclerViewAdapter.getCheckList().get(position).equals("true")){
                                deleteBookShelfRecyclerViewAdapter.setCheckListFalse(position);
                                book_check_list.set(position,"false");
                                book_chosed_name_list.remove(book_name_list.get(position));//删除

                            }else if(deleteBookShelfRecyclerViewAdapter.getCheckList().get(position).equals("false")){
                                deleteBookShelfRecyclerViewAdapter.setCheckListTrue(position);
                                book_check_list.set(position,"true");
                                book_chosed_name_list.add(book_name_list.get(position));//将选择的名称放入列表中


                            }
                        }
                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(deleteBookShelfRecyclerViewAdapter);




                    Log.i("初始化","初始化结束");
                    break;
            }
        }
    };


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_delete:
                    if(book_chosed_name_list.isEmpty()){
                        Toast.makeText(getApplicationContext(),"请选择删除的书籍",Toast.LENGTH_LONG).show();
                    }else{
                        MyAlertDialogFragment myAlertDialogFragment=MyAlertDialogFragment.newInstance();
                        myAlertDialogFragment.setOnClickListener(onClickListener);
                        myAlertDialogFragment.show(getSupportFragmentManager(),"MyAlertDialogFragment");
                    }



                    return true;
                case R.id.navigation_all_selected:
                    deleteBookShelfRecyclerViewAdapter.setCheckListAllTrue();
                    return true;
                case R.id.navigation_cancle:
                    deleteBookShelfRecyclerViewAdapter.setCheckListAllFalse();
                    return true;
            }
            return false;
        }
    };
    private DialogInterface.OnClickListener onClickListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which){
                case DialogInterface.BUTTON_POSITIVE:
                    deleteLocalBookShelf();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf_delete);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        fragmentActivity= this.fragmentActivity;

        //返回
        deleteTextView=(TextView)findViewById(R.id.deleteTextView);
        Drawable back= getResources().getDrawable(R.drawable.icon_book_return2);
        back.setBounds(0, 0, 50, 50);
        deleteTextView.setCompoundDrawables(back, null, null, null);
        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getLocalBookShelf();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        book_chosed_path_list=new LinkedList<>();
        book_chosed_cover_list=new LinkedList<>();
        book_chosed_name_list=new LinkedList<>();
    }


    private void getLocalBookShelf(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        new Thread(){
            @Override
            public void run() {

                bookShelfTableManger=new bookShelfTableManger(getApplicationContext());
                bookShelfTableManger.createDb();
                bookList=bookShelfTableManger.findAllBook();
                System.out.println("开始获取本地书架图书");

                book_name_list=new LinkedList<>();
                book_post_list=new LinkedList<>();
                book_author_list=new LinkedList<>();
                book_shortcontent_list=new LinkedList<>();
                book_check_list=new LinkedList<>();

                for(int i=0;i<bookList.size();i++){

                    book_name_list.add(bookList.get(i).getBook_name());
                    book_post_list.add(bookList.get(i).getBook_cover_path());
                    book_author_list.add(bookList.get(i).getBook_author());
                    book_shortcontent_list.add(" ");
                    book_check_list.add("false");
                }
                Message message=new Message();
                message.what=MSG_GET_LOCAL_BOOKS_SUCCESS;
                handler.sendMessage(message);

            }
        }.start();

    }


    private void deleteLocalBookShelf(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        new Thread(){
            @Override
            public void run() {

                //删除书架中所有的书籍以及本地保存的内容




                bookShelfTableManger=new bookShelfTableManger(getApplicationContext());
                bookShelfTableManger.createDb();
                Util.deleteAllBook(bookShelfTableManger.findPathListByNameList(book_chosed_name_list),bookShelfTableManger.findCoverListByNameList(book_chosed_name_list));//删除本地内容
                bookShelfTableManger.deleteByNameList(book_chosed_name_list);//删除表中内容
                getLocalBookShelf();

                Message message=new Message();
                message.what=MSG_GET_LOCAL_BOOKS_SUCCESS;
                handler.sendMessage(message);

            }
        }.start();

    }







}
