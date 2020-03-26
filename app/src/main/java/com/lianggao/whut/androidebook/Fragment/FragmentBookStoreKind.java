package com.lianggao.whut.androidebook.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Adapter.KindRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Adapter.RecyclerViewAdapter;
import com.lianggao.whut.androidebook.Adapter.TextRecyclerViewAdapter;
import com.lianggao.whut.androidebook.R;
import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewDrawable;

import java.util.LinkedList;
import java.util.List;

import static com.bigkoo.convenientbanner.utils.ScreenUtil.dip2px;
import static org.litepal.LitePalApplication.getContext;

public class FragmentBookStoreKind extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private TextRecyclerViewAdapter textrecyclerViewAdapter;
    private RecyclerViewAdapter recyclerViewAdapter;
    private KindRecyclerViewAdapter kindRecyclerViewAdapter;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_bookstore_kind,null);

        recyclekindView=(RecyclerView)rootView.findViewById(R.id.id_recycleview_kind);
        recyclebookView=(RecyclerView)rootView.findViewById(R.id.id_recycleview_book);
        linerlayout_kind=(LinearLayout)rootView.findViewById(R.id.id_linerlayout_kind);
        linerlayout_book=(LinearLayout)rootView.findViewById(R.id.id_linerlayout_book);
        ShadowProperty sp = new ShadowProperty()
                .setShadowColor(R.color.colorGreen)
                .setShadowDy(dip2px(getContext(), 0f))
                .setShadowRadius(dip2px(getContext(), 3))

                .setShadowSide(ShadowProperty.LEFT | ShadowProperty.RIGHT | ShadowProperty.BOTTOM|ShadowProperty.TOP);
        ShadowViewDrawable sd = new ShadowViewDrawable(sp, Color.TRANSPARENT, 0, 0);


        /*ViewCompat.setBackground(linerlayout_kind, sd);
        ViewCompat.setLayerType(linerlayout_kind, ViewCompat.LAYER_TYPE_SOFTWARE, null);*/
        ViewCompat.setBackground(linerlayout_book, sd);
        ViewCompat.setLayerType(linerlayout_book, ViewCompat.LAYER_TYPE_SOFTWARE, null);

        initData();
        textrecyclerViewAdapter=new TextRecyclerViewAdapter(getContext(),bookKindList);
        textrecyclerViewAdapter.setOnItemClickListener(new TextRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Log.i("!!!+",position+"");
                textrecyclerViewAdapter.setKindNumber(position);
                textrecyclerViewAdapter.notifyDataSetChanged();
                if(position==1){
                    initdata3();
                    recyclerViewAdapter = new RecyclerViewAdapter(getContext(), book_post_list, book_name_list, book_author_list, book_kind_list, book_shortcontent_list);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView1.setAdapter(recyclerViewAdapter);
                }
                Toast.makeText(getContext(),"第"+position+"个",Toast.LENGTH_LONG).show();
            }
        });
        recyclerView=(RecyclerView)rootView.findViewById(R.id.id_recycleview_kind);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(textrecyclerViewAdapter);

        initdata2();
        recyclerView1=(RecyclerView)rootView.findViewById(R.id.id_recycleview_book);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), book_post_list, book_name_list, book_author_list, book_kind_list, book_shortcontent_list);
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(getContext(),"点击了recycleview第"+position+"个位置",Toast.LENGTH_LONG).show();
            }
        });
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(recyclerViewAdapter);
        return rootView;
    }
    void initData(){
        bookKindList=new LinkedList<>();
        for(int i=0;i<12;i++){
            bookKindList.add("玄幻"+i);
        }
    }

    void initdata2(){
        book_post_list=new LinkedList<>();
        book_name_list=new LinkedList<>();
        book_author_list=new LinkedList<>();
        book_shortcontent_list=new LinkedList<>();
        book_kind_list=new LinkedList<>();
        for(int i=0;i<15;i++){
            book_author_list.add("梁高");
            book_name_list.add("离开的每一种方式");
            book_post_list.add(R.drawable.img_bookshelf_everybook);
            book_shortcontent_list.add("是法国作家安托万·德·圣·埃克苏佩里于1942年写成的著名儿童文学短篇小说。本书的主人公是来自外星球的小王子。书中以一位飞行员作为故事叙述者，讲述了小王子从自己星球出发前往地球的过程中，所经历的各种历险");
            book_kind_list.add("世界名著");
        }
    }
    void initdata3(){
        book_post_list=new LinkedList<>();
        book_name_list=new LinkedList<>();
        book_author_list=new LinkedList<>();
        book_shortcontent_list=new LinkedList<>();
        book_kind_list=new LinkedList<>();
        for(int i=0;i<15;i++){
            book_author_list.add("佚名");
            book_name_list.add("离开的每一种方式");
            book_post_list.add(R.drawable.img_bookshelf_everybook);
            book_shortcontent_list.add("是法国作家安托万·德·圣·埃克苏佩里于1942年写成的著名儿童文学短篇小说。本书的主人公是来自外星球的小王子。书中以一位飞行员作为故事叙述者，讲述了小王子从自己星球出发前往地球的过程中，所经历的各种历险");
            book_kind_list.add("世界名著");
        }
    }
}
