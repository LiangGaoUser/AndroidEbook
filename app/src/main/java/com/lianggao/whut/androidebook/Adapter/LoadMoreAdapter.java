package com.lianggao.whut.androidebook.Adapter;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 21:50
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.ViewHolder.CommonRcViewHolder;


import java.util.LinkedList;


public  class LoadMoreAdapter extends RecyclerView.Adapter<CommonRcViewHolder> {
    public static final int ITEM_TYPE_FOOTER = 0;
    protected String loadMoreText = "加载更多";
    private LinkedList<Integer> bookPostList;
    private LinkedList<String> bookNameList;
    private LinkedList<String> bookShortContentList;
    private LinkedList<String> bookAuthorList;
    private LinkedList<String> bookKindList;
    public LoadMoreAdapter(){
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
    public void onBindViewHolder(CommonRcViewHolder holder, int position) {
        if (getItemViewType(position) != ITEM_TYPE_FOOTER){

            //TextView tv = holder.getView(R.id.tv);
            //tv.setText(mData.get(position));
            ImageView imageView=holder.getView(R.id.id_tv_book_post);
            TextView tv = holder.getView(R.id.id_tv_book_name);
            TextView tv2 = holder.getView(R.id.id_tv_book_shortcontent);
            TextView tv3 = holder.getView(R.id.id_tv_book_author);
            TextView tv4 = holder.getView(R.id.id_tv_book_kind);
            imageView.setImageResource(bookPostList.get(position));
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


    public void loadMore() {
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
            /*View view=new View();
            view.post(new Runnable() {
                @Override
                public void run() {

                }
            })*/
            notifyItemRangeInserted(startPosition,10);
        }else {
            setLoadMoreText("没有更多了");
        }
    }



}

