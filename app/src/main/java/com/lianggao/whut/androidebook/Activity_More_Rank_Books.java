package com.lianggao.whut.androidebook;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.google.gson.Gson;
import com.lianggao.whut.androidebook.Adapter.LoadMoreBookAdapter;
import com.lianggao.whut.androidebook.Adapter.NetRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.View.LoadMoreBookRecyclerView;
import com.lianggao.whut.androidebook.View.LoadMoreRankBookRecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Activity_More_Rank_Books extends FragmentActivity {
   // private RecyclerView recyclerView;
    private LoadMoreRankBookRecyclerView loadMoreRankBookRecyclerView;
    private LoadMoreBookAdapter loadMoreBookAdapter;
    private NetRecyclerViewAdapter recyclerViewAdapter;
    private Button button;
    private TextView returnTextView;
    private List<String> book_post_path_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_kind_list;//书的种类集合
    private List<Integer>book_id_list;/////////////////////////////



    private final int MSG_GET_MUTIBITMAP_SUCCESS=1;
    private FragmentActivity fragmentActivity;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_MUTIBITMAP_SUCCESS:
                    Log.i("获取图片","批量获取图片成功");
                    /*recyclerViewAdapter=new NetRecyclerViewAdapter(getApplicationContext(),book_post_path_list,book_name_list,book_author_list,book_kind_list,book_shortcontent_list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(recyclerViewAdapter);*/

                    loadMoreBookAdapter=new LoadMoreBookAdapter(getApplicationContext(),36,book_post_path_list,book_name_list,book_author_list,book_shortcontent_list,book_kind_list,book_id_list);
                    loadMoreRankBookRecyclerView.setManager();
                    loadMoreBookAdapter.setOnItemClickListener(new LoadMoreBookAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getApplicationContext(),"点击了"+position+"本图书",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Activity_More_Rank_Books.this, Activity_BookDetail.class);
                            Book book=new Book();

                            book.setBook_id(loadMoreBookAdapter.getBookIdList().get(position));///////////////////////////////
                            book.setBook_cover_path(loadMoreBookAdapter.getBookPostList().get(position));
                            book.setBook_name(loadMoreBookAdapter.getBookNameList().get(position));
                            book.setBook_short_content_path(loadMoreBookAdapter.getBookShortContentList().get(position));
                            book.setBook_author(loadMoreBookAdapter.getBookAuthorList().get(position));
                            intent.putExtra("book",book);
                            startActivity(intent);
                        }
                    });
                    loadMoreRankBookRecyclerView.setLoadMoreBookAdapter(loadMoreBookAdapter);




                    Log.i("初始化","初始化结束");
                    break;
            }
        }
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_rank_books);
        //recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        loadMoreRankBookRecyclerView=(LoadMoreRankBookRecyclerView) findViewById(R.id.loadMoreRankBookRecyclerView);
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
                userList2=HttpCaller.getInstance().postSyncList(Book.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Rank_Servlet",postParam);
                Log.i("用户4",userList2+"");

                book_post_path_list=new LinkedList<String>();
                book_name_list=new LinkedList<String>();
                book_author_list=new LinkedList<String>();
                book_shortcontent_list=new LinkedList<String>();
                book_kind_list=new LinkedList<>();
                book_id_list=new LinkedList<>();///////////////////////////////////////////
                Gson gson=new Gson();

                for(int i=0;i<userList2.size();i++){
                    String jsonStr=gson.toJson(userList2.get(i));
                    Book book=new Book();
                    book=gson.fromJson(jsonStr,Book.class);
                    book_id_list.add(book.getBook_id());/////////////////////////////////////////
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
}
