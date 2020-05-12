package com.lianggao.whut.androidebook.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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


import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.lianggao.whut.androidebook.Activity_BookDetail;
import com.lianggao.whut.androidebook.Activity_BookShelf_Delete;
import com.lianggao.whut.androidebook.Activity_BookShelf_History;
import com.lianggao.whut.androidebook.Activity_BookShelf_Kind;
import com.lianggao.whut.androidebook.Activity_Read;
import com.lianggao.whut.androidebook.Activity_Read_Pdf;
import com.lianggao.whut.androidebook.HwTxtPlayActivityOverWrite;
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

import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

//类似于BookShelfGridView.java的作用，用来获取fragment_bookshelf.xml中的内容
//BookShelfGridViewAdapter.java和BookShelfGridView.java不再使用，因为在BooksShelfView.java中绘制非常耗费时间，换成使用
//的gridview和simadapter来替代之前的写法，这样不会出现卡顿的现象，而图片阴影框架没有使用到，之后也许还需要上面的来实现书单阴影
public class FragmentBookShelf extends ViewPageFragment {

    private List<String>book_name_list;
    private List<String>book_author_list;
    private List<String>book_post_list;
    private List<String>book_path_list;

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
    private final int MSG_DELETE_ALL_BOOKS_SUCCESS=2;
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

                            if(!Util.isPdf(book_path_list.get(position))){
                                //HwTxtPlayActivity.loadTxtFile(getContext(), book_path_list.get(position));
                                HwTxtPlayActivityOverWrite.loadTxtFile(getContext(), book_path_list.get(position),book_name_list.get(position));
                            }else{
                                Intent intent=new Intent(getActivity(), Activity_Read_Pdf.class);
                                intent.putExtra("path",book_path_list.get(position));
                                intent.putExtra("name",book_name_list.get(position));
                                startActivity(intent);
                            }

                        }
                    });
                    System.out.println("本地书架图书加载完成");
                    break;

                case MSG_DELETE_ALL_BOOKS_SUCCESS:
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
                            if(CheckPermission()){
                                Permit=true;
                                chooseFile();

                            }
                            else{

                            }

                            return true;
                        case R.id.toolbar_action2:
                            Log.i("haha", "toolbar_action2");
                            Intent intent=new Intent(getActivity(), Activity_BookShelf_Delete.class);
                            startActivity(intent);


                            return true;
                        case R.id.toolbar_action3:
                            Log.i("haha", "toolbar_action3");
                            Intent intent2=new Intent(getActivity(), Activity_BookShelf_History.class);
                            startActivity(intent2);
                            return true;
                        case R.id.toolbar_action4:
                            Log.i("haha", "toolbar_action4");
                            Intent intent3=new Intent(getActivity(), Activity_BookShelf_Kind.class);
                            startActivity(intent3);
                            return true;
                        case R.id.toolbar_action5:
                            Log.i("haha", "toolbar_action5");
                            delteAllBook();
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
        File file= new File(getActivity().getExternalFilesDir("Cover").getPath() );
        if(!file.exists()){
            file.mkdir();
        }
        File file2= new File(getActivity().getExternalFilesDir("Content").getPath());
        if(!file2.exists()){
            file2.mkdir();
        }
        File file3= new File(getActivity().getExternalFilesDir("CacheContent").getPath());
        if(!file3.exists()){
            file3.mkdir();
        }
        File file4= new File(getActivity().getExternalFilesDir("CacheCover").getPath());
        if(!file4.exists()){
            file4.mkdir();
        }
        File file5=new File(getActivity().getExternalFilesDir("CoverBookShelfHistory").getPath());
        if(!file5.exists()){
            file5.mkdir();
        }


        return rootView;
    }



    @Override
    public void onStart() {
        super.onStart();

        Log.i("FragmentBookShelf","onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        //Log.i("FragmentBookShelf","onResume");
        //getLocalBookShelf();
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


    //得到数据库中存储的书架文件，得到相应的文件进行显示
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
                book_path_list=new LinkedList<>();
                data_list = new ArrayList<Map<String, Object>>();
                List<Bitmap>bitmapList;
                bitmapList=new LinkedList<>();
                for(int i=0;i<bookList.size();i++){
                    book_path_list.add(bookList.get(i).getBook_path());
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



    ////////////////////加载本地文件
    private String path;
    private boolean  Permit;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0x01;
   /* public void chooseFile() {
        System.out.println("选择本地文件到书架开始");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");//设置类型
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 3);
    }*/
    public void chooseFile() {
        System.out.println("选择本地文件到书架开始");
        String [] mimeTypes={"application/pdf","text/plain"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra("android.intent.extra.MIME_TYPES",mimeTypes);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 3);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            path="";
            try {

                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT){//4.4以后
                    path=getPath(getContext(),uri);
                    Toast.makeText(getContext(),path,Toast.LENGTH_SHORT).show();
                }else{
                    path=getRealPathFromURI(uri);
                    Toast.makeText(getContext(),path,Toast.LENGTH_SHORT).show();
                }
                loadFile();
            } catch (Exception e) {
                toast("选择出错了");
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri){
        String res=null;
        String[] pros = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor=getActivity().getContentResolver().query(contentUri,pros,null,null,null);
        String path="";
        if(null!=cursor&&cursor.moveToFirst()) {

            int actual_txt_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(actual_txt_column_index);
            cursor.close();
        }
        return path;

    }

    public String getPath(final Context context, final Uri uri){
        final boolean isKitKat=Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT;
        //DocumentProvider
        if(isKitKat&& DocumentsContract.isDocumentUri(context,uri)){
            if(isExternalStorageDocument(uri)){
                final String docId=DocumentsContract.getDocumentId(uri);
                final String []split=docId.split(":");
                final String type=split[0];
                if("primary".equals(type)){
                    return Environment.getExternalStorageDirectory()+"/"+split[1];
                }
            }
            else if(isDownloadsDocument(uri)){
                final String id=DocumentsContract.getDocumentId(uri);
                final Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));
                return getDataColumn(context,contentUri,null,null);
            }
            else if(isMediaDocument(uri)){
                final String docId=DocumentsContract.getDocumentId(uri);
                final String []split=docId.split(":");
                final String type=split[0];
                Uri contentUri=null;

                if("image".equals(type)){
                    contentUri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                }else if("video".equals(type)){
                    contentUri=MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }else if("audio".equals(type)){
                    contentUri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection="_id=?";
                final String []selectionArgs=new String[]{split[1]};
                return getDataColumn(context,contentUri,selection,selectionArgs);
            }

        }
        else if("content".equalsIgnoreCase(uri.getScheme())){
            return getDataColumn(context,uri,null,null);
        }
        else if("file".equalsIgnoreCase(uri.getScheme())){
            return uri.getPath();
        }
        return null;
    }

    public String getDataColumn(Context context,Uri uri,String selection,String []selectionArgs){
        Cursor cursor=null;
        final String column="_data";
        final String[]projection={column};
        try{
            cursor=context.getContentResolver().query(uri,projection,selection,selectionArgs,null);
            if(cursor!=null&&cursor.moveToFirst()){
                final int column_index=cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            return null;
        }
    }
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public void loadFile() {
        if (Permit) {
            TxtConfig.saveIsOnVerticalPageMode(getContext(),false);
            Book book=new Book();
            File file=new File(path);
            book.setBook_path(path);

            book.setBook_name(file.getName());
            if(!Util.isPdf(path)){
                addLocalBook(book);
            }else{
                addLocalBookPDF(book);
            }

            Log.i("输出","#######"+path);
            System.out.println("选择本地文件到书架完成");
        }
    }


    private Boolean CheckPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Permit = true;
                loadFile();
            } else {
                // Permission Denied
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private Toast t;

    private void toast(String msg) {
        if (t != null) {
            t.cancel();
        }
        t = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        t.show();
    }
    //本地选择文件导入到书架中
    private void addLocalBook(final Book book){//上传txt类型的书籍
        new Thread(){
            @Override
            public void run() {
                bookshelfTableManger=new bookShelfTableManger(getContext());
                bookshelfTableManger.createDb();
                //bookshelfTableManger.addBook(book);
                String newPath=getActivity().getExternalFilesDir("Content")+"/"+book.getBook_name();
                //复制文件到path文件夹
                Util.copyFile(book.getBook_path(),newPath);
                //复制系统默认封面到post文件夹
                String name=book.getBook_name().substring(0,book.getBook_name().length()-4);
                String newPostPath=getActivity().getExternalFilesDir("Cover")+"/"+name+".jpg";
                AssetManager assetManager=getActivity().getAssets();
                Util.copyFilePost(assetManager,newPostPath);
                bookshelfTableManger=new bookShelfTableManger(getContext());
                bookshelfTableManger.createDb();
                Book book=new Book();

                book.setBook_name(name);
                book.setBook_cover_path(newPostPath);
                book.setBook_path(newPath);
                book.setBook_main_kind("本地");
                book.setBook_detail_kind("文档");
                bookshelfTableManger.addBook(book);
                getLocalBookShelf();
            }
        }.start();
    }

    //删除书架中所有的书籍以及本地保存的内容
    private void delteAllBook(){
        new Thread(){
            @Override
            public void run() {
                bookshelfTableManger=new bookShelfTableManger(getContext());
                bookshelfTableManger.createDb();
                Util.deleteAllBook(bookshelfTableManger.findAllPathList(),bookshelfTableManger.findAllCoverList());//删除本地内容
                bookshelfTableManger.deleteTableContent();//首先删除表中内容
                getLocalBookShelf();
            }
        }.start();

    }


    private void addLocalBookPDF(final Book book){//上传pdf类型的书籍
        new Thread(){
            @Override
            public void run() {
                bookshelfTableManger=new bookShelfTableManger(getContext());
                bookshelfTableManger.createDb();
                //bookshelfTableManger.addBook(book);
                String newPath=getActivity().getExternalFilesDir("Content")+"/"+book.getBook_name();
                //复制文件到path文件夹
                Util.copyFile(book.getBook_path(),newPath);
                //复制系统默认封面到post文件夹
                String name=book.getBook_name().substring(0,book.getBook_name().length()-4);
                String newPostPath=getActivity().getExternalFilesDir("Cover")+"/"+name+".jpg";
                AssetManager assetManager=getActivity().getAssets();
                Util.copyFilePdfPost(assetManager,newPostPath);
                bookshelfTableManger=new bookShelfTableManger(getContext());
                bookshelfTableManger.createDb();
                Book book=new Book();
                book.setBook_name(name);
                book.setBook_cover_path(newPostPath);
                book.setBook_path(newPath);
                book.setBook_main_kind("本地");
                book.setBook_detail_kind("文档");
                bookshelfTableManger.addBook(book);
                getLocalBookShelf();
            }
        }.start();
    }


}
