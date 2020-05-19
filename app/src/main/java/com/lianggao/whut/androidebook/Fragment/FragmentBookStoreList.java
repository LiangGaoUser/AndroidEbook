package com.lianggao.whut.androidebook.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lianggao.whut.androidebook.Activity_BookDetail;
import com.lianggao.whut.androidebook.Activity_More_BookList;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.Utils.Util;
import com.lianggao.whut.androidebook.View.DrawableTextView;
import com.squareup.picasso.Picasso;
//import com.yinglan.shadowimageview.ShadowImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bigkoo.convenientbanner.utils.ScreenUtil.dip2px;

public class FragmentBookStoreList extends Fragment {
    private View rootView;
    //private ShadowImageView shadowImageView;
    //private ShadowImageView shadowImageView2;

    private LinearLayout linearLayout_book_list;
    private GridView gridView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> data_list;
    private List<Integer>booklist_post_list;//书单封面
    private List<String>booklist_name_list;//书单名字
    private List<Integer>booklist_number_list;//书的数目


    private List<String>book_post_list;
    private List<String>book_name_list;
    private List<String>book_author_list;
    private List<Bitmap>bitmapList;
    private final int MSG_GET_BOOK_SUCCESS=1;
    private SimpleAdapter sim_adapter;
    private String [] from ={"book_post","book_name","book_progress"};
    private int [] to = {R.id.book_post,R.id.book_name,R.id.book_progress};
    private static FragmentActivity fragmentActivity = null;
    private ImageView imageView1,imageView2,imageView3,imageView4;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GET_BOOK_SUCCESS:
                    Log.i("获取图片","批量获取图片成功");

                    sim_adapter = new SimpleAdapter(fragmentActivity, data_list,R.layout.part_activity_book_gridview_new, from, to);
                    sim_adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        @Override
                        public boolean setViewValue(View view, Object data, String textRepresentation) {
                            if(view instanceof ImageView &&data instanceof Bitmap){
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

            }
        }
    };

    private View view1,view2,view3,view4;
    private CardView cardView;
    private DrawableTextView moreBookList;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_bookstore_list,null);
        gridView=(GridView)rootView.findViewById(R.id.id_gv_book_list);
        data_list = new ArrayList<Map<String, Object>>();

        imageView1=(ImageView)rootView.findViewById(R.id.id_tv_book_list5);
        imageView2=(ImageView)rootView.findViewById(R.id.id_tv_book_list6);
        imageView3=(ImageView)rootView.findViewById(R.id.id_tv_book_list7);
        imageView4=(ImageView)rootView.findViewById(R.id.id_tv_book_list8);
        Picasso
                .with(getContext())
                .load("http://192.168.1.4:8080/com.lianggao.whut/images_booklist/2019.jpg")
                .placeholder(R.drawable.icon_arrow_return)//占位符
                .error(R.drawable.img_bookshelf_everybook)//链接失效是加载的图片
                .into(imageView1);

        Picasso
                .with(getContext())
                .load("http://192.168.1.4:8080/com.lianggao.whut/images_booklist/2018.jpg")
                .placeholder(R.drawable.icon_arrow_return)//占位符
                .error(R.drawable.img_bookshelf_everybook)//链接失效是加载的图片
                .into(imageView2);
        Picasso
                .with(getContext())
                .load("http://192.168.1.4:8080/com.lianggao.whut/images_booklist/2017.jpg")
                .placeholder(R.drawable.icon_arrow_return)//占位符
                .error(R.drawable.img_bookshelf_everybook)//链接失效是加载的图片
                .into(imageView3);
        Picasso
                .with(getContext())
                .load("http://192.168.1.4:8080/com.lianggao.whut/images_booklist/2016.jpg")
                .placeholder(R.drawable.icon_arrow_return)//占位符
                .error(R.drawable.img_bookshelf_everybook)//链接失效是加载的图片
                .into(imageView4);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Activity_More_BookList.class);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Activity_More_BookList.class);
                startActivity(intent);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Activity_More_BookList.class);
                startActivity(intent);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Activity_More_BookList.class);
                startActivity(intent);
            }
        });

        fragmentActivity=getActivity();
        getAllBook();

        moreBookList=(DrawableTextView)rootView.findViewById(R.id.id_tv_all_booklist);
        Drawable drawable = getResources().getDrawable(R.drawable.icon_book_more2);
        drawable.setBounds(0, 0, 50, 50);
        moreBookList.setCompoundDrawables(null, null, drawable, null);
        moreBookList.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                Intent intent=new Intent(getActivity(), Activity_More_BookList.class);
                startActivity(intent);

            }
        });
        return rootView;
    }


    //初始化推荐书籍数据
    public void getAllBook(){
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                book_name_list=new LinkedList<>();
                book_post_list=new LinkedList<>();
                book_author_list=new LinkedList<>();
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
                    book_post_list.add(book.getBook_cover_path());
                    book_author_list.add(book.getBook_author());
                    book_name_list.add(book.getBook_name());
                }
                bitmapList= Util.getMultiBitMap(book_post_list);
                for(int i=0;i<bookRecommendList.size();i++){
                    map=new HashMap<String,Object>();
                    map.put("book_post", bitmapList.get(i));
                    map.put("book_name", book_name_list.get(i));
                    map.put("book_progress",book_author_list.get(i));
                    data_list.add(map);

                }
                Message msg=new Message();
                msg.obj=data_list;
                msg.what=MSG_GET_BOOK_SUCCESS;
                handler.sendMessage(msg);
            }
        }.start();
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i("Fragment","FragmentBookStoreList onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("Fragment","FragmentBookStoreList onResume");;
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragment","FragmentBookStoreList onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Fragment","FragmentBookStoreList onStop");
    }
}
