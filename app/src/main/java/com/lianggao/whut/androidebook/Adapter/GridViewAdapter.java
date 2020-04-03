package com.lianggao.whut.androidebook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianggao.whut.androidebook.R;
import java.util.LinkedList;
import java.util.List;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 9:29
 */
public class GridViewAdapter extends BaseAdapter {
    private List<Integer> postList;
    private List<String>nameList;
    private List<String>authorList;
    private LayoutInflater layoutInflater;
    private int number=0;
    public GridViewAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
        initData();
    }
    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.part_book_gridview,null);
        }
        ImageView imageView=(ImageView)convertView.findViewById(R.id.book_post);
        TextView textView1=(TextView)convertView.findViewById(R.id.book_name);
        TextView textView2=(TextView)convertView.findViewById(R.id.book_progress);
        imageView.setImageResource(postList.get(position));
        textView1.setText(nameList.get(position));
        textView2.setText(authorList.get(position));
        return convertView;
    }

    void initData(){
        postList=new LinkedList<>();
        nameList=new LinkedList<>();
        authorList=new LinkedList<>();
        for(int i=number;i<8;i++){
            postList.add(R.drawable.img_bookshelf_everybook);
            nameList.add("book"+i);
            authorList.add("author"+i);
        }

    }

    public void loadMoreData(){
        System.out.println("4");
        for(int i=0;i<40;i++){
            number++;
            postList.add(R.drawable.img_bookshelf_everybook);
            nameList.add("book"+number);
            authorList.add("author"+number);
        }
       // this.notifyDataSetChanged();
    }
}
