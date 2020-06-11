package com.lianggao.whut.androidebook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
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
import com.lianggao.whut.androidebook.Model.BookStar;
import com.lianggao.whut.androidebook.Model.Result;
import com.lianggao.whut.androidebook.Model.UserReadHistory;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.Utils.DialogThridUtils;
import com.lianggao.whut.androidebook.Utils.Util;
import com.lianggao.whut.androidebook.Utils.WeiboDialogUtils;
import com.lianggao.whut.androidebook.Utils.bookShelfHistoryTableManger;
import com.lianggao.whut.androidebook.Utils.bookShelfTableManger;
import com.lianggao.whut.androidebook.View.BookNameTextView;
import com.lianggao.whut.androidebook.View.DrawableTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private TextView  id_tv_book_main_kind;
    private TextView  id_tv_book_detail_kind;
    private TextView id_tv_book_author;

    private TextView id_tv_book_star;
    private TextView id_tv_book_content;

    private String book_name;

    public bookShelfTableManger bookshelfTableManger;
    private Dialog dialogStartRead;
    private Dialog dialogAddBookShelf;
    private final int MSG_DOWNLOAD_SUCCESS=1;
    private final int MSG_DOWNLOADCHCHE_SUCCESS=2;
    private final int MSG_ALREADY_HAVED=3;
    private final int MSG_NOT_STAR=4;
    private final int MSG_IS_STAR=5;
    private final int MSG_ADD_STAR=6;
    private final int MSG_NOT_LOGINED=7;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_DOWNLOAD_SUCCESS:
                    DialogThridUtils.closeDialog(dialogAddBookShelf);
                    Toast.makeText(getContext(),"加入书架成功",Toast.LENGTH_SHORT).show();
                    break;
                case MSG_ALREADY_HAVED:
                    DialogThridUtils.closeDialog(dialogAddBookShelf);
                    Toast.makeText(getContext(),"已经在书架中存在",Toast.LENGTH_SHORT).show();
                    break;
                case MSG_DOWNLOADCHCHE_SUCCESS:
                    Toast.makeText(getContext(),"打开成功",Toast.LENGTH_SHORT).show();
                    DialogThridUtils.closeDialog(dialogStartRead);
                    String path=(String)msg.obj;
                    HwTxtPlayActivity.loadTxtFile(Activity_BookDetail.this, path);///storage/emulated/0/d.txt
                    break;
                case MSG_NOT_STAR:
                    id_tv_book_star.setText("加入收藏");
                    break;
                case MSG_IS_STAR:
                    id_tv_book_star.setText("已经加入收藏");
                    break;
                case MSG_ADD_STAR:
                    Toast.makeText(getContext(),"加入收藏成功",Toast.LENGTH_SHORT).show();
                    id_tv_book_star.setText("已经加入收藏");
                    break;
                case MSG_NOT_LOGINED:
                    new AlertDialog.Builder(Activity_BookDetail.this)
                            .setTitle("操作结果")
                            .setMessage("还没有登录，请返回登录")
                            .setPositiveButton("确定返回", onClickListener)
                            .show();
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
                    //开启一个新线程，下载封面文件和文本文件到本地文件夹
                    bookshelfTableManger=new bookShelfTableManger(getContext());
                    bookshelfTableManger.createDb();
                    dialogAddBookShelf=WeiboDialogUtils.createLoadingDialog(Activity_BookDetail.this, "正在加入书架...");


                    new Thread(){
                        @Override
                        public void run() {
                            Looper.prepare();
                            final Message msg=new Message();
                            if(bookshelfTableManger.findBookByName(book_name)){
                                msg.what=MSG_ALREADY_HAVED;
                                handler.sendMessage(msg);

                            }else {


                                List<NameValuePair> postParam = new ArrayList<>();
                                postParam.add(new NameValuePair("username", "lianggao"));
                                postParam.add(new NameValuePair("password", "12"));
                                postParam.add(new NameValuePair("action", "postAction"));


                                bookshelfTableManger = new bookShelfTableManger(getContext());
                                bookshelfTableManger.createDb();
                                //bookshelfTableManger.deleteTable();
                                Book bookAdd = new Book();

                                bookAdd.setBook_name(id_tv_book_name.getText().toString());
                                bookAdd.setBook_author(id_tv_book_author.getText().toString());

                               // bookAdd.setBook_cover_path( getExternalFilesDir("Cover")+"/"+ book_name + ".jpg");//"/storage/emulated/0/android_ebook/Cover/"
                               // bookAdd.setBook_path(getExternalFilesDir("Content") +"/"+ book_name + ".txt");

                                bookAdd.setBook_cover_path( getExternalFilesDir("Cover")+"/"+ book_name + ".jpg");//"/storage/emulated/0/android_ebook/Cover/"
                                bookAdd.setBook_path(getExternalFilesDir("Content") +"/"+  book_name+ ".txt");

                                bookAdd.setBook_main_kind(book.getBook_main_kind());
                                bookAdd.setBook_detail_kind(book.getBook_detail_kind());
                                System.out.println(  bookAdd.getBook_name() + bookAdd.getBook_author() + bookAdd.getBook_cover_path() + bookAdd.getBook_path()+bookAdd.getBook_main_kind()+bookAdd.getBook_detail_kind());
                                bookshelfTableManger.addBook(bookAdd);




                                //书架历史，只需要存储封面，不需要存储文件，存储到书架历史中


                                bookShelfHistoryTableManger bookShelfHistoryTableManger=new bookShelfHistoryTableManger(getApplicationContext());
                                bookShelfHistoryTableManger.createDb();
                                //bookShelfHistoryTableManger.deleteTable();

                                if(!bookShelfHistoryTableManger.findBookByName(book_name)){
                                    System.out.println("开始缓存书籍封面到书架历史");
                                    String saveFilePath3 = getExternalFilesDir("CoverBookShelfHistory") + "/" + book_name + ".jpg";
                                    String url3 = "http://192.168.1.4:8080/com.lianggao.whut/images_cover/" + book_name  + ".jpg";
                                    HttpCaller.getInstance().downloadFile(url3, saveFilePath3, null, new ProgressUIListener() {
                                        @Override
                                        public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                                            Log.i("正在下载", "dowload file content numBytes:" + numBytes + " totalBytes:" + totalBytes + " percent:" + percent + " speed:" + speed);
                                        }
                                    });
                                    Book addBook=(Book)book;
                                    addBook.setBook_cover_path(saveFilePath3);
                                    bookShelfHistoryTableManger.addBook(addBook);
                                    System.out.println("完成缓存书籍文件封面到书架历史" );

                                }else{
                                    System.out.println("书架历史中已经存在该书籍" );
                                }






                                String saveFilePath = null;

                                saveFilePath = getExternalFilesDir("Content")  + "/"+ book_name + ".txt";
                                String url = "http://192.168.1.4:8080/com.lianggao.whut/txtbooks/" + book_name+ ".txt";//这里是以name请求的
                                System.out.println("开始下载书籍文件" + saveFilePath + "  " + url);
                                HttpCaller.getInstance().downloadFile(url, saveFilePath, null, new ProgressUIListener() {
                                    @Override
                                    public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                                        Log.i("正在下载", "dowload file content numBytes:" + numBytes + " totalBytes:" + totalBytes + " percent:" + percent + " speed:" + speed);
                                    }
                                });
                                System.out.println("下载书籍文件完成");

                                System.out.println("开始书籍封面下载");
                                String saveFilePath2 = getExternalFilesDir("Cover") + "/" + book_name+ ".jpg";
                                String url2 = "http://192.168.1.4:8080/com.lianggao.whut/images_cover/" + book_name + ".jpg";
                                HttpCaller.getInstance().downloadFile(url2, saveFilePath2, null, new ProgressUIListener() {
                                    @Override
                                    public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                                        Log.i("正在下载", "dowload file content numBytes:" + numBytes + " totalBytes:" + totalBytes + " percent:" + percent + " speed:" + speed);
                                        if(percent==1.0){

                                            msg.what = MSG_DOWNLOAD_SUCCESS;
                                            handler.sendMessage(msg);
                                        }
                                    }
                                });
                                System.out.println("下载书籍封面完成");



                            }

                        }
                    }.start();
                    return true;
                case R.id.navigation_return:
                    finish();
                    return true;
                case R.id.navigation_begin_read:
                    //Toast.makeText(Activity_BookDetail.this,"正在下载，下载完成会自动打开，请稍后...",Toast.LENGTH_LONG).show();
                    dialogStartRead = WeiboDialogUtils.createLoadingDialog(Activity_BookDetail.this, "加载中...");
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

        id_tv_book_author=(TextView)findViewById(R.id.id_tv_book_author) ;
        id_tv_book_author.setText(book.getBook_author());

        id_tv_book_main_kind=(TextView)findViewById(R.id.id_tv_book_main_kind) ;
        id_tv_book_main_kind.setText(book.getBook_main_kind());
        id_tv_book_detail_kind=(TextView)findViewById(R.id.id_tv_book_detail_kind) ;
        id_tv_book_detail_kind.setText(book.getBook_detail_kind());

        id_tv_book_star=(TextView)findViewById(R.id.id_tv_book_star);
        id_tv_book_content=(TextView)findViewById(R.id.id_tv_book_content);
        id_tv_book_content.setText(book.getBook_short_content_path());
        ifStart();
        id_tv_book_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_tv_book_star.getText().equals("加入收藏")){
                    addStart();
                }
            }
        });




        book_name=book.getBook_name();
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


        textViewReturn=(TextView)findViewById(R.id.id_tv_return);
        textViewReturn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return true;
            }
        });


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
                final Message message=new Message();
                //阅读打开的文件应该放在CacheContent里面，而且封面文件不需要存储
                final String saveFilePath;
                try {
                    saveFilePath = getExternalFilesDir("CacheContent") + "/" + URLEncoder.encode(book_name,"utf-8") + ".txt";
                    String url = "http://192.168.1.4:8080/com.lianggao.whut/txtbooks/" + URLEncoder.encode(book_name,"utf-8") + ".txt";

                    SharedPreferences sp=getApplicationContext().getSharedPreferences("QQFile", Context.MODE_PRIVATE);
                    String user_id=sp.getString("flag","");
                    if(user_id==""){
                        message.what=MSG_NOT_LOGINED;
                        handler.sendMessage(message);
                    }else{
                        //浏览记录上传服务器
                        List<NameValuePair> postParam1 = new ArrayList<>();
                        Calendar calendar = Calendar.getInstance(); // get current instance of the calendar
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String dataTime=formatter.format(calendar.getTime());
                        postParam1.add(new NameValuePair("user_id", user_id));
                        postParam1.add(new NameValuePair("book_name", book_name));
                        postParam1.add(new NameValuePair("time", dataTime));
                        Result result=HttpCaller.getInstance().postSyncResult(Result.class, "http://192.168.1.4:8080/com.lianggao.whut/Post_User_Read_History_Servlet",postParam1);
                        //

                        if(fileIsExists(saveFilePath)){
                            System.out.println("本地已经存在文本文件");
                            message.what=MSG_DOWNLOADCHCHE_SUCCESS;
                            message.obj=saveFilePath;
                            handler.sendMessage(message);
                        }else{
                            System.out.println("开始缓存书籍文件" + saveFilePath + "  " + url);
                            Toast.makeText(getContext(),"请稍等正在缓存书籍，缓存完将自动打开...",Toast.LENGTH_SHORT).show();
                            HttpCaller.getInstance().downloadFile(url, saveFilePath, null, new ProgressUIListener() {
                                @Override
                                public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                                    Log.i("正在下载", "dowload file content numBytes:" + numBytes + " totalBytes:" + totalBytes + " percent:" + percent + " speed:" + speed);
                                    if(percent==1.0){
                                        Toast.makeText(getContext(),"下载完成",Toast.LENGTH_SHORT).show();
                                        message.what=MSG_DOWNLOADCHCHE_SUCCESS;
                                        message.obj=saveFilePath;
                                        handler.sendMessage(message);
                                    }
                                }
                            });
                            System.out.println("缓存书籍文件完成");
                        }



                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }



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

    public void addStart(){

        new Thread() {
            @Override
            public void run() {
                SharedPreferences sp=getApplicationContext().getSharedPreferences("QQFile", Context.MODE_PRIVATE);
                String user_id=sp.getString("flag","");
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("user_id",user_id));
                postParam.add(new NameValuePair("book_name",book_name));
                HttpCaller.getInstance().postSyncResult(Result.class,"http://192.168.1.4:8080/com.lianggao.whut/Post_Book_Star_Servlet",postParam)  ;
                Message message=new Message();
                message.what=MSG_ADD_STAR;
                handler.sendMessage(message);

            }
        }.start();


    }
    public void ifStart(){

        new Thread() {
            @Override
            public void run() {
                SharedPreferences sp=getApplicationContext().getSharedPreferences("QQFile", Context.MODE_PRIVATE);
                String user_id=sp.getString("flag","");
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("user_id",user_id));
                postParam.add(new NameValuePair("book_name",book_name));
                Result result=HttpCaller.getInstance().postSyncResult(Result.class,"http://192.168.1.4:8080/com.lianggao.whut/Post_If_Book_Star_Servlet",postParam)  ;
                Message message=new Message();

                if(result.getResult().equals("false")){
                    message.what=MSG_NOT_STAR;
                    handler.sendMessage(message);
                }else{
                    message.what=MSG_IS_STAR;
                    handler.sendMessage(message);
                }

            }
        }.start();


    }

    private DialogInterface.OnClickListener onClickListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which){
                case DialogInterface.BUTTON_POSITIVE:
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
}
