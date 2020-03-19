package com.lianggao.whut.androidebook.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lianggao.whut.androidebook.Activity_SearchView;
import com.lianggao.whut.androidebook.Adapter.BookGridViewAdapter;
import com.lianggao.whut.androidebook.Adapter.RecyclerViewAdapter;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.View.LocalImageHolderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FragmentBookStoreBook extends Fragment implements OnItemClickListener {
    private View rootView;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Integer> book_post_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_kind_list;//书的种类集合

    private List<String>book_name_list2;//书的名字集合
    private List<String>book_author_list2;//书的作者集合
    private List<Integer>book_post_list2;//书的图片集合
    private GridView gridView;
    private BookGridViewAdapter bookGridViewAdapter;

    private TextView tv_search;//搜索框


    //顶部广告栏控件，加载本地图片
    private ConvenientBanner localConvenientBanner;

    private List<Integer> localImages = new ArrayList<>();
    //本地图片
    private Integer[] imagesInteger = new Integer[] { R.drawable.img_test_lunbotu, R.drawable.img_test_lunbotu, R.drawable.img_test_lunbotu};







    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_bookstore_book,null);
            recyclerView=(RecyclerView) rootView.findViewById(R.id.id_book_recyclerview);
            initdata();
            recyclerViewAdapter=new RecyclerViewAdapter(getContext(),book_post_list,book_name_list,book_author_list,book_kind_list,book_shortcontent_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(recyclerViewAdapter);


            gridView=(GridView)rootView.findViewById(R.id.id_book_gridview);
            initdata2();
            bookGridViewAdapter=new BookGridViewAdapter(getContext(),book_name_list2,book_post_list2,book_author_list2);
            gridView.setAdapter(bookGridViewAdapter);


            localConvenientBanner = (ConvenientBanner)rootView.findViewById(R.id.localConvenientBanner);

            tv_search=(TextView)rootView.findViewById(R.id.id_tv_search);
            Typeface iconfont= Typeface.createFromAsset(this.getActivity().getAssets(), "iconfont/iconfont.ttf");
            tv_search.setTypeface(iconfont);
            tv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), Activity_SearchView.class);
                    startActivity(intent);

                }
            });
            init();


        }

        return rootView;
    }
    public void initdata(){
        book_post_list=new LinkedList<>();
        book_name_list=new LinkedList<>();
        book_author_list=new LinkedList<>();
        book_shortcontent_list=new LinkedList<>();
        book_kind_list=new LinkedList<>();
        for(int i=0;i<5;i++){
            book_author_list.add("梁高");
            book_name_list.add("离开的每一种方式");
            book_post_list.add(R.drawable.img_bookshelf_everybook);
            book_shortcontent_list.add("是法国作家安托万·德·圣·埃克苏佩里于1942年写成的著名儿童文学短篇小说。本书的主人公是来自外星球的小王子。书中以一位飞行员作为故事叙述者，讲述了小王子从自己星球出发前往地球的过程中，所经历的各种历险");
            book_kind_list.add("世界名著");
        }
    }
    public void initdata2(){
        book_post_list2=new LinkedList<>();
        book_name_list2=new LinkedList<>();
        book_author_list2=new LinkedList<>();
        for(int i=0;i<3;i++){
            book_author_list2.add("梁高");
            book_name_list2.add("离开"+i);
            book_post_list2.add(R.drawable.img_bookshelf_everybook);
        }

    }
    private void initViews() {
        localConvenientBanner = (ConvenientBanner) rootView.findViewById(R.id.localConvenientBanner);
        //netConvenientBanner = (ConvenientBanner) findViewById(R.id.netConvenientBanner);
    }

    private void init() {

        //广告栏播放本地图片资源
        localRes();
    }
    /**
     * 广告栏播放本地图片资源
     */
    private void localRes() {
        loadTestDatas();
        localConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new LocalImageHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.part_item_image;
            }
        }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器，不需要圆点指示器可以不设
                .setPageIndicator(new int[] { R.drawable.img_gray_idot, R.drawable.img_white_idot })
                //设置指示器的位置（左、中、右）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                //设置指示器是否可见
                .setPointViewVisible(true)
                //监听单击事件
                .setOnItemClickListener(this)
        //监听翻页事件
//                .setOnPageChangeListener(this)
        ;
    }



    /**
     * 加载本地图片资源
     */
    private void loadTestDatas() {
        localImages.addAll(Arrays.asList(imagesInteger));
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        localConvenientBanner.startTurning();
        //netConvenientBanner.startTurning();
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        localConvenientBanner.stopTurning();
        //netConvenientBanner.stopTurning();
    }
}
