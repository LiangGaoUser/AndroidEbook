package com.lianggao.whut.androidebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lianggao.whut.androidebook.Adapter.RecyclerViewAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author LiangGao
 * @description:循环视图的Activity  acticity_recycler.xml   part_activity_book_recycler.xml RecyclerViewAdapter.java ||fragment_bookstroe_book.xml修改加入进
 * fragment_bookstore_book.xml中，fragmentBookStroeBook.java中进行初始化
 * @data:${DATA} 17:02
 */
public class Activity_RecyclerView extends FragmentActivity {
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private List<Integer>book_post_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_kind_list;//书的种类集合
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView=(RecyclerView)findViewById(R.id.id_book_recyclerview);
        initData();
        recyclerViewAdapter=new RecyclerViewAdapter(getApplicationContext(),book_post_list,book_name_list,book_author_list,book_kind_list,book_shortcontent_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    void initData(){
         book_post_list=new LinkedList<>();
         book_name_list=new LinkedList<>();
         book_author_list=new LinkedList<>();
         book_shortcontent_list=new LinkedList<>();
         book_kind_list=new LinkedList<>();
        for(int i=0;i<9;i++){
            book_author_list.add("梁高");
            book_name_list.add("离开的每一种方式");
            book_post_list.add(R.drawable.img_bookshelf_everybook);
            book_shortcontent_list.add("是法国作家安托万·德·圣·埃克苏佩里于1942年写成的著名儿童文学短篇小说。本书的主人公是来自外星球的小王子。书中以一位飞行员作为故事叙述者，讲述了小王子从自己星球出发前往地球的过程中，所经历的各种历险");
            book_kind_list.add("世界名著");
        }
    }
}
