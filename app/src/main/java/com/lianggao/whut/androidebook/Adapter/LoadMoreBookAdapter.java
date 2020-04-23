package com.lianggao.whut.androidebook.Adapter;

/**
 * @author LiangGao
 * @description:由LoadMoreAdapter修改而来，用来显示详细的书籍列表，这里改成参数传递的形式
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


public  class LoadMoreBookAdapter extends RecyclerView.Adapter<CommonRcViewHolder> {
    public static final int ITEM_TYPE_FOOTER = 0;
    protected String loadMoreText = "加载更多";
    private List<String> bookPostList;
    private List<String> bookNameList;
    private List<String> bookShortContentList;
    private List<String> bookAuthorList;
    private List<String> bookKindList;
    private Context context;
    private int totalNumber;//总的数目
    private int currentTotalNumber;//现在已有的数目
    private final int MSG_GET_MUTIBITMAP_SUCCESS=1;


    private OnItemClickListener onItemClickListener;//接口1
    private OnItemLongClickListener onItemLongClickListener;//接口2


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_MUTIBITMAP_SUCCESS:
                    Log.i("获取图片","批量获取图片成功");
                    int startPosition = bookNameList.size();
                    if (currentTotalNumber<totalNumber) {
                        int endPosition = bookNameList.size()-1;
                        notifyItemRangeInserted(startPosition,6);
                    }else {
                        setLoadMoreText("没有更多了");
                    }
                    Log.i("初始化","初始化结束");
                    break;
            }
        }
    };
    public LoadMoreBookAdapter(Context context, int totalNumber,List<String>bookPostList, List<String> bookNameList, List<String>bookAuthorList, List<String>bookShortContentList, List<String>bookKindList){
        bookPostList=new LinkedList<>();
        bookNameList = new LinkedList<>();
        bookShortContentList = new LinkedList<>();
        bookAuthorList = new LinkedList<>();
        bookKindList = new LinkedList<>();
        this.context=context;
        this.bookNameList=bookNameList;
        this.bookAuthorList=bookAuthorList;
        this.bookPostList=bookPostList;
        this.bookShortContentList=bookShortContentList;
        this.bookKindList=bookKindList;
        this.totalNumber=totalNumber;
        this.currentTotalNumber=bookNameList.size();

    }

    public void LoadMore(List<String>addBookPostList,List<String> addBookNameList,List<String>addBookAuthorList,List<String>addBookShortContentList,List<String>addBookKindList){
        for(int i=0;i<addBookNameList.size();i++){
            bookNameList.add(addBookNameList.get(i));
            bookAuthorList.add(addBookAuthorList.get(i));
            bookPostList.add(addBookPostList.get(i));
            bookShortContentList.add(addBookShortContentList.get(i));
            bookKindList.add(addBookKindList.get(i));
        }
        int startPosition = bookNameList.size();
        if (addBookNameList.size() < 100) {
            int endPosition = bookNameList.size()-1;
            notifyItemRangeInserted(startPosition,10);
        }else {
            setLoadMoreText("没有更多了");
        }
    }

    public void LoadMore() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("action", "loadMore"));
                postParam.add(new NameValuePair("currentTotalNumber",Integer.toString(currentTotalNumber)));

                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Load_More_Servlet", postParam);
                Log.i("用户4", userList2 + "");
                currentTotalNumber+=userList2.size();

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
                message.what = MSG_GET_MUTIBITMAP_SUCCESS;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }


    public void LoadMoreRecommendBook() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("kind", "Recommend"));
                postParam.add(new NameValuePair("currentTotalNumber",Integer.toString(currentTotalNumber)));

                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Load_More_Servlet", postParam);
                Log.i("用户4", userList2 + "");
                currentTotalNumber+=userList2.size();

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
                message.what = MSG_GET_MUTIBITMAP_SUCCESS;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }

    public void LoadMoreHotBook() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("kind", "Hot"));
                postParam.add(new NameValuePair("currentTotalNumber",Integer.toString(currentTotalNumber)));

                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Load_More_Servlet", postParam);
                Log.i("用户4", userList2 + "");
                currentTotalNumber+=userList2.size();

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
                message.what = MSG_GET_MUTIBITMAP_SUCCESS;
                handler.sendMessage(message);
                //message.sendToTarget();
                Looper.loop();
            }
        }.start();
    }







    public void LoadMoreRankBook() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("kind", "Rank"));
                postParam.add(new NameValuePair("currentTotalNumber",Integer.toString(currentTotalNumber)));

                List<Book> userList2;
                userList2 = HttpCaller.getInstance().postSyncList(Book.class, "http://192.168.1.4:8080/com.lianggao.whut/Get_Load_More_Servlet", postParam);
                Log.i("用户4", userList2 + "");
                currentTotalNumber+=userList2.size();

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
                message.what = MSG_GET_MUTIBITMAP_SUCCESS;
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


            if(bookPostList.get(position)==null){
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

    @Override
    public int getItemCount() {
        return  bookNameList.size()+1;
    }

    public void setLoadMoreText(String loadMoreText) {
        this.loadMoreText = loadMoreText;
        notifyItemChanged(getItemCount()-1);
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




}

