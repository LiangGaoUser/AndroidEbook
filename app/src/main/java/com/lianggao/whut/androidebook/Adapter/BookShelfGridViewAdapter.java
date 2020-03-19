package com.lianggao.whut.androidebook.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lianggao.whut.androidebook.MainActivity;
import com.lianggao.whut.androidebook.R;

import java.util.List;


public class BookShelfGridViewAdapter extends BaseAdapter {
    private List<String> book_name_list;//书名集合
    private List<Integer>book_post_list;//书的封面集合
    private List<Integer>book_percent_list;//书的已读百分比集合
    private List<Boolean>book_download_list;//书是否已经下载的集合
    private LayoutInflater inflater;
    public BookShelfGridViewAdapter(Context context,List<String>book_name_list,List <Integer>book_post_list,List<Integer>book_percent_list,List<Boolean>book_download_list){
        inflater=LayoutInflater.from(context);
        this.book_name_list=book_name_list;
        this.book_post_list=book_post_list;
        this.book_percent_list=book_percent_list;
        this.book_download_list=book_download_list;
    }

    @Override
    public int getCount() {
        return book_name_list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= inflater.inflate(R.layout.part_activity_everybook,null);
            viewHolder.textView=(TextView)convertView.findViewById(R.id.id_tv_book_img);
            viewHolder.textViewContent=(TextView)convertView.findViewById(R.id.id_tv_book_content);
            viewHolder.textViewAlreadyRead=(TextView)convertView.findViewById(R.id.id_book_already_read);
            viewHolder.textViewBookEtc=(TextView)convertView.findViewById(R.id.id_ib_book_etc);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();

        }
        //遇到的问题如何在Adapter中将省略号用iconfont进行初始化,为什么会提示找不到路径
        //ContextThemeWrapper contextThemeWrapper=new ContextThemeWrapper();
        //AssetManager assetManager ;
        //Typeface iconfont= Typeface.createFromFile("D:/androidproject/AndroidEbook/app/src/main/assets/iconfont/iconfont.ttf");
        //viewHolder.textViewBookEtc.setTypeface(iconfont);
        viewHolder.textView.setBackgroundResource(book_post_list.get(position));
        viewHolder.textViewContent.setText(book_name_list.get(position));
        viewHolder.textViewAlreadyRead.setText("已读%"+book_percent_list.get(position).toString());
        return convertView;
    }
    private class ViewHolder{
        private TextView textView;//书的封面
        private TextView textViewContent;//书的名字
        private TextView textViewAlreadyRead;//已经读了多少
        private TextView textViewBookEtc;//省略号
    }
}
