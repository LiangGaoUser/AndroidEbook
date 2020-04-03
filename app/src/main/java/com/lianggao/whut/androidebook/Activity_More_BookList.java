package com.lianggao.whut.androidebook;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Adapter.GridViewAdapter;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.View.DrawableTextView;
import com.lianggao.whut.androidebook.View.LoadMoreGridView;


public class Activity_More_BookList extends AppCompatActivity {
    private LoadMoreGridView loadMoreGridView;
    private GridViewAdapter gridViewAdapter;
    private final int MSG_SUCCESS_LOADMORE =1;
    private final int MSG_ALL_LOADMORE=2;
    private TextView textView;//返回
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_SUCCESS_LOADMORE:

                    Toast.makeText(getApplicationContext(),"成功获得数据",Toast.LENGTH_SHORT).show();

                    loadMoreGridView.footerLl.setVisibility(View.GONE);
                    gridViewAdapter.notifyDataSetChanged();
                    break;
                case MSG_ALL_LOADMORE:
                    loadMoreGridView.loadDataComplete();
                    Toast.makeText(getApplicationContext(),"已经全部加载完成",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_booklist);
        loadMoreGridView=(LoadMoreGridView) findViewById(R.id.loadMoreGridView);
        gridViewAdapter=new GridViewAdapter(getApplicationContext());
        loadMoreGridView.setAdapter(gridViewAdapter);

        LoadMoreGridView.OnLoadGridListener onLoadGridListener=new LoadMoreGridView.OnLoadGridListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("click "+position);
            }

            @Override
            public void onLoadMore() {

                new MyThread().start();
            }
        };
        loadMoreGridView.setOnLoadGridListener(onLoadGridListener);

        textView=(TextView)findViewById(R.id.id_booklist_return);
        Drawable drawable = getResources().getDrawable(R.drawable.icon_book_return);
        drawable.setBounds(0, 0, 50, 50);
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    class MyThread extends  Thread{
        @Override
        public void run() {
            Message msg=new Message();
            if(gridViewAdapter.getCount()<200){
                gridViewAdapter.loadMoreData();
                msg.what=MSG_SUCCESS_LOADMORE;
            }
            else{

                msg.what=MSG_ALL_LOADMORE;
            }
            mHandler.sendMessage(msg);
        }
    }


}
