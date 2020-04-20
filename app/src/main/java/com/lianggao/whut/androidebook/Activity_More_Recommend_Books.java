package com.lianggao.whut.androidebook;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lianggao.whut.androidebook.Adapter.NetRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Adapter.RecyclerViewAdapter;
import com.lianggao.whut.androidebook.Entity.User;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class Activity_More_Recommend_Books extends FragmentActivity {
    private RecyclerView recyclerView;
    private NetRecyclerViewAdapter recyclerViewAdapter;
    private Button button;
    private List<String> book_post_path_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_kind_list;//书的种类集合

    private final int MSG_GET_MUTIBITMAP_SUCCESS=1;
    private FragmentActivity fragmentActivity;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_MUTIBITMAP_SUCCESS:
                    Log.i("获取图片","批量获取图片成功");
                    recyclerViewAdapter=new NetRecyclerViewAdapter(getApplicationContext(),book_post_path_list,book_name_list,book_author_list,book_kind_list,book_shortcontent_list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(recyclerViewAdapter);
                    Log.i("初始化","初始化结束");
                    break;
            }
        }
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_recommend_books);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        button=(Button)findViewById(R.id.button);
        fragmentActivity= this.fragmentActivity;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSync();
            }
        });



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
                //com.lianggao.whut.thirdtest.entity.User user=HttpCaller.getInstance().postSync(com.lianggao.whut.thirdtest.entity.User.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_Resopnce_Servlet",postParam);
                //Log.i("ansen","post sync 用户name:"+user.getUsername());
                /*List<User> userList=HttpCaller.getInstance().postSyncList(User.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_Resopnce_Servlet",postParam);
                for(int i=0;i<userList.size();i++){
                    System.out.println(userList.get(i));
                    Log.i("用户",userList.get(i)+"");
                }*/

                List<Book> userList2 ;
                userList2=HttpCaller.getInstance().postSyncList(Book.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Recommend_Servlet",postParam);
                Log.i("用户4",userList2+"");

                book_post_path_list=new LinkedList<String>();
                book_name_list=new LinkedList<String>();
                book_author_list=new LinkedList<String>();
                book_shortcontent_list=new LinkedList<String>();
                book_kind_list=new LinkedList<>();
                Gson gson=new Gson();

                for(int i=0;i<userList2.size();i++){
                    String jsonStr=gson.toJson(userList2.get(i));
                    Book book=new Book();
                    book=gson.fromJson(jsonStr,Book.class);
                    book_post_path_list.add(book.getBook_cover_path());
                    book_author_list.add(book.getBook_author());
                    book_name_list.add(book.getBook_name());
                    book_shortcontent_list.add(book.getBook_short_content_path());
                    book_kind_list.add("文学名著");
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
    public void initdata(){
        book_post_path_list=new LinkedList<>();
        book_name_list=new LinkedList<>();
        book_author_list=new LinkedList<>();
        book_shortcontent_list=new LinkedList<>();
        book_kind_list=new LinkedList<>();

        new Thread(){
            @Override
            public void run() {
                Looper.prepare();

                List<NameValuePair>postParam=new ArrayList<>();
                postParam.add(new NameValuePair("username","lianggao"));
                postParam.add(new NameValuePair("password","12"));
                postParam.add(new NameValuePair("action","postAction"));

                List<User>userList2;
                userList2=HttpCaller.getInstance().postSyncList(User.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_Responce_Servlet",postParam);
                for(int i=0;i<userList2.size();i++){
                    Log.i("用户6",userList2.get(i)+"");
                }

/*
                List<Book> userList ;
                userList=HttpCaller.getInstance().postSyncList(Book.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Recommend_Servlet",postParam);
                Log.i("用户4",userList+"");
                for(int i=0;i<userList.size();i++){
                    Log.i("用户3",userList.get(i)+"");
                    book_name_list.add(userList.get(i).getBook_name());
                    book_author_list.add(userList.get(i).getBook_author());
                    book_shortcontent_list.add(userList.get(i).getBook_short_content_path());
                    book_post_path_list.add(userList.get(i).getBook_cover_path());
                }
                Message message=new Message();
                message.what=MSG_GET_MUTIBITMAP_SUCCESS;
                message.sendToTarget();*/
                Looper.loop();
            }
        }.start();





        /*book_post_list=new LinkedList<>();
        book_name_list=new LinkedList<>();
        book_author_list=new LinkedList<>();
        book_shortcontent_list=new LinkedList<>();
        book_kind_list=new LinkedList<>();
        String url="http://192.168.1.4:8080/com.lianggao.whut/recommendImages/recommend3.jpg";
        for(int i=0;i<200;i++){
            book_author_list.add("梁高");
            book_name_list.add("离开的每一种方式");
            book_post_list.add(R.drawable.img_bookstore_book_recommend);
            book_shortcontent_list.add("是法国作家安托万·德·圣·埃克苏佩里于1942年写成的著名儿童文学短篇小说。本书的主人公是来自外星球的小王子。书中以一位飞行员作为故事叙述者，讲述了小王子从自己星球出发前往地球的过程中，所经历的各种历险");
            book_kind_list.add("世界名著");
        }*/
    }
}
