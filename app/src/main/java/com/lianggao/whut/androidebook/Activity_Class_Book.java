package com.lianggao.whut.androidebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.lianggao.whut.androidebook.Adapter.ClassBookRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Model.ClassBook;
import com.lianggao.whut.androidebook.Utils.ClassBookTableManger;
import com.lianggao.whut.androidebook.Utils.ClassTableManger;
import com.lianggao.whut.androidebook.Utils.Util;

import java.util.LinkedList;
import java.util.List;


public class Activity_Class_Book extends Activity {
    private RecyclerView recyclerView;
    private ClassBookTableManger classBookTableManger;

    private List<ClassBook> classBookList;
    private List<String>fileNameList;
    private List<String>classNameList;
    private List<String>filePathList;


    private ClassBookRecyclerViewAdapter classBookRecyclerViewAdapter;


    private final int MSG_GET_LOCAL_CLASS_BOOK_SUCCESS=1;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_LOCAL_CLASS_BOOK_SUCCESS:
                    Log.i("获取本地","批量课程表和图书");
                    classBookRecyclerViewAdapter=new ClassBookRecyclerViewAdapter(getApplicationContext(), classNameList,fileNameList,filePathList);
                    classBookRecyclerViewAdapter.setOnItemClickListener(new ClassBookRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            showPopupMenu(view,classNameList.get(position));
                        }
                    });

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(classBookRecyclerViewAdapter);
                    Log.i("初始化","初始化结束");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_book);
        recyclerView=(RecyclerView)findViewById(R.id.class_book_recycleview);
        getLocalClassBook();

    }
    private void getLocalClassBook(){
        new Thread(){
            @Override
            public void run() {
                classBookTableManger=new ClassBookTableManger(getApplicationContext());
                classBookTableManger.createDb();
                classBookList=classBookTableManger.findAllClassBook();
                System.out.println("开始获取本地课程表和图书");

                fileNameList=new LinkedList<>();
                filePathList=new LinkedList<>();
                classNameList=new LinkedList<>();
                for(int i=0;i<classBookList.size();i++){
                    System.out.println("#########################"+classBookList.get(i).getClass_name());
                    fileNameList.add(classBookList.get(i).getFile_name());
                    filePathList.add(classBookList.get(i).getFile_path());
                    classNameList.add(classBookList.get(i).getClass_name());


                }
                Message message=new Message();
                message.what=MSG_GET_LOCAL_CLASS_BOOK_SUCCESS;
                handler.sendMessage(message);

            }
        }.start();

    }

    private void showPopupMenu(View view, final String className){
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_each_book_class_click,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_delete_file:
                        Toast.makeText(getApplicationContext(),"点击了删除文件",Toast.LENGTH_SHORT).show();
                        classBookTableManger=new ClassBookTableManger(getApplicationContext());
                        classBookTableManger.createDb();
                        classBookTableManger.deleteClassBook(className);
                        getLocalClassBook();

                        break;
                    case R.id.id_input_file:
                        Toast.makeText(getApplicationContext(),"点击了导入文件",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),Activity_Class_Book_Add_Book.class);
                        intent.putExtra("className",className);
                        startActivityForResult(intent,1);

                        break;
                    case R.id.id_start_read:
                        Toast.makeText(getApplicationContext(),"点击了开始阅读",Toast.LENGTH_SHORT).show();

                        classBookTableManger=new ClassBookTableManger(getApplicationContext());
                        classBookTableManger.createDb();

                        ClassBook classBook=classBookTableManger.findByName2(className);
                        System.out.println("######"+classBook.getFile_path());
                        if(!Util.isPdf(classBook.getFile_path())){
                            HwTxtPlayActivityOverWrite.loadTxtFile(getApplicationContext(), classBook.getFile_path());
                        }else{
                            Intent intent2=new Intent(Activity_Class_Book.this, Activity_Read_Pdf.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent2.putExtra("path",classBook.getFile_path());
                            intent2.putExtra("name",classBook.getFile_name());
                            startActivity(intent2);
                        }



                        break;
                    case R.id.id_delete_class:
                        Toast.makeText(getApplicationContext(),"删除该课程",Toast.LENGTH_SHORT).show();
                        classBookTableManger=new ClassBookTableManger(getApplicationContext());
                        classBookTableManger.createDb();
                        classBookTableManger.dropClassBook(className);
                        ClassTableManger classTableManger=new ClassTableManger(getApplicationContext());
                        classTableManger.createDb();
                        classTableManger.dropTable(className);


                        getLocalClassBook();


                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode==1){
           getLocalClassBook();
       }

    }
}