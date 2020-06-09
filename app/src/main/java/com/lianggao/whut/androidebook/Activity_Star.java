package com.lianggao.whut.androidebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.google.gson.Gson;
import com.lianggao.whut.androidebook.Adapter.BookStarRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Adapter.BookshelfKindRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.Utils.DialogThridUtils;
import com.lianggao.whut.androidebook.Utils.WeiboDialogUtils;
import com.lianggao.whut.androidebook.Utils.bookShelfTableManger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Activity_Star extends Activity {
    private View rootView;
    //
    private RecyclerView recyclerView;
    private BookStarRecyclerViewAdapter bookStarRecyclerViewAdapter;
    //
    private bookShelfTableManger bookShelfTableManger;

    //
    private List<Book> bookList;
    private List<String>book_post_list;
    private List<String>book_name_list;
    private List<String>book_author_list;
    private List<String>book_shortcontent_list;
    private List<String>book_main_kind_list;
    private List<String>book_detail_kind_list;
    private List<String>book_path_list;
    private Dialog dialog;
    //
    private final int MSG_GET_NOVEL_SUCCESS=1;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GET_NOVEL_SUCCESS:
                    bookStarRecyclerViewAdapter=new BookStarRecyclerViewAdapter(getApplicationContext(),book_post_list,book_name_list,book_author_list,book_main_kind_list,book_detail_kind_list,book_shortcontent_list);
                    bookStarRecyclerViewAdapter.setOnItemClickListener(new BookStarRecyclerViewAdapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(View view, int position) {
                            /*String path=book_path_list.get(position);
                            HwTxtPlayActivity.loadTxtFile(getApplicationContext(), path);*/
                        }
                    });



                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(bookStarRecyclerViewAdapter);
                    DialogThridUtils.closeDialog(dialog);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        dialog = WeiboDialogUtils.createLoadingDialog(Activity_Star.this, "加载中...");
        getBookStar();
    }

    private void getBookStar(){
        new Thread(){
            @Override
            public void run() {

                List<NameValuePair> postParam = new ArrayList<>();
                SharedPreferences sp=getApplicationContext().getSharedPreferences("QQFile", Context.MODE_PRIVATE);
                String user_id=sp.getString("flag","");
                postParam.add(new NameValuePair("user_id",user_id));
                bookList= HttpCaller.getInstance().postSyncList(Book.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_User_Star_Servlet",postParam);



                book_name_list=new LinkedList<>();
                book_post_list=new LinkedList<>();
                book_author_list=new LinkedList<>();
                book_shortcontent_list=new LinkedList<>();
                book_main_kind_list=new LinkedList<>();
                book_detail_kind_list=new LinkedList<>();
                book_path_list=new LinkedList<>();
                Gson gson=new Gson();
                for(int i=0;i<bookList.size();i++){
                    String jsonStr=gson.toJson(bookList.get(i));
                    Book book=new Book();
                    book=gson.fromJson(jsonStr,Book.class);
                    book_name_list.add(book.getBook_name());
                    book_post_list.add(book.getBook_cover_path());
                    book_author_list.add(book.getBook_author());
                    book_shortcontent_list.add(" ");
                    book_main_kind_list.add(book.getBook_main_kind());
                    book_detail_kind_list.add(book.getBook_detail_kind());
                    book_path_list.add(book.getBook_path());
                }
                Message message=new Message();
                message.what=MSG_GET_NOVEL_SUCCESS;
                handler.sendMessage(message);



            }
        }.start();
    }




}
