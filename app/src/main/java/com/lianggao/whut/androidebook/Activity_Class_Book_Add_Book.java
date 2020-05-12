package com.lianggao.whut.androidebook;


import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Adapter.DeleteBookShelfRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Fragment.MyAlertDialogFragment;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Model.ClassBook;
import com.lianggao.whut.androidebook.Utils.ClassBookTableManger;
import com.lianggao.whut.androidebook.Utils.Util;
import com.lianggao.whut.androidebook.Utils.bookShelfTableManger;

import java.util.LinkedList;
import java.util.List;


public class Activity_Class_Book_Add_Book extends FragmentActivity {
    private RecyclerView recyclerView;
    private DeleteBookShelfRecyclerViewAdapter deleteBookShelfRecyclerViewAdapter;

    private List<String> book_post_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_check_list;//书的种类集合

    private List<Book> bookList;

    private List<String> book_chosed_path_list;//书的封面集合

    private List<String> book_chosed_cover_list;//书的封面集合

    private List<String> book_chosed_name_list;//书的封面集合


    private TextView deleteTextView;
    private bookShelfTableManger bookShelfTableManger;
    private final int MSG_GET_LOCAL_BOOKS_SUCCESS=1;

    private Button button;
    private ClassBookTableManger classBookTableManger;
    private FragmentActivity fragmentActivity;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_LOCAL_BOOKS_SUCCESS:
                    Log.i("获取图片","批量获取图片成功");


                    deleteBookShelfRecyclerViewAdapter=new DeleteBookShelfRecyclerViewAdapter(getApplicationContext(),book_post_list, book_name_list,book_author_list,book_check_list,book_shortcontent_list);

                    deleteBookShelfRecyclerViewAdapter.setOnItemClickListener(new DeleteBookShelfRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if(deleteBookShelfRecyclerViewAdapter.getCheckList().get(position).equals("true")){
                                deleteBookShelfRecyclerViewAdapter.setCheckListAllFalse();
                                for(int number=0;number<book_check_list.size();number++){
                                        book_check_list.set(number,"false");
                                }
                                book_chosed_name_list.clear();

                            }else if(deleteBookShelfRecyclerViewAdapter.getCheckList().get(position).equals("false")){
                                deleteBookShelfRecyclerViewAdapter.setCheckListAllFalse();
                                deleteBookShelfRecyclerViewAdapter.setCheckListTrue(position);

                                for(int number=0;number<book_check_list.size();number++){
                                    if(number!=position){
                                        book_check_list.set(number,"false");
                                    }else{
                                        book_check_list.set(position,"true");
                                    }
                                }
                                book_chosed_name_list.clear();
                                book_chosed_name_list.add(book_name_list.get(position));//将选择的名称放入列表中


                            }
                        }
                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(deleteBookShelfRecyclerViewAdapter);




                    Log.i("初始化","初始化结束");
                    break;
            }
        }
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_book_add_book);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        fragmentActivity= this.fragmentActivity;

        //返回
        deleteTextView=(TextView)findViewById(R.id.deleteTextView);
        Drawable back= getResources().getDrawable(R.drawable.icon_book_return2);
        back.setBounds(0, 0, 50, 50);
        deleteTextView.setCompoundDrawables(back, null, null, null);
        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button=(Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(book_chosed_name_list.isEmpty()){
                    Toast.makeText(getApplicationContext(),"请选择导入的书籍",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"确定导入",Toast.LENGTH_LONG).show();
                    addClassBook();
                }
            }
        });
        getLocalBookShelf();


        book_chosed_path_list=new LinkedList<>();
        book_chosed_cover_list=new LinkedList<>();
        book_chosed_name_list=new LinkedList<>();
    }


    private void getLocalBookShelf(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        new Thread(){
            @Override
            public void run() {

                bookShelfTableManger=new bookShelfTableManger(getApplicationContext());
                bookShelfTableManger.createDb();
                bookList=bookShelfTableManger.findAllBook();
                System.out.println("开始获取本地书架图书");

                book_name_list=new LinkedList<>();
                book_post_list=new LinkedList<>();
                book_author_list=new LinkedList<>();
                book_shortcontent_list=new LinkedList<>();
                book_check_list=new LinkedList<>();

                for(int i=0;i<bookList.size();i++){

                    book_name_list.add(bookList.get(i).getBook_name());
                    book_post_list.add(bookList.get(i).getBook_cover_path());
                    book_author_list.add(bookList.get(i).getBook_author());
                    book_shortcontent_list.add(" ");
                    book_check_list.add("false");
                }
                Message message=new Message();
                message.what=MSG_GET_LOCAL_BOOKS_SUCCESS;
                handler.sendMessage(message);

            }
        }.start();

    }


    private void addClassBook(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        new Thread(){
            @Override
            public void run() {

                //删除书架中所有的书籍以及本地保存的内容

                classBookTableManger=new ClassBookTableManger(getApplicationContext());
                classBookTableManger.createDb();


                bookShelfTableManger=new bookShelfTableManger(getApplicationContext());
                bookShelfTableManger.createDb();
                ClassBook classBook=bookShelfTableManger.findBookByName2(book_chosed_name_list.get(0));
                classBook.setClass_name(getIntent().getStringExtra("className"));
                classBookTableManger.setFileNameByClassName(classBook);
                finish();

            }
        }.start();

    }







}
