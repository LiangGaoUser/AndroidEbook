package com.lianggao.whut.androidebook.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.lianggao.whut.androidebook.Adapter.BookshelfKindRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.Utils.bookShelfTableManger;

import java.util.LinkedList;
import java.util.List;


public class FragmentScience extends Fragment {
    private View rootView;
    //
    private RecyclerView recyclerView;
    private BookshelfKindRecyclerViewAdapter netRecyclerViewAdapter;
    //
    private bookShelfTableManger bookShelfTableManger;

    //
    private List<Book> bookList;
    private List<String>book_post_list;
    private List<String>book_name_list;
    private List<String>book_author_list;
    private List<String>book_shortcontent_list;
    private List<String>book_kind_list;
    private List<String>book_path_list;

    //
    private final int MSG_GET_NOVEL_SUCCESS=1;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GET_NOVEL_SUCCESS:
                    netRecyclerViewAdapter=new BookshelfKindRecyclerViewAdapter(getContext(),book_post_list,book_name_list,book_author_list,book_kind_list,book_shortcontent_list);
                    netRecyclerViewAdapter.setOnItemClickListener(new BookshelfKindRecyclerViewAdapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(View view, int position) {
                            String path=book_path_list.get(position);
                            HwTxtPlayActivity.loadTxtFile(getContext(), path);
                        }
                    });



                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(netRecyclerViewAdapter);
                    break;

            }
        }
    };


    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(rootView==null){
           rootView=inflater.inflate(R.layout.fragment_novel,null);
           recyclerView= rootView.findViewById(R.id.recyclerView);
           getBookShelfScience();
       }
        return rootView;
    }

    private void getBookShelfScience(){
        new Thread(){
            @Override
            public void run() {
                bookShelfTableManger=new bookShelfTableManger(getContext());
                bookShelfTableManger.createDb();
                bookList= bookShelfTableManger.findAllScience();
                System.out.println("开始获取书架历史图书");

                book_name_list=new LinkedList<>();
                book_post_list=new LinkedList<>();
                book_author_list=new LinkedList<>();
                book_shortcontent_list=new LinkedList<>();
                book_kind_list=new LinkedList<>();
                book_path_list=new LinkedList<>();
                for(int i=0;i<bookList.size();i++){

                    book_name_list.add(bookList.get(i).getBook_name());
                    book_post_list.add(bookList.get(i).getBook_cover_path());
                    book_author_list.add(bookList.get(i).getBook_author());
                    book_shortcontent_list.add(" ");
                    book_kind_list.add(" ");
                    book_path_list.add(bookList.get(i).getBook_path());
                }
                Message message=new Message();
                message.what=MSG_GET_NOVEL_SUCCESS;
                handler.sendMessage(message);



            }
        }.start();
    }




}
