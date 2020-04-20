package com.lianggao.whut.androidebook.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.google.gson.Gson;
import com.lianggao.whut.androidebook.Activity_More_Recommend_Books;
import com.lianggao.whut.androidebook.Activity_SearchView;
import com.lianggao.whut.androidebook.Adapter.BookGridViewAdapter;
import com.lianggao.whut.androidebook.Adapter.NetRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Adapter.RecyclerViewAdapter;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.Utils.Util;
import com.lianggao.whut.androidebook.View.DrawableTextView;
import com.lianggao.whut.androidebook.View.LabelGridView;
import com.lianggao.whut.androidebook.View.LocalImageHolderView;
import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewDrawable;

import org.w3c.dom.Text;

import java.net.URLDecoder;
import java.net.URLEncoder;
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

    //推荐书籍
    private List<String>book_name_recommend_list;//名字列表
    private List<String>book_author_recommend_list;//作者列表
    private List<String>book_post_recommend_list;//封面列表
    private List<Bitmap>bitmapList;

    //热门书籍
    private List<String>book_name_hot_list;//名字
    private List<String>book_author_hot_list;//作者
    private List<String>book_post_hot_list;//封面
    private List<String>book_shortcontent_hot_list;//简介
    private List<String>book_kind_hot_list;//种类

    //排行榜
    private List<String>book_name_rank_list;//名字列表
    private List<String>book_author_rank_list;//作者列表
    private List<String>book_post_rank_list;//封面列表
    private List<String>book_shortcontent_rank_list;//简介
    private List<String>book_kind_rank_list;//种类



    //封面名称
    private List<String>book_post_name_list;
    private final int MSG_GET_RECOMMEND_SUCCESS=1;
    private final int MSG_GET_HOT_SUCCESS=2;
    private final int MSG_GET_RANK_SUCCESS=3;
    private String [] from ={"book_post","book_name","book_progress"};
    private int [] to = {R.id.book_post,R.id.book_name,R.id.book_progress};
    private static  FragmentActivity fragmentActivity = null;
    private NetRecyclerViewAdapter netRecyclerViewAdapter;
    private NetRecyclerViewAdapter hotRecyclerViewAdapter;
    private NetRecyclerViewAdapter rankRecyclerViewAdapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_RECOMMEND_SUCCESS:
                    //netRecyclerViewAdapter=new NetRecyclerViewAdapter(getContext(),book_post_recommend_list,book_name_recommend_list,book_author_recommend_list,book_kind_recommend_list,book_shortcontent_recommend_list);



                    Log.i("获取图片","批量获取图片成功");

                    sim_adapter = new SimpleAdapter(fragmentActivity, data_list,R.layout.part_activity_book_gridview_new, from, to);
                    sim_adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        @Override
                        public boolean setViewValue(View view, Object data, String textRepresentation) {
                            if(view instanceof  ImageView &&data instanceof Bitmap){
                                ImageView iv=(ImageView)view;
                                iv.setImageBitmap((Bitmap)data);
                                return true;
                            }else{
                                return false;
                            }
                        }
                    });
                    gridView.setAdapter(sim_adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getContext(),"点击了gridview的第"+position+"个图书",Toast.LENGTH_LONG).show();
                        }
                    });
                    break;

                case MSG_GET_HOT_SUCCESS:
                    hotRecyclerViewAdapter = new NetRecyclerViewAdapter(getContext(), book_post_hot_list, book_name_hot_list, book_author_hot_list, book_kind_hot_list, book_shortcontent_hot_list);
                    hotRecyclerViewAdapter.setOnItemClickListener(new NetRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getContext(),"点击了recycleview第"+position+"个位置",Toast.LENGTH_LONG).show();
                        }
                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(hotRecyclerViewAdapter);
                    break;
                case MSG_GET_RANK_SUCCESS:
                    rankRecyclerViewAdapter = new NetRecyclerViewAdapter(getContext(), book_post_rank_list, book_name_rank_list, book_author_rank_list, book_kind_rank_list, book_shortcontent_rank_list);
                    rankRecyclerViewAdapter.setOnItemClickListener(new NetRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getContext(),"点击了recycleview第"+position+"个位置",Toast.LENGTH_LONG).show();
                        }
                    });
                    recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView2.setAdapter(rankRecyclerViewAdapter);
                    break;
            }
        }
    };

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


            //热门图书
            recyclerView = (RecyclerView) rootView.findViewById(R.id.id_book_recyclerview);
            getHotBook();






            //排行榜
            recyclerView2 = (RecyclerView) rootView.findViewById(R.id.id_book_recyclerview2);
            getRankBook();




            gridView=(LabelGridView)rootView.findViewById(R.id.id_book_gridview);
            data_list = new ArrayList<Map<String, Object>>();
            getRecommendBook();
            fragmentActivity=getActivity();





            //轮播图
            localConvenientBanner = (ConvenientBanner) rootView.findViewById(R.id.localConvenientBanner);
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
                    Intent intent=new Intent(getActivity(), Activity_More_Recommend_Books.class);
                    startActivity(intent);


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
    //广告栏播放本地图片资源
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
                //.setOnPageChangeListener(this)
        ;
    }




    // 加载本地图片资源
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

    //初始化热门书籍数据
    public void getHotBook(){
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("username","lianggao"));
                postParam.add(new NameValuePair("password","12"));
                postParam.add(new NameValuePair("action","postAction"));
                book_post_hot_list=new LinkedList<>();
                book_name_hot_list=new LinkedList<>();
                book_kind_hot_list=new LinkedList<>();
                book_shortcontent_hot_list=new LinkedList<>();
                book_author_hot_list=new LinkedList<>();
                List<Book>bookHotList;
                bookHotList=HttpCaller.getInstance().postSyncList(Book.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Hot_Servlet",postParam);
                Gson gson=new Gson();
                for(int i=0;i<bookHotList.size();i++){
                    String jsonStr=gson.toJson(bookHotList.get(i));
                    Book book=gson.fromJson(jsonStr,Book.class);
                    book_post_hot_list.add(book.getBook_cover_path());
                    book_name_hot_list.add(book.getBook_name());
                    book_shortcontent_hot_list.add(book.getBook_short_content_path());
                    book_author_hot_list.add(book.getBook_author());
                    book_kind_hot_list.add("文学名著");

                }
                Message message=new Message();
                message.what=MSG_GET_HOT_SUCCESS;
                handler.sendMessage(message);
                Looper.loop();


            }
        }.start();


    }

    //初始化推荐书籍数据
    public void getRecommendBook(){
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                book_name_recommend_list=new LinkedList<>();
                book_post_recommend_list=new LinkedList<>();
                book_author_recommend_list=new LinkedList<>();
                bitmapList=new LinkedList<>();

                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("username","lianggao"));
                postParam.add(new NameValuePair("password","12"));
                postParam.add(new NameValuePair("action","postAction"));
                List<Book>bookRecommendList;
                bookRecommendList= HttpCaller.getInstance().postSyncList(Book.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Recommend_Servlet",postParam);
                Gson gson=new Gson();
                Map<String ,Object>map;
                for(int i=0;i<bookRecommendList.size();i++){

                    String jsonStr=gson.toJson(bookRecommendList.get(i));
                    Book book=new Book();
                    book=gson.fromJson(jsonStr,Book.class);
                    book_post_recommend_list.add(book.getBook_cover_path());
                    book_author_recommend_list.add(book.getBook_author());
                    book_name_recommend_list.add(book.getBook_name());
                }
                bitmapList=Util.getMultiBitMap(book_post_recommend_list);
                for(int i=0;i<bookRecommendList.size();i++){
                    map=new HashMap<String,Object>();
                    map.put("book_post", bitmapList.get(i));
                    map.put("book_name", book_name_recommend_list.get(i));
                    map.put("book_progress",book_author_recommend_list.get(i));
                    data_list.add(map);

                }
                Message msg=new Message();
                msg.obj=data_list;
                msg.what=MSG_GET_RECOMMEND_SUCCESS;
                handler.sendMessage(msg);
            }
        }.start();
    }


    //初始化排行榜书籍数据
    public void getRankBook(){
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("username","lianggao"));
                postParam.add(new NameValuePair("password","12"));
                postParam.add(new NameValuePair("action","postAction"));
                book_post_rank_list=new LinkedList<>();
                book_name_rank_list=new LinkedList<>();
                book_kind_rank_list=new LinkedList<>();
                book_shortcontent_rank_list=new LinkedList<>();
                book_author_rank_list=new LinkedList<>();
                List<Book>bookRankList;
                bookRankList=HttpCaller.getInstance().postSyncList(Book.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_Book_Rank_Servlet",postParam);
                Gson gson=new Gson();
                for(int i=0;i<bookRankList.size();i++){
                    String jsonStr=gson.toJson(bookRankList.get(i));
                    //String jsonStr2= URLDecoder.decode(jsonStr);
                    Book book=gson.fromJson(jsonStr,Book.class);
                    book_post_rank_list.add(book.getBook_cover_path());
                    book_name_rank_list.add(book.getBook_name());
                    book_shortcontent_rank_list.add(book.getBook_short_content_path());
                    book_author_rank_list.add(book.getBook_author());
                    book_kind_rank_list.add("文学名著");

                }
                Message message=new Message();
                message.what=MSG_GET_RANK_SUCCESS;
                handler.sendMessage(message);
                Looper.loop();


            }
        }.start();


    }







}
