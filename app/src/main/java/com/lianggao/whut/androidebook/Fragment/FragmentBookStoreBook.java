package com.lianggao.whut.androidebook.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
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
import com.lianggao.whut.androidebook.View.DrawableTextView;
import com.lianggao.whut.androidebook.View.LabelGridView;
import com.lianggao.whut.androidebook.View.LocalImageHolderView;
import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewDrawable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bigkoo.convenientbanner.utils.ScreenUtil.dip2px;

public class FragmentBookStoreBook extends Fragment implements OnItemClickListener {
    private View rootView;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerViewAdapter recyclerViewAdapter2;
    private List<Integer> book_post_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_kind_list;//书的种类集合

    private List<Integer> book_post_list_1;//书的封面集合
    private List<String>book_name_list_1;//书的名字集合
    private List<String>book_author_list_1;//书的作者集合
    private List<String>book_shortcontent_list_1;//书的简介集合
    private List<String>book_kind_list_1;//书的种类集合

    private List<String>book_name_list2;//书的名字集合
    private List<String>book_author_list2;//书的作者集合
    private List<Integer>book_post_list2;//书的图片集合
    private LabelGridView gridView;
    private BookGridViewAdapter bookGridViewAdapter;

    private TextView tv_search;//搜索框
    private static final int DRAWABLE_RIGHT = 2;//图片监听上下左右哪个图片的标志
    private  DrawableTextView drawableTextView_hot;
    private DrawableTextView drawableTextView_recommed;
    private DrawableTextView drawableTextView_maylike;
    //顶部广告栏控件，加载本地图片
    private ConvenientBanner localConvenientBanner;

    private List<Integer> localImages = new ArrayList<>();
    //本地图片
    private Integer[] imagesInteger = new Integer[] { R.drawable.img_test_lunbotu, R.drawable.img_test_lunbotu, R.drawable.img_test_lunbotu};
    //修改为gridview和simpleAdapter
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private List<String>book_name_list3;//书的名字集合
    private List<String>book_author_list3;//书的作者集合
    private List<Integer>book_post_list3;//书的图片集合


    private View linerlayout_hot;
    private View linerlayout_recommed;
    private View linerlayout_maylike;
    private View linerlayout_banner;
    private View text;
    private View searchView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null) {


            rootView = inflater.inflate(R.layout.fragment_bookstore_book, null);

            linerlayout_hot=(LinearLayout)rootView.findViewById(R.id.id_linerlayout_hot);
            linerlayout_recommed=(LinearLayout)rootView.findViewById(R.id.id_linerlayout_recommend);
            linerlayout_maylike=(LinearLayout)rootView.findViewById(R.id.id_linerlayout_maylike);
            linerlayout_banner=(LinearLayout)rootView.findViewById(R.id.id_linerlayout_banner);
            searchView=(TextView)rootView.findViewById(R.id.id_tv_search);
            //text=(TextView)rootView.findViewById(R.id.text);
            ShadowProperty sp = new ShadowProperty()
                    .setShadowColor(R.color.colorGreen)
                    .setShadowDy(dip2px(getContext(), 0f))
                    .setShadowRadius(dip2px(getContext(), 3))

                    .setShadowSide(ShadowProperty.LEFT | ShadowProperty.RIGHT | ShadowProperty.BOTTOM|ShadowProperty.TOP);
            ShadowViewDrawable sd = new ShadowViewDrawable(sp, Color.TRANSPARENT, 0, 0);
            ViewCompat.setBackground(linerlayout_hot, sd);
            ViewCompat.setLayerType(linerlayout_hot, ViewCompat.LAYER_TYPE_SOFTWARE, null);
            /*ViewCompat.setBackground(text, sd);
            ViewCompat.setLayerType(text, ViewCompat.LAYER_TYPE_SOFTWARE, null);*/
            /*ViewCompat.setBackground(linerlayout_recommed, sd);
            ViewCompat.setLayerType(linerlayout_recommed, ViewCompat.LAYER_TYPE_SOFTWARE, null);*/
            ViewCompat.setBackground(linerlayout_banner, sd);
            ViewCompat.setLayerType(linerlayout_banner , ViewCompat.LAYER_TYPE_SOFTWARE, null);
            ViewCompat.setBackground(linerlayout_maylike, sd);
            ViewCompat.setLayerType(linerlayout_maylike , ViewCompat.LAYER_TYPE_SOFTWARE, null);
            ViewCompat.setBackground(searchView, sd);
            ViewCompat.setLayerType(searchView , ViewCompat.LAYER_TYPE_SOFTWARE, null);
            //图书列表
            recyclerView = (RecyclerView) rootView.findViewById(R.id.id_book_recyclerview);
            initdata();
            recyclerViewAdapter = new RecyclerViewAdapter(getContext(), book_post_list, book_name_list, book_author_list, book_kind_list, book_shortcontent_list);
            recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(getContext(),"点击了recycleview第"+position+"个位置",Toast.LENGTH_LONG).show();
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(recyclerViewAdapter);


            recyclerView2 = (RecyclerView) rootView.findViewById(R.id.id_book_recyclerview2);
            initdata3();
            recyclerViewAdapter2 = new RecyclerViewAdapter(getContext(), book_post_list_1, book_name_list_1, book_author_list_1, book_kind_list_1, book_shortcontent_list_1);
            recyclerViewAdapter2.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(getContext(),"点击了recycleview第"+position+"个位置",Toast.LENGTH_LONG).show();
                }
            });

            recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView2.setAdapter(recyclerViewAdapter2);



            /*//gridview展示图书
            gridView = (GridView) rootView.findViewById(R.id.id_book_gridview);
            initdata2();
            bookGridViewAdapter = new BookGridViewAdapter(getContext(), book_name_list2, book_post_list2, book_author_list2);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getContext(),"点击了gridview的第"+position+"个图书",Toast.LENGTH_LONG).show();
                }
            });
            gridView.setAdapter(bookGridViewAdapter);*/


            gridView=(LabelGridView)rootView.findViewById(R.id.id_book_gridview);
            data_list = new ArrayList<Map<String, Object>>();
            //新建适配器,这里的book_progress应该为book_author,但是不用修改
            String [] from ={"book_post","book_name","book_progress"};
            int [] to = {R.id.book_post,R.id.book_name,R.id.book_progress};
            getData();
            sim_adapter = new SimpleAdapter(getContext(), data_list,R.layout.part_activity_book_gridview_new, from, to);
            gridView.setAdapter(sim_adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getContext(),"点击了gridview的第"+position+"个图书",Toast.LENGTH_LONG).show();
                }
            });




            //轮播图
            localConvenientBanner = (ConvenientBanner) rootView.findViewById(R.id.localConvenientBanner);
            //点击搜索textview进入到搜索界面
            tv_search = (TextView) rootView.findViewById(R.id.id_tv_search);
            Typeface iconfont = Typeface.createFromAsset(this.getActivity().getAssets(), "iconfont/iconfont.ttf");
            tv_search.setTypeface(iconfont);
            tv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Activity_SearchView.class);
                    startActivity(intent);

                }
            });
            init();
            //对更多图片进行监听并设置大小
            drawableTextView_hot = (DrawableTextView) rootView.findViewById(R.id.id_tv_book_hot);
            Drawable drawable = getResources().getDrawable(R.drawable.icon_book_more2);
            drawable.setBounds(0, 0, 50, 50);
            drawableTextView_hot.setCompoundDrawables(null, null, drawable, null);
            drawableTextView_hot.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
                @Override
                public void onDrawableRightClickListener(View view) {
                    Toast.makeText(getContext(),"点击了更多热门",Toast.LENGTH_LONG).show();
                }
            });
            //对更多图片进行监听并设置大小
            drawableTextView_recommed = (DrawableTextView) rootView.findViewById(R.id.id_tv_book_recommend);
            Drawable drawable2 = getResources().getDrawable(R.drawable.icon_book_more2);
            drawable2.setBounds(0, 0, 50, 50);
            drawableTextView_recommed.setCompoundDrawables(null, null, drawable2, null);
            drawableTextView_recommed.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
                @Override
                public void onDrawableRightClickListener(View view) {
                    Toast.makeText(getContext(),"点击了更多推荐",Toast.LENGTH_LONG).show();
                }
            });
            //对更多图片进行监听并设置大小
            drawableTextView_maylike = (DrawableTextView) rootView.findViewById(R.id.id_tv_book_maylike);
            Drawable drawable3 = getResources().getDrawable(R.drawable.icon_book_more2);
            drawable3.setBounds(0, 0, 50, 50);
            drawableTextView_maylike.setCompoundDrawables(null, null, drawable3, null);
            drawableTextView_maylike.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
                @Override
                public void onDrawableRightClickListener(View view) {
                    Toast.makeText(getContext(),"点击了猜您喜欢",Toast.LENGTH_LONG).show();
                }
            });
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
    public void initdata3(){
        book_post_list_1=new LinkedList<>();
        book_name_list_1=new LinkedList<>();
        book_author_list_1=new LinkedList<>();
        book_shortcontent_list_1=new LinkedList<>();
        book_kind_list_1=new LinkedList<>();
        //将集合清空，进行重复使用
        /*book_post_list.clear();
        book_name_list.clear();
        book_author_list.clear();
        book_shortcontent_list.clear();
        book_kind_list.clear();*/
        for(int i=0;i<3;i++){
            book_author_list_1.add("梁高");
            book_name_list_1.add("离开的每一种方式");
            book_post_list_1.add(R.drawable.img_bookshelf_everybook);
            book_shortcontent_list_1.add("是法国作家安托万·德·圣·埃克苏佩里于1942年写成的著名儿童文学短篇小说。本书的主人公是来自外星球的小王子。书中以一位飞行员作为故事叙述者，讲述了小王子从自己星球出发前往地球的过程中，所经历的各种历险");
            book_kind_list_1.add("世界名著");
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
    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        book_name_list3=new LinkedList<>();
        book_post_list3=new LinkedList<>();
        book_author_list3=new LinkedList<>();
        for(int i=0;i<6;i++){
            book_name_list3.add("爆裂无声"+i);
            book_post_list3.add(R.drawable.img_bookshelf_everybook);
            book_author_list3.add("雨果");
        }
        for(int i=0;i<6;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("book_post", book_post_list3.get(i));
            map.put("book_name", book_name_list3.get(i));
            map.put("book_progress",book_author_list3.get(i));
            data_list.add(map);
        }

        return data_list;
    }
}
