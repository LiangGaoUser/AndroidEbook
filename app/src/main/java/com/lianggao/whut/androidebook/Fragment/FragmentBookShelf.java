package com.lianggao.whut.androidebook.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Activity_BookShelf;
import com.lianggao.whut.androidebook.Adapter.BookShelfGridViewAdapter;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.View.BookShelfGridView;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

//类似于BookShelfGridView.java的作用，用来获取fragment_bookshelf.xml中的内容
public class FragmentBookShelf extends ViewPageFragment {
    private BookShelfGridView bookShelfGridView;//书架布局类
    //private Button back_Btn;//书架中的返回按钮
    private BookShelfGridViewAdapter bookShelfGridViewAdapter;
    /*private TextView  id_tv_book_number;
    private TextView id_tv_book_menu;
    private TextView id_tv_book_search;*/
    private List<String> book_name_list;//书名集合
    private List<Integer>book_post_list;//书的封面集合
    private List<Integer>book_percent_list;//书的已读百分比集合
    private List<Boolean>book_download_list;//书是否已经下载的集合
    private Toolbar toolbar;
    //    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null) {
            rootView = inflater.inflate(R.layout.fragment_bookshelf, null);
            bookShelfGridView=(BookShelfGridView)rootView.findViewById(R.id.BookShelfGridView);
            initData();
            bookShelfGridViewAdapter=new BookShelfGridViewAdapter(getContext(),book_name_list,book_post_list,book_percent_list,book_download_list);
            bookShelfGridView.setAdapter(bookShelfGridViewAdapter);
            /*id_tv_book_number=(TextView)rootView.findViewById(R.id.id_tv_book_number);
            id_tv_book_menu=(TextView)rootView.findViewById(R.id.id_tv_book_menu);
            id_tv_book_search=(TextView)rootView.findViewById(R.id.id_tv_book_search);*/

            /*Typeface iconfont= Typeface.createFromAsset(this.getActivity().getAssets(), "iconfont/iconfont.ttf");
            id_tv_book_menu.setTypeface(iconfont);
            id_tv_book_search.setTypeface(iconfont);*/

           /* back_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"点击了返回按钮",Toast.LENGTH_LONG).show();
                    showPopupMenu(v);
                }
            });
            bookShelfGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getContext(),"点击了书本"+position,Toast.LENGTH_LONG).show();
                }
            });*/
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
                    }
                    return true;
                }
            });

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
            Log.i("FragmentBookShelf","aaa");
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

    /**
     * 初始化要在BookShelfGridView中的适配器中的数据
     */
    private void initData(){
        book_name_list=new LinkedList<>();
        book_post_list=new LinkedList<>();
        book_percent_list=new LinkedList<>();
        book_download_list=new LinkedList<>();
        for(int i=0;i<20;i++){
            book_name_list.add("book"+i);
            book_post_list.add(R.drawable.img_bookshelf_everybook);
            book_percent_list.add(32);
            book_download_list.add(true);
        }
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_bookshelf_toolbar, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_search:
                Log.i("haha", "toolbar_search");
                return true;
            case R.id.toolbar_action1:
                Log.i("haha", "toolbar_action1");
                return true;
            case R.id.toolbar_action2:
                Log.i("haha", "toolbar_action2");
                return true;
        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
        Toolbar toolbar= (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        appCompatActivity.setSupportActionBar(toolbar);
        super.onActivityCreated(savedInstanceState);
    }*/
}
