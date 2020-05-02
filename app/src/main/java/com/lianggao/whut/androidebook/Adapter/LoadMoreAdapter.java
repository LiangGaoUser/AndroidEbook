package com.lianggao.whut.androidebook.Adapter;

/**
 * @author LiangGao
 * @description:在书的种类界面用来显示recycler列表，LoadMoreRecyclerView
 * @data:${DATA} 21:50
 */

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.ViewHolder.CommonRcViewHolder;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public  class LoadMoreAdapter extends RecyclerView.Adapter<CommonRcViewHolder> {
    public static final int ITEM_TYPE_FOOTER = 0;
    protected String loadMoreText = "加载更多";
    private LinkedList<String> bookPostList;
    private LinkedList<String> bookNameList;
    private LinkedList<String> bookShortContentList;
    private LinkedList<String> bookAuthorList;
    private LinkedList<String> bookKindList;

    private int bookKind;//专门记录书的种类
    private final int MSG_GET_KIND_1_SUCCESS=1;
    private final int MSG_GET_KIND_2_SUCCES=2;
    private Context context;
    private OnItemClickListener onItemClickListener;//接口1
    private OnItemLongClickListener onItemLongClickListener;//接口2
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_KIND_1_SUCCESS:
                    Log.i("获取图片","批量获取图片成功");
                    int startPosition = bookNameList.size();
                    notifyItemRangeInserted(startPosition,6);
                    Log.i("初始化","初始化结束");
                    break;
                case MSG_GET_KIND_2_SUCCES:
                    Log.i("获取图片","批量获取图片成功");
                    int startPosition2 = bookNameList.size();
                    notifyItemRangeInserted(startPosition2,6);
                    Log.i("初始化","初始化结束");
                    break;
            }
        }
    };

    public LoadMoreAdapter(Context context) {
        this.context=context;
        bookPostList=new LinkedList<>();
        bookNameList = new LinkedList<>();
        bookShortContentList = new LinkedList<>();
        bookAuthorList = new LinkedList<>();
        bookKindList = new LinkedList<>();
        //setBookKind(1);
       /* for (int i = 0; i < 10; i++) {
            bookPostList.add("http://192.168.1.4:8080/com.lianggao.whut/Images_recommend/recommend1.jpg");
            bookNameList.add("放风筝的人 "+i+"");
            bookShortContentList.add("放风筝的人的简介 "+i+"");
            bookAuthorList.add("放风筝的人的作者"+i);
            bookKindList.add("种类 "+i+"");
        }*/
    }


    /* public LoadMoreAdapter(){
         bookPostList=new LinkedList<>();
         bookNameList = new LinkedList<>();
         bookShortContentList = new LinkedList<>();
         bookAuthorList = new LinkedList<>();
         bookKindList = new LinkedList<>();
         for (int i = 0; i < 10; i++) {
             bookPostList.add(R.drawable.img_bookshelf_everybook);
             bookNameList.add("放风筝的人 "+i+"");
             bookShortContentList.add("放风筝的人的简介 "+i+"");
             bookAuthorList.add("放风筝的人的作者"+i);
             bookKindList.add("种类 "+i+"");
         }
     }*/

    public void setBookKind(int bookKind){
        this.bookKind=bookKind;

        bookNameList.clear();
        bookKindList.clear();
        bookAuthorList.clear();
        bookPostList.clear();
        bookShortContentList.clear();

        notifyItemRangeInserted(0,0);



        switch (bookKind){
            case 1:
                LoadKind1();
                break;
            case 2:
                LoadKind2();
                break;
            case 3:
                LoadKind3();
                break;
            case 4:
                LoadKind4();
                break;
            case 5:
                LoadKind5();
                break;
            case 6:
                LoadKind6();
                break;

        }





    }

    public void loadMore(){



        switch (bookKind){
            case 1:
                if(bookNameList.size()<136){
                    LoadMore1();
                }else{
                    setLoadMoreText("没有更多了");
                }
                break;
            case 2:
                if(bookNameList.size()<136){
                    LoadMore2();
                }else{
                    setLoadMoreText("没有更多了");
                }
                break;
            case 3:
                if(bookNameList.size()<136){
                    LoadMore3();
                }else{
                    setLoadMoreText("没有更多了");
                }
                break;
            case 4:
                if(bookNameList.size()<136){
                    LoadMore4();
                }else{
                    setLoadMoreText("没有更多了");
                }
                break;
            case 5:
                if(bookNameList.size()<136){
                    LoadMore5();
                }else{
                    setLoadMoreText("没有更多了");
                }
                break;
            case 6:
                if(bookNameList.size()<136){
                    LoadMore6();
                }else{
                    setLoadMoreText("没有更多了");
                }
                break;

        }








    }
    public void LoadKind1() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("action", "loadMore"));


                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Rank_Servlet", postParam);
                Log.i("用户4", userList2 + "");


                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_1_SUCCESS;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }


    public void LoadKind2() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("action", "loadMore"));


                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Rank_Servlet", postParam);
                Log.i("用户4", userList2 + "");


                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_1_SUCCESS;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }













    public void LoadKind3() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("action", "loadMore"));


                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Rank_Servlet", postParam);
                Log.i("用户4", userList2 + "");


                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_1_SUCCESS;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    public void LoadKind4() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("action", "loadMore"));


                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Rank_Servlet", postParam);
                Log.i("用户4", userList2 + "");


                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_1_SUCCESS;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    public void LoadKind5() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("action", "loadMore"));


                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Rank_Servlet", postParam);
                Log.i("用户4", userList2 + "");


                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_1_SUCCESS;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    public void LoadKind6() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("action", "loadMore"));


                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Rank_Servlet", postParam);
                Log.i("用户4", userList2 + "");


                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_1_SUCCESS;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    //判断不同的类型，是否到了页脚
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1){
            return ITEM_TYPE_FOOTER;
        }else {
            return 1;
        }
    }
    //判断不同类型创建item的view
    @Override
    public CommonRcViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.part_item_footer,parent,false);
            return new CommonRcViewHolder(view);//如果是最后的数据，则返回页脚的布局
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.part_activity_book_recycler_more, parent,false);
            return new CommonRcViewHolder(view);//不是最后的数据，返回空白的布局
        }
    }
    //设置数据
    @Override
    public void onBindViewHolder(CommonRcViewHolder holder, final int position) {
        if (getItemViewType(position) != ITEM_TYPE_FOOTER){

            //TextView tv = holder.getView(R.id.tv);
            //tv.setText(mData.get(position));
            final ImageView imageView=holder.getView(R.id.id_tv_book_post);
            TextView tv = holder.getView(R.id.id_tv_book_name);
            TextView tv2 = holder.getView(R.id.id_tv_book_shortcontent);
            TextView tv3 = holder.getView(R.id.id_tv_book_author);
            TextView tv4 = holder.getView(R.id.id_tv_book_kind);
            //imageView.setImageResource(bookPostList.get(position));

            if(onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(imageView,position);
                    }
                });

            }
            if(onItemLongClickListener!=null){
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                    @Override
                    public boolean onLongClick(View v) {
                        onItemLongClickListener.onItemLongClick(imageView,position);
                        return true;
                    }
                });

            }

            if(position>bookPostList.size()-1){//bookPostList.get(position)==null||
                Picasso
                        .with(context)
                        .load(R.drawable.img_booklist_recommend1)
                        .into(imageView);

            }else{
                Picasso
                        .with(context)
                        .load(bookPostList.get(position))
                        .placeholder(R.drawable.icon_arrow_return)//占位符
                        .error(R.drawable.img_bookshelf_everybook)//链接失效是加载的图片
                        .into(imageView);
            }
            tv.setText(bookNameList.get(position));
            tv2.setText(bookShortContentList.get(position));
            tv3.setText(bookAuthorList.get(position));
            tv4.setText(bookKindList.get(position));


        }else {
            TextView tv = holder.getView(R.id.tv);
            tv.setText(loadMoreText);//设置加载更多和没有更多了
        }
    }

    public void LoadMore1() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("kind", "1"));
                postParam.add(new NameValuePair("currentTotalNumber", "10"));
                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Load_More_Servlet", postParam);
                Log.i("用户4", userList2 + "");

                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_2_SUCCES;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return  bookNameList.size()+1;
    }

    public void setLoadMoreText(String loadMoreText) {
        this.loadMoreText = loadMoreText;
        notifyItemChanged(getItemCount()-1);
    }

    public void LoadMore2() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("kind", "2"));
                postParam.add(new NameValuePair("currentTotalNumber", "10"));
                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Load_More_Servlet", postParam);
                Log.i("用户4", userList2 + "");

                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_2_SUCCES;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    /*public void loadMore() {
        int startPosition = bookNameList.size();
        if (bookNameList.size() < 100) {
            for (int i = 0; i < 10; i++) {
                bookPostList.add(R.drawable.img_bookshelf_everybook);
                bookNameList.add("more "+i + "");
                bookShortContentList.add("more "+i + "");
                bookAuthorList.add("more "+i + "");
                bookKindList.add("more "+i + "");

            }
            int endPosition = bookNameList.size()-1;
            *//*View view=new View();
            view.post(new Runnable() {
                @Override
                public void run() {

                }
            })*//*
            notifyItemRangeInserted(startPosition,10);
        }else {
            setLoadMoreText("没有更多了");
        }
    }*/

    public void LoadMore3() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("kind", "3"));
                postParam.add(new NameValuePair("currentTotalNumber", "10"));
                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Load_More_Servlet", postParam);
                Log.i("用户4", userList2 + "");

                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_2_SUCCES;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    public void LoadMore4() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("kind", "4"));
                postParam.add(new NameValuePair("currentTotalNumber", "10"));
                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Load_More_Servlet", postParam);
                Log.i("用户4", userList2 + "");

                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_2_SUCCES;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    public void LoadMore5() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("kind", "5"));
                postParam.add(new NameValuePair("currentTotalNumber", "10"));
                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Load_More_Servlet", postParam);
                Log.i("用户4", userList2 + "");

                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_2_SUCCES;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    public void LoadMore6() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("kind", "6"));
                postParam.add(new NameValuePair("currentTotalNumber", "10"));
                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Load_More_Servlet", postParam);
                Log.i("用户4", userList2 + "");

                Gson gson = new Gson();

                for (int i = 0; i < userList2.size(); i++) {
                    String jsonStr = gson.toJson(userList2.get(i));
                    Book book = new Book();
                    book = gson.fromJson(jsonStr, Book.class);
                    bookPostList.add(book.getBook_cover_path());
                    bookAuthorList.add(book.getBook_author());
                    bookNameList.add(book.getBook_name());
                    bookShortContentList.add(book.getBook_short_content_path());
                    bookKindList.add("文学名著");
                    Log.i("用户输出", book.getBook_name() + book.getBook_author());

                }


                Message message = new Message();
                message.what = MSG_GET_KIND_2_SUCCES;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }
    public List<String> getBookPostList(){
        return bookPostList;
    }
    public List<String>getBookNameList(){
        return bookNameList;
    }
    public List<String>getBookAuthorList(){
        return bookAuthorList;
    }
    public List<String>getBookShortContentList(){
        return bookShortContentList;
    }
    public List<String>getBookKindList(){
        return bookKindList;
    }
}

