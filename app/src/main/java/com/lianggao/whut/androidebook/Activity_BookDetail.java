package com.lianggao.whut.androidebook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.Utils.bookShelfTableManger;
import com.lianggao.whut.androidebook.View.BookNameTextView;
import com.lianggao.whut.androidebook.View.DrawableTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.github.lizhangqu.coreprogress.ProgressUIListener;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

import static org.litepal.LitePalApplication.getContext;

/**
 * @author LiangGao
 * @description:用来搜索的视图，点击搜索按钮跳转到该布局
 * @data:${DATA} 16:41
 */
public class Activity_BookDetail extends Activity{
    private SearchView searchView;
    //private RatingBar ratingBar;
    private GridView gridView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> data_list;
    private List<Integer>booklist_post_list;//书的封面
    private List<String>booklist_name_list;//书的名字
    private List<Integer>booklist_author_list;//书的作者
    private DrawableTextView textViewCatalog;//目录图标
    private TextView textViewReturn;//返回图标
    Book book=new Book();

    private ImageView imageView;
    private BookNameTextView id_tv_book_name;
    private TextView  id_tv_book_shortcontent;
    private TextView id_tv_book_author;
    private int bookid;///////////////////////
    public bookShelfTableManger bookshelfTableManger;
    private final int MSG_DOWNLOAD_SUCCESS=1;
    private final int MSG_DOWNLOADCHCHE_SUCCESS=2;
    private final int MSG_ALREADY_HAVED=3;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_DOWNLOAD_SUCCESS:
                    Toast.makeText(getContext(),"加入书架成功",Toast.LENGTH_SHORT).show();
                    break;
                case MSG_ALREADY_HAVED:
                    Toast.makeText(getContext(),"已经在书架中存在",Toast.LENGTH_SHORT).show();
                    break;
                case MSG_DOWNLOADCHCHE_SUCCESS:
                    Toast.makeText(getContext(),"打开成功",Toast.LENGTH_SHORT).show();
                    String path=(String)msg.obj;
                    HwTxtPlayActivity.loadTxtFile(Activity_BookDetail.this, path);///storage/emulated/0/d.txt
                    break;
            }
        }
    };





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add_bookshelf:
                    ///////////////////////////////开启一个新线程，下载封面文件和文本文件到本地文件夹
                    bookshelfTableManger=new bookShelfTableManger(getContext());
                    bookshelfTableManger.createDb();


                    new Thread(){
                        @Override
                        public void run() {
                            Looper.prepare();
                            Message msg=new Message();
                            if(bookshelfTableManger.findBookById(bookid)){
                                msg.what=MSG_ALREADY_HAVED;
                                handler.sendMessage(msg);

                            }else {


                                List<NameValuePair> postParam = new ArrayList<>();
                                postParam.add(new NameValuePair("username", "lianggao"));
                                postParam.add(new NameValuePair("password", "12"));
                                postParam.add(new NameValuePair("action", "postAction"));


                                bookshelfTableManger = new bookShelfTableManger(getContext());
                                bookshelfTableManger.createDb();
                                //bookshelfTableManger.deleteBookById(2);
                                Book book = new Book();
                                book.setBook_id(bookid);
                                book.setBook_name(id_tv_book_name.getText().toString());
                                book.setBook_author(id_tv_book_author.getText().toString());
                                book.setBook_cover_path("/storage/emulated/0/android_ebook/Cover/" + (bookid + 1) + ".jpg");
                                book.setBook_path("/storage/emulated/0/android_book/Content/" + (bookid + 1) + ".txt");
                                System.out.println("######################" + book.getBook_id() + book.getBook_name() + book.getBook_author() + book.getBook_cover_path() + book.getBook_path());
                                bookshelfTableManger.addBook(book);


                                String saveFilePath = Environment.getExternalStorageDirectory() + "/android_ebook/Content/" + (bookid + 1) + ".txt";
                                String url = "http://192.168.1.4:8080/com.lianggao.whut/txtbooks/" + (bookid + 1) + ".txt";
                                System.out.println("######################开始下载书籍文件" + saveFilePath + "  " + url);
                                HttpCaller.getInstance().downloadFile(url, saveFilePath, null, new ProgressUIListener() {
                                    @Override
                                    public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                                        Log.i("正在下载", "dowload file content numBytes:" + numBytes + " totalBytes:" + totalBytes + " percent:" + percent + " speed:" + speed);
                                    }
                                });
                                System.out.println("######################下载书籍文件完成");

                                System.out.println("**********************开始书籍封面下载");
                                String saveFilePath2 = Environment.getExternalStorageDirectory() + "/android_ebook/Cover/" + (bookid + 1) + ".jpg";
                                String url2 = "http://192.168.1.4:8080/com.lianggao.whut/images_cover/" + (bookid + 1) + ".jpg";
                                HttpCaller.getInstance().downloadFile(url2, saveFilePath2, null, new ProgressUIListener() {
                                    @Override
                                    public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                                        Log.i("正在下载", "dowload file content numBytes:" + numBytes + " totalBytes:" + totalBytes + " percent:" + percent + " speed:" + speed);
                                    }
                                });
                                System.out.println("**********************下载书籍封面完成");

                                msg.what = MSG_DOWNLOAD_SUCCESS;
                                handler.sendMessage(msg);
                            }

                        }
                    }.start();
                    return true;
                case R.id.navigation_empty:
                    return true;
                case R.id.navigation_begin_read:
                    Toast.makeText(Activity_BookDetail.this,"点击了开始阅读",Toast.LENGTH_SHORT).show();
                    startRead();


                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);


        book=getIntent().getParcelableExtra("book");
        id_tv_book_name=(BookNameTextView)findViewById(R.id.id_tv_book_name);
        id_tv_book_name.setText(book.getBook_name());
        id_tv_book_shortcontent=(TextView)findViewById(R.id.id_tv_book_shortcontent) ;
        id_tv_book_shortcontent.setText(book.getBook_short_content_path());
        id_tv_book_author=(TextView)findViewById(R.id.id_tv_book_author) ;
        id_tv_book_author.setText(book.getBook_author());
        bookid=book.getBook_id();////////////////////////////////
        imageView=(ImageView)findViewById(R.id.id_tv_book_post) ;
        if(book.getBook_cover_path()==null){
            Picasso
                    .with(getApplicationContext())
                    .load(R.drawable.img_booklist_recommend1)
                    .into(imageView);

        }else{
            Picasso
                    .with(getApplicationContext())
                    .load(book.getBook_cover_path())
                    .placeholder(R.drawable.icon_arrow_return)//占位符
                    .error(R.drawable.img_bookshelf_everybook)//链接失效是加载的图片
                    .into(imageView);
        }
      /*  ratingBar=(RatingBar)findViewById(R.id.ratingbar) ;
        ratingBar.setRating(3);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(Activity_BookDetail.this,"rating "+rating+"",Toast.LENGTH_SHORT).show();
            }
        });*/

        gridView=(GridView)findViewById(R.id.id_gv_book_list);
        data_list = new ArrayList<Map<String, Object>>();
        //新建适配器
        String [] from ={"book_post","book_name","book_progress"};
        int [] to = {R.id.book_post,R.id.book_name,R.id.book_progress};
        getData();
        simpleAdapter = new SimpleAdapter(getApplicationContext(), data_list,R.layout.part_activity_book_gridview_new, from, to);
        //配置适配器
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"点击gridview"+position+"个",Toast.LENGTH_SHORT).show();
            }
        });






        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //设置图标大小和监听
       /* textViewCatalog=(DrawableTextView)findViewById(R.id.id_tv_catalog);
        Drawable drawable= getResources().getDrawable(R.drawable.icon_book_catalog);
        drawable.setBounds(0, 0, 70, 70);
        textViewCatalog.setCompoundDrawables(null, drawable, null, null);
        textViewCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"点击了图片 ",Toast.LENGTH_LONG).show();
            }
        });*/
        textViewReturn=(TextView)findViewById(R.id.id_tv_return);
        textViewReturn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return true;
            }
        });

        /*textViewCatalog.setDrawableTopClick(new DrawableTextView.DrawableTopClickListener() {
            @Override
            public void onDrawableTopClickListener(View view) {
                Toast.makeText(getApplicationContext(),"点击了图片 ",Toast.LENGTH_LONG).show();

            }
        });*/







    }
    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        booklist_name_list=new LinkedList<>();
        booklist_post_list=new LinkedList<>();
        booklist_author_list=new LinkedList<>();
        for(int i=0;i<6;i++){
            booklist_name_list.add("爆裂无声"+i);
            booklist_post_list.add(R.drawable.img_bookshelf_everybook);
            booklist_author_list.add(32);
        }
        for(int i=0;i<6;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("book_post", booklist_post_list.get(i));
            map.put("book_name", booklist_name_list.get(i));
            map.put("book_progress",booklist_author_list.get(i));
            data_list.add(map);
        }

        return data_list;
    }

    private void startRead(){
        //开始阅读需要先下载到本地,先判断本地是否已经下载


        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("username", "lianggao"));
                postParam.add(new NameValuePair("password", "12"));
                postParam.add(new NameValuePair("action", "postAction"));
                Message message=new Message();
                String saveFilePath = Environment.getExternalStorageDirectory() + "/android_ebook/CacheCover/" + (bookid + 1) + ".txt";
                String url = "http://192.168.1.4:8080/com.lianggao.whut/txtbooks/" + (bookid + 1) + ".txt";

                if(fileIsExists(saveFilePath)){
                    System.out.println("######################本地已经存在文本文件");
                }else{
                    System.out.println("######################开始缓存书籍文件" + saveFilePath + "  " + url);
                    HttpCaller.getInstance().downloadFile(url, saveFilePath, null, new ProgressUIListener() {
                        @Override
                        public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                            Log.i("正在下载", "dowload file content numBytes:" + numBytes + " totalBytes:" + totalBytes + " percent:" + percent + " speed:" + speed);
                        }
                    });
                    System.out.println("######################缓存书籍文件完成");

                }
                message.what=MSG_DOWNLOADCHCHE_SUCCESS;
                message.obj=saveFilePath;
                handler.sendMessage(message);

            }
        }.start();
    }
    private boolean fileIsExists(String path){
        File file=new  File(path);
        try{
            if(!file.exists()){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }


}
