package com.lianggao.whut.androidebook.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Activity_BookDetail;
import com.lianggao.whut.androidebook.Activity_More_Rank_Books;
import com.lianggao.whut.androidebook.Adapter.KindRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Adapter.LoadMoreAdapter;
import com.lianggao.whut.androidebook.Adapter.LoadMoreBookAdapter;
import com.lianggao.whut.androidebook.Adapter.RecyclerViewAdapter;
import com.lianggao.whut.androidebook.Adapter.TextRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.View.LoadMoreRecyclerView;


import java.util.LinkedList;
import java.util.List;

import static com.bigkoo.convenientbanner.utils.ScreenUtil.dip2px;
import static org.litepal.LitePalApplication.getContext;

public class FragmentBookStoreKind extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    //private RecyclerView recyclerView1;
    private TextRecyclerViewAdapter textrecyclerViewAdapter;
   // private RecyclerViewAdapter recyclerViewAdapter;
   // private KindRecyclerViewAdapter kindRecyclerViewAdapter;
    private LoadMoreRecyclerView loadMoreRecyclerView;
    private LoadMoreAdapter loadMoreAdapter;


    private List<String> bookKindList;
    private List<Integer> book_post_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_kind_list;//书的种类集合

    private View recyclekindView;
    private View recyclebookView;
    private View linerlayout_book;
    private View linerlayout_kind;
    private String[] kindList={"小说","文学","社科","计算机","励志","经济"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_bookstore_kind,null);

        recyclekindView=(RecyclerView)rootView.findViewById(R.id.id_recycleview_kind);
        recyclebookView=(RecyclerView)rootView.findViewById(R.id.id_recycleview_book);
        linerlayout_kind=(LinearLayout)rootView.findViewById(R.id.id_linerlayout_kind);
        linerlayout_book=(LinearLayout)rootView.findViewById(R.id.id_linerlayout_book);


        initData();
        textrecyclerViewAdapter=new TextRecyclerViewAdapter(getContext(),bookKindList);
        textrecyclerViewAdapter.setOnItemClickListener(new TextRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Log.i("!!!+",position+"");
                textrecyclerViewAdapter.setKindNumber(position);
                textrecyclerViewAdapter.notifyDataSetChanged();
                if(position==0){loadMoreAdapter.setBookKind(position+1);
                    Toast.makeText(getContext(),"点击了小说",Toast.LENGTH_LONG).show();

                    loadMoreRecyclerView=(LoadMoreRecyclerView)rootView.findViewById(R.id.id_recycleview_book);
                    loadMoreAdapter=new LoadMoreAdapter(getContext());
                    loadMoreAdapter.setBookKind(position+1);
                    loadMoreAdapter.setOnItemClickListener(new LoadMoreAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getContext(),"点击了"+position+"本图书",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getContext(), Activity_BookDetail.class);
                            Book book=new Book();
                            book.setBook_cover_path(loadMoreAdapter.getBookPostList().get(position));
                            book.setBook_name(loadMoreAdapter.getBookNameList().get(position));
                            book.setBook_short_content_path(loadMoreAdapter.getBookShortContentList().get(position));
                            book.setBook_author(loadMoreAdapter.getBookAuthorList().get(position));
                            book.setBook_main_kind(loadMoreAdapter.getBookMainKindList().get(position));
                            book.setBook_detail_kind(loadMoreAdapter.getBookDetailKindList().get(position));
                            intent.putExtra("book",book);
                            startActivity(intent);
                        }
                    });
                    loadMoreRecyclerView.setManager();
                    loadMoreRecyclerView.setLoadMoreAdapter(loadMoreAdapter);
                }else if(position==1){
                    Toast.makeText(getContext(),"点击了文学",Toast.LENGTH_LONG).show();
                    loadMoreRecyclerView=(LoadMoreRecyclerView)rootView.findViewById(R.id.id_recycleview_book);
                    loadMoreAdapter=new LoadMoreAdapter(getContext());
                    loadMoreAdapter.setBookKind(position+1);
                    loadMoreAdapter.setOnItemClickListener(new LoadMoreAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getContext(),"点击了"+position+"本图书",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getContext(), Activity_BookDetail.class);
                            Book book=new Book();
                            book.setBook_cover_path(loadMoreAdapter.getBookPostList().get(position));
                            book.setBook_name(loadMoreAdapter.getBookNameList().get(position));
                            book.setBook_short_content_path(loadMoreAdapter.getBookShortContentList().get(position));
                            book.setBook_author(loadMoreAdapter.getBookAuthorList().get(position));
                            book.setBook_main_kind(loadMoreAdapter.getBookMainKindList().get(position));
                            book.setBook_detail_kind(loadMoreAdapter.getBookDetailKindList().get(position));
                            intent.putExtra("book",book);
                            startActivity(intent);
                        }
                    });
                    loadMoreRecyclerView.setManager();
                    loadMoreRecyclerView.setLoadMoreAdapter(loadMoreAdapter);
                }else if(position==2){
                    Toast.makeText(getContext(),"点击了社科",Toast.LENGTH_LONG).show();
                    loadMoreRecyclerView=(LoadMoreRecyclerView)rootView.findViewById(R.id.id_recycleview_book);
                    loadMoreAdapter=new LoadMoreAdapter(getContext());
                    loadMoreAdapter.setBookKind(position+1);
                    loadMoreAdapter.setOnItemClickListener(new LoadMoreAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getContext(),"点击了"+position+"本图书",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getContext(), Activity_BookDetail.class);
                            Book book=new Book();
                            book.setBook_cover_path(loadMoreAdapter.getBookPostList().get(position));
                            book.setBook_name(loadMoreAdapter.getBookNameList().get(position));
                            book.setBook_short_content_path(loadMoreAdapter.getBookShortContentList().get(position));
                            book.setBook_author(loadMoreAdapter.getBookAuthorList().get(position));
                            book.setBook_main_kind(loadMoreAdapter.getBookMainKindList().get(position));
                            book.setBook_detail_kind(loadMoreAdapter.getBookDetailKindList().get(position));
                            intent.putExtra("book",book);
                            startActivity(intent);
                        }
                    });
                    loadMoreRecyclerView.setManager();
                    loadMoreRecyclerView.setLoadMoreAdapter(loadMoreAdapter);
                }else if(position==3){
                    Toast.makeText(getContext(),"点击了计算机",Toast.LENGTH_LONG).show();
                    loadMoreRecyclerView=(LoadMoreRecyclerView)rootView.findViewById(R.id.id_recycleview_book);
                    loadMoreAdapter=new LoadMoreAdapter(getContext());
                    loadMoreAdapter.setBookKind(position+1);
                    loadMoreAdapter.setOnItemClickListener(new LoadMoreAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getContext(),"点击了"+position+"本图书",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getContext(), Activity_BookDetail.class);
                            Book book=new Book();
                            book.setBook_cover_path(loadMoreAdapter.getBookPostList().get(position));
                            book.setBook_name(loadMoreAdapter.getBookNameList().get(position));
                            book.setBook_short_content_path(loadMoreAdapter.getBookShortContentList().get(position));
                            book.setBook_author(loadMoreAdapter.getBookAuthorList().get(position));
                            book.setBook_main_kind(loadMoreAdapter.getBookMainKindList().get(position));
                            book.setBook_detail_kind(loadMoreAdapter.getBookDetailKindList().get(position));
                            intent.putExtra("book",book);
                            startActivity(intent);
                        }
                    });
                    loadMoreRecyclerView.setManager();
                    loadMoreRecyclerView.setLoadMoreAdapter(loadMoreAdapter);
                }else if(position==4){
                    Toast.makeText(getContext(),"点击了励志",Toast.LENGTH_LONG).show();
                    loadMoreRecyclerView=(LoadMoreRecyclerView)rootView.findViewById(R.id.id_recycleview_book);
                    loadMoreAdapter=new LoadMoreAdapter(getContext());
                    loadMoreAdapter.setBookKind(position+1);
                    loadMoreAdapter.setOnItemClickListener(new LoadMoreAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getContext(),"点击了"+position+"本图书",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getContext(), Activity_BookDetail.class);
                            Book book=new Book();
                            book.setBook_cover_path(loadMoreAdapter.getBookPostList().get(position));
                            book.setBook_name(loadMoreAdapter.getBookNameList().get(position));
                            book.setBook_short_content_path(loadMoreAdapter.getBookShortContentList().get(position));
                            book.setBook_author(loadMoreAdapter.getBookAuthorList().get(position));
                            book.setBook_main_kind(loadMoreAdapter.getBookMainKindList().get(position));
                            book.setBook_detail_kind(loadMoreAdapter.getBookDetailKindList().get(position));
                            intent.putExtra("book",book);
                            startActivity(intent);
                        }
                    });
                    loadMoreRecyclerView.setManager();
                    loadMoreRecyclerView.setLoadMoreAdapter(loadMoreAdapter);
                }else if(position==5){
                    Toast.makeText(getContext(),"点击了经济",Toast.LENGTH_LONG).show();
                    loadMoreRecyclerView=(LoadMoreRecyclerView)rootView.findViewById(R.id.id_recycleview_book);
                    loadMoreAdapter=new LoadMoreAdapter(getContext());
                    loadMoreAdapter.setBookKind(position+1);
                    loadMoreAdapter.setOnItemClickListener(new LoadMoreAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getContext(),"点击了"+position+"本图书",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getContext(), Activity_BookDetail.class);
                            Book book=new Book();
                            book.setBook_cover_path(loadMoreAdapter.getBookPostList().get(position));
                            book.setBook_name(loadMoreAdapter.getBookNameList().get(position));
                            book.setBook_short_content_path(loadMoreAdapter.getBookShortContentList().get(position));
                            book.setBook_author(loadMoreAdapter.getBookAuthorList().get(position));
                            book.setBook_main_kind(loadMoreAdapter.getBookMainKindList().get(position));
                            book.setBook_detail_kind(loadMoreAdapter.getBookDetailKindList().get(position));
                            intent.putExtra("book",book);
                            startActivity(intent);
                        }
                    });
                    loadMoreRecyclerView.setManager();
                    loadMoreRecyclerView.setLoadMoreAdapter(loadMoreAdapter);
                }






            }
        });
        recyclerView=(RecyclerView)rootView.findViewById(R.id.id_recycleview_kind);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(textrecyclerViewAdapter);





        loadMoreRecyclerView=(LoadMoreRecyclerView)rootView.findViewById(R.id.id_recycleview_book);
        loadMoreRecyclerView.setManager();
        loadMoreAdapter=new LoadMoreAdapter(getContext());
        loadMoreAdapter.setBookKind(1);//默认选择第一项
        loadMoreRecyclerView.setLoadMoreAdapter(loadMoreAdapter);







        return rootView;
    }
    void initData(){
        bookKindList=new LinkedList<>();
        for(int i=0;i<6;i++){
            bookKindList.add(kindList[i]);
        }
    }


}
