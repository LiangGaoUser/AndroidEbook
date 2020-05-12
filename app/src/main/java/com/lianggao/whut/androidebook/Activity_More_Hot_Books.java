package com.lianggao.whut.androidebook;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lianggao.whut.androidebook.Adapter.LoadMoreBookAdapter;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.View.LoadMoreHotBookRecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Activity_More_Hot_Books extends FragmentActivity {
   // private RecyclerView recyclerView;
    private LoadMoreHotBookRecyclerView loadMoreHotBookRecyclerView;
    private LoadMoreBookAdapter loadMoreBookAdapter;
    private Button button;
    private TextView returnTextView;
    private List<String> book_post_path_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_main_kind;
    private List<String>book_detail_kind;

    private final int MSG_GET_MUTIBITMAP_SUCCESS=1;
    private FragmentActivity fragmentActivity;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_MUTIBITMAP_SUCCESS:
                    Log.i("获取图片","批量获取图片成功");


                    loadMoreBookAdapter=new LoadMoreBookAdapter(getApplicationContext(),36,book_post_path_list,book_name_list,book_author_list,book_shortcontent_list,book_main_kind,book_detail_kind);
                    loadMoreHotBookRecyclerView.setManager();
                    loadMoreBookAdapter.setOnItemClickListener(new LoadMoreBookAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getApplicationContext(),"点击了"+position+"本图书",Toast.LENGTH_SHORT).show();
                        }
                    });
                    loadMoreHotBookRecyclerView.setLoadMoreBookAdapter(loadMoreBookAdapter);




                    Log.i("初始化","初始化结束");
                    break;
            }
        }
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_hot_books);
        //recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        loadMoreHotBookRecyclerView=(LoadMoreHotBookRecyclerView)findViewById(R.id.loadMoreHotBookRecyclerView);
        fragmentActivity= this.fragmentActivity;

        //返回
        returnTextView=(TextView)findViewById(R.id.returnTextView);
        Drawable back= getResources().getDrawable(R.drawable.icon_book_return2);
        back.setBounds(0, 0, 50, 50);
        returnTextView.setCompoundDrawables(back, null, null, null);
        returnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        postSync();



    }
    private void postSync(){
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("username","lianggao"));
                postParam.add(new NameValuePair("password","12"));
                postParam.add(new NameValuePair("action","postAction"));




                List<Book> userList2 ;
                userList2=HttpCaller.getInstance().postSyncList(Book.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Hot_Servlet",postParam);
                Log.i("用户4",userList2+"");

                book_post_path_list=new LinkedList<String>();
                book_name_list=new LinkedList<String>();
                book_author_list=new LinkedList<String>();
                book_shortcontent_list=new LinkedList<String>();
                book_main_kind=new LinkedList<>();
                book_detail_kind=new LinkedList<>();

                Gson gson=new Gson();

                for(int i=0;i<userList2.size();i++){
                    String jsonStr=gson.toJson(userList2.get(i));
                    Book book=new Book();
                    book=gson.fromJson(jsonStr,Book.class);
                    book_post_path_list.add(book.getBook_cover_path());
                    book_author_list.add(book.getBook_author());
                    book_name_list.add(book.getBook_name());
                    book_shortcontent_list.add(book.getBook_short_content_path());
                    book_main_kind.add(book.getBook_main_kind());
                    book_detail_kind.add(book.getBook_detail_kind());

                    Log.i("用户输出",book.getBook_name()+book.getBook_author());

                }



                Message message=new Message();
                message.what=MSG_GET_MUTIBITMAP_SUCCESS;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }
}
