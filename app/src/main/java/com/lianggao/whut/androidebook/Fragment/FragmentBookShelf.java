package com.lianggao.whut.androidebook.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.lianggao.whut.androidebook.Activity_BookDetail;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.Utils.Util;
import com.lianggao.whut.androidebook.Utils.bookShelfTableManger;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.litepal.LitePalApplication.getContext;

//类似于BookShelfGridView.java的作用，用来获取fragment_bookshelf.xml中的内容
//BookShelfGridViewAdapter.java和BookShelfGridView.java不再使用，因为在BooksShelfView.java中绘制非常耗费时间，换成使用
//的gridview和simadapter来替代之前的写法，这样不会出现卡顿的现象，而图片阴影框架没有使用到，之后也许还需要上面的来实现书单阴影
public class FragmentBookShelf extends ViewPageFragment {
    /*private BookShelfGridView bookShelfGridView;//书架布局类
    private BookShelfGridViewAdapter bookShelfGridViewAdapter;
    private List<String> book_name_list;//书名集合
    private List<Integer>book_post_list;//书的封面集合
    private List<Integer>book_percent_list;//书的已读百分比集合
    private List<Boolean>book_download_list;//书是否已经下载的集合*/

    //private List<Integer>book_post_list;
    private List<String>book_name_list;
    private List<String>book_author_list;
    private List<String>book_post_list;

    private GridView gridView;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;

    private Toolbar toolbar;

    private bookShelfTableManger bookshelfTableManger;
    private List<Book> bookList;
    //GridView
    String [] from ={"book_post","book_name","book_progress"};
    int [] to = {R.id.book_post,R.id.book_name,R.id.book_progress};

    private FragmentActivity fragmentActivity;
    private final int MSG_GET_LOCAL_BOOKS_SUCCESS=1;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_GET_LOCAL_BOOKS_SUCCESS:




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
                    System.out.println("本地书架图书加载完成");
                    break;









            }
        }
    };



    //    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null) {
            rootView = inflater.inflate(R.layout.fragment_bookshelf, null);
            gridView=(GridView)rootView.findViewById(R.id.id_gv_bookshelf);

            fragmentActivity=getActivity();

            //开启子线程获取本地图书数据
            getLocalBookShelf();




            toolbar=(Toolbar)rootView.findViewById(R.id.toolbar);
            toolbar.inflateMenu(R.menu.menu_bookshelf_toolbar);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.toolbar_search:
                            Log.i("haha", "toolbar_search");
                            return true;
                        case R.id.toolbar_action1:
                            Log.i("haha", "toolbar_action1");
                            return true;
                        case R.id.toolbar_action2:
                            Log.i("haha", "toolbar_action2");
                            return true;
                        case R.id.toolbar_action3:
                            Log.i("haha", "toolbar_action3");
                            return true;
                        case R.id.toolbar_action4:
                            Log.i("haha", "toolbar_action4");
                            return true;
                    }
                    return true;
                }
            });

        }

        //判断bookshelf表是否为空，为空则建立bookshelf表
        bookshelfTableManger=new bookShelfTableManger(getContext());

        bookshelfTableManger.createDb();
        //bookshelfTableManger.deleteTable();
        //判断系统文件夹是否存在不存在就创建文件夹
        File file= new File(Environment.getExternalStorageDirectory()+"/android_ebook/CacheCover");
        if(!file.exists()){
            file.mkdir();
        }
        File file2= new File(Environment.getExternalStorageDirectory()+"/android_ebook/CacheContent");
        if(!file2.exists()){
            file2.mkdir();
        }
        File file3= new File(Environment.getExternalStorageDirectory()+"/android_ebook/Cover");
        if(!file3.exists()){
            file3.mkdir();
        }
        File file4= new File(Environment.getExternalStorageDirectory()+"/android_ebook/Content");
        if(!file4.exists()){
            file4.mkdir();
        }

        return rootView;
    }
    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            //Log.i("MainActivityBook","MainActivity出现时这里可以加载数据");
        }else{
            //Log.i("MainActivityBook","退出时执行操作");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }*/
    @Override
    public void onStart() {
        super.onStart();
        //mVp.setCurrentItem(number);
        Log.i("FragmentBookShelf","onStart");
        // Toast.makeText(getContext(),"点击了按钮"+number,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("FragmentBookShelf","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("FragmentBookShelf","onRause");
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(isVisible){
            Log.i("FragmentBookShelf","重新回到书架页面，开始重新加载");
            getLocalBookShelf();
            //在这里进行刷新

        }else{
            Log.i("FragmentBookShelf","bbb");
        }
    }
    private void showPopupMenu(View view){
        PopupMenu popupMenu=new PopupMenu(getContext(),view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_each_book_click,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //Toast.makeText(getApplicationContext(), "关闭popupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }



    private void getLocalBookShelf(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        new Thread(){
            @Override
            public void run() {

                bookshelfTableManger=new bookShelfTableManger(getContext());
                bookshelfTableManger.createDb();
                bookList=bookshelfTableManger.findAllBook();
                System.out.println("开始获取本地书架图书");

                book_name_list=new LinkedList<>();
                book_post_list=new LinkedList<>();
                book_author_list=new LinkedList<>();
                data_list = new ArrayList<Map<String, Object>>();
                List<Bitmap>bitmapList;
                bitmapList=new LinkedList<>();
                for(int i=0;i<bookList.size();i++){
                    book_name_list.add(bookList.get(i).getBook_name());
                    book_post_list.add(bookList.get(i).getBook_cover_path());
                    book_author_list.add(bookList.get(i).getBook_author());
                    System.out.println("######"+bookList.get(i).getBook_name()+" "+bookList.get(i).getBook_cover_path()+" "+bookList.get(i).getBook_author()+"****");
                }

                bitmapList= Util.getMultiLocalBitMap(book_post_list);

                for(int i=0;i<bookList.size();i++){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("book_post", bitmapList.get(i));
                    map.put("book_name", book_name_list.get(i));
                    map.put("book_progress",book_author_list.get(i));
                    data_list.add(map);
                }
                Message message=new Message();
                message.what=MSG_GET_LOCAL_BOOKS_SUCCESS;
                handler.sendMessage(message);

            }
        }.start();

    }
    public static Bitmap getLocalBitmap(String url){
        try{
            FileInputStream fileInputStream=new FileInputStream(url);
            return BitmapFactory.decodeStream(fileInputStream);
        }catch(Exception e){
            return null;
        }
    }

}
