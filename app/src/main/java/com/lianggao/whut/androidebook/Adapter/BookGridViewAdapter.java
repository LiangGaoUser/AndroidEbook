package com.lianggao.whut.androidebook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lianggao.whut.androidebook.R;

import java.util.List;


public class BookGridViewAdapter extends BaseAdapter {
    private List<String> book_name_list;//书名集合
    private List<Integer>book_post_list;//书的封面集合
    private List<String>book_author_list;//书的作者集合
    private LayoutInflater inflater;
    public BookGridViewAdapter(Context context, List<String>book_name_list, List <Integer>book_post_list,  List<String>book_author_list){
        inflater=LayoutInflater.from(context);
        this.book_name_list=book_name_list;
        this.book_post_list=book_post_list;
        this.book_author_list=book_author_list;

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
            convertView= inflater.inflate(R.layout.part_activity_book_gridview,null);
            viewHolder.textView=(TextView)convertView.findViewById(R.id.id_tv_book_img);
            viewHolder.textViewName=(TextView)convertView.findViewById(R.id.id_tv_book_name);
            viewHolder.textViewAuthor=(TextView)convertView.findViewById(R.id.id_tv_book_author);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();

        }
        viewHolder.textView.setBackgroundResource(book_post_list.get(position));
        viewHolder.textViewName.setText(book_name_list.get(position));
        viewHolder.textViewAuthor.setText(book_author_list.get(position));
        return convertView;
    }
    private class ViewHolder{
        private TextView textView;//书的封面
        private TextView textViewName;//书的名字
        private TextView textViewAuthor;//书的名字

    }
}
