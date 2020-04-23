package com.lianggao.whut.androidebook;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


//BookShelfGridView.java  activity_bookshelf_addbar.xml   menu_bookshelf_toolbar.xml 只是设置了toolbar没有其他的内容填充
public class Activity_BookShelfAddBar extends AppCompatActivity {
    /*private BookShelfGridView bookShelfGridView;//书架布局类
    private Button back_Btn;//书架中的返回按钮
    private BookShelfGridViewAdapter bookShelfGridViewAdapter;
    private List<String> book_name_list;//书名集合
    private List<Integer>book_post_list;//书的封面集合
    private List<Integer>book_percent_list;//书的已读百分比集合
    private List<Boolean>book_download_list;//书是否已经下载的集合*/

    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf_addbar);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bookshelf_toolbar, menu);
        return true;
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

}
