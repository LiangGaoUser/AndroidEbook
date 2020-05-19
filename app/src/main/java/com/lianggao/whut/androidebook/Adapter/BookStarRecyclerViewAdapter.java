package com.lianggao.whut.androidebook.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.View.BookNameTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * @author LiangGao
 * @description:Activity_Star的适配器,用来展示收藏的图书
 * @data:${DATA} 16:17
 */
public class BookStarRecyclerViewAdapter extends RecyclerView.Adapter<BookStarRecyclerViewAdapter.MyViewHolder> {
    private List<String>book_post_path_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_main_kind_list;
    private List<String>book_detail_kind_list;


    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;//接口1
    private OnItemLongClickListener onItemLongClickListener;//接口2
    private Context context;
    public BookStarRecyclerViewAdapter(Context context, List<String>book_post_path_list, List<String>book_name_list, List<String>book_author_list, List<String>book_main_kind_list, List<String>book_detail_kind_list, List<String>book_shortcontent_list){
        inflater=LayoutInflater.from(context);
        this.book_post_path_list=book_post_path_list;
        this.book_name_list=book_name_list;
        this.book_author_list=book_author_list;
        this.book_shortcontent_list=book_shortcontent_list;
        this.book_main_kind_list=book_main_kind_list;
        this.book_detail_kind_list=book_detail_kind_list;
        this.context=context;//
    }


    @NonNull
    @Override
    public BookStarRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.part_activity_book_recycler,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookStarRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tvBookAuthor.setText(book_author_list.get(i));
        myViewHolder.tvBookName.setText(book_name_list.get(i));
        if(book_post_path_list.get(i)==null){
            Picasso
                    .with(context)
                    .load(R.drawable.img_booklist_recommend1)
                    .into(myViewHolder.tvBookPost);

        }else{
            Picasso
                    .with(context)
                    .load(book_post_path_list.get(i))
                    .placeholder(R.drawable.icon_arrow_return)//占位符
                    .error(R.drawable.img_bookshelf_everybook)//链接失效是加载的图片
                    .into(myViewHolder.tvBookPost);
        }


        myViewHolder.bookNameTextView.setText(book_shortcontent_list.get(i));
        myViewHolder.tvBookKind.setText(book_detail_kind_list.get(i));
        myViewHolder.tvMainBookKind.setText(book_main_kind_list.get(i));

       //判断是否在activity中设置监听，回调
       if(onItemClickListener!=null){
           myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                   int position=myViewHolder.getLayoutPosition();
                   onItemClickListener.onItemClick(myViewHolder.itemView,position);
               }
           });

       }
       if(onItemLongClickListener!=null){
           myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
               @Override
               public boolean onLongClick(View v) {
                   int position=myViewHolder.getLayoutPosition();
                   onItemLongClickListener.onItemLongClick(myViewHolder.itemView,position);
                   return true;
               }
           });

       }
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
        private TextView tvMainBookKind;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookAuthor=(TextView)itemView.findViewById(R.id.id_tv_book_author);
            tvBookPost=(ImageView)itemView.findViewById(R.id.id_tv_book_post);
            tvBookName=(BookNameTextView)itemView.findViewById(R.id.id_tv_book_name);
            bookNameTextView=(TextView)itemView.findViewById(R.id.id_tv_book_shortcontent);
            tvBookKind=(TextView)itemView.findViewById(R.id.id_tv_book_kind);
            tvMainBookKind=(TextView)itemView.findViewById(R.id.id_tv_book_main_kind);
        }
    }
    /**
     * @description:    recycleview需要自己设置监听器，先设置两个接口
     * @param:
     * @return:
     * @author:         梁高
     * @time:           2020/3/20
     */
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }

}
