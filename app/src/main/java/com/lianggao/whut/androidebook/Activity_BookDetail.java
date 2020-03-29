package com.lianggao.whut.androidebook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.View.DrawableTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private RatingBar ratingBar;
    private GridView gridView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> data_list;
    private List<Integer>booklist_post_list;//书的封面
    private List<String>booklist_name_list;//书的名字
    private List<Integer>booklist_author_list;//书的作者
    private DrawableTextView textViewCatalog;//目录图标
    private TextView textViewReturn;//返回图标
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);
        ratingBar=(RatingBar)findViewById(R.id.ratingbar) ;
        ratingBar.setRating(3);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(Activity_BookDetail.this,"rating "+rating+"",Toast.LENGTH_SHORT).show();
            }
        });

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
        textViewCatalog=(DrawableTextView)findViewById(R.id.id_tv_catalog);
        Drawable drawable= getResources().getDrawable(R.drawable.icon_book_catalog);
        drawable.setBounds(0, 0, 70, 70);
        textViewCatalog.setCompoundDrawables(null, drawable, null, null);
        textViewCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"点击了图片 ",Toast.LENGTH_LONG).show();
            }
        });
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
}
