package com.lianggao.whut.androidebook.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.View.BookNameTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * @author LiangGao
 * @description:recyclerView的适配器，点击批量删除时进入，对应地是part_activity_book_recycler_delete.xml
 * @data:${DATA} 16:17
 */
public class DeleteBookShelfRecyclerViewAdapter extends RecyclerView.Adapter<DeleteBookShelfRecyclerViewAdapter.MyViewHolder> {
    private List<String>book_post_path_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_author_list;//书的作者集合
    private List<String>book_shortcontent_list;//书的简介集合
    private List<String>book_check_list;//书的种类集合
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListener onItemClickListener;//接口1
    private OnItemLongClickListener onItemLongClickListener;//接口2
    public DeleteBookShelfRecyclerViewAdapter(Context context, List<String>book_post_path_list, List<String>book_name_list, List<String>book_author_list, List<String>book_check_list, List<String>book_shortcontent_list){
        inflater=LayoutInflater.from(context);
        this.book_post_path_list=book_post_path_list;
        this.book_name_list=book_name_list;
        this.book_author_list=book_author_list;
        this.book_shortcontent_list=book_shortcontent_list;
        this.book_check_list=book_check_list;
        this.context=context;//
    }


    @NonNull
    @Override
    public DeleteBookShelfRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.part_activity_book_recycler_delete,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeleteBookShelfRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
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
                    .load(new File(book_post_path_list.get(i)))
                    .placeholder(R.drawable.icon_arrow_return)//占位符
                    .error(R.drawable.img_bookshelf_everybook)//链接失效是加载的图片
                    .into(myViewHolder.tvBookPost);
        }
        if(onItemClickListener!=null){
            myViewHolder.radioButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position=myViewHolder.getLayoutPosition();
                    onItemClickListener.onItemClick(myViewHolder.radioButton,position);
                    if(book_check_list.get(position).equals("true")){
                        myViewHolder.radioButton.setChecked(true);
                    }else{
                        myViewHolder.radioButton.setChecked(false);
                    }

                }
            });

        }

        myViewHolder.bookNameTextView.setText(book_shortcontent_list.get(i));
        if(book_check_list.get(i).equals("true")){
            myViewHolder.radioButton.setChecked(true);
        }else{
            myViewHolder.radioButton.setChecked(false);
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
        private RadioButton radioButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookAuthor=(TextView)itemView.findViewById(R.id.id_tv_book_author);
            tvBookPost=(ImageView)itemView.findViewById(R.id.id_tv_book_post);
            tvBookName=(BookNameTextView)itemView.findViewById(R.id.id_tv_book_name);
            bookNameTextView=(TextView)itemView.findViewById(R.id.id_tv_book_shortcontent);
            radioButton=(RadioButton) itemView.findViewById(R.id.id_tv_book_check);
        }
    }
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

    public List<String> getCheckList(){
        return book_check_list;
    }

    public void setCheckListFalse(int i){
        book_check_list.set(i,"false");

    }
    public void setCheckListTrue(int i){
        book_check_list.set(i,"true");
    }
    public void setCheckListAllFalse(){
        for(int i=0;i<book_check_list.size();i++){
            book_check_list.set(i,"false");
        }
    }
    public void setCheckListAllTrue(){
        for(int i=0;i<book_check_list.size();i++){
            book_check_list.set(i,"true");
        }
    }
}
