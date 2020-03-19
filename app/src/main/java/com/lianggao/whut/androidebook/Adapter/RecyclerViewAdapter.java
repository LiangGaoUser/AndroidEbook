package com.lianggao.whut.androidebook.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.Utils.CreateReflectionBitmap;
import com.lianggao.whut.androidebook.View.BookNameTextView;

import java.util.List;
import java.util.ResourceBundle;

import static android.graphics.BitmapFactory.decodeResource;

/**
 * @author LiangGao
 * @description:recyclerView的适配器，可以用来展示书
 * @data:${DATA} 16:17
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Integer>book_post_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_kind_list;//书的种类集合
    private LayoutInflater inflater;
    public RecyclerViewAdapter(Context context,List<Integer>book_post_list,List<String>book_name_list,List<String>book_author_list,List<String>book_kind_list,List<String>book_shortcontent_list){
        inflater=LayoutInflater.from(context);
        this.book_post_list=book_post_list;
        this.book_name_list=book_name_list;
        this.book_author_list=book_author_list;
        this.book_shortcontent_list=book_shortcontent_list;
        this.book_kind_list=book_kind_list;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.part_activity_book_recycler,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tvBookAuthor.setText(book_author_list.get(i));
        myViewHolder.tvBookName.setText(book_name_list.get(i));
        //myViewHolder.tvBookPost.setBackgroundResource(book_post_list.get(i));
        myViewHolder.tvBookPost.setImageResource(book_post_list.get(i));
        myViewHolder.bookNameTextView.setText(book_shortcontent_list.get(i));
        myViewHolder.tvBookKind.setText(book_kind_list.get(i));
        //并不行，得到的bitmap总是为空
       /* Bitmap bitmap= BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.img_bookshelf_everybook);
        Bitmap bitmap1=createReflectionBitmap(bitmap);
        myViewHolder.tvBookPost.setImageBitmap(bitmap1);*/
    }

    @Override
    public int getItemCount() {
        return book_name_list.size();
    }
















    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView tvBookPost;
        private BookNameTextView tvBookName;
        private TextView tvBookAuthor;
        private TextView bookNameTextView;
        private TextView tvBookKind;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookAuthor=(TextView)itemView.findViewById(R.id.id_tv_book_author);
            tvBookPost=(ImageView)itemView.findViewById(R.id.id_tv_book_post);
            tvBookName=(BookNameTextView)itemView.findViewById(R.id.id_tv_book_name);
            bookNameTextView=(TextView)itemView.findViewById(R.id.id_tv_book_shortcontent);
            tvBookKind=(TextView)itemView.findViewById(R.id.id_tv_book_kind);
        }
    }
}
