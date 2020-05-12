package com.lianggao.whut.androidebook.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
public class GeneralThoughtRecyclerViewAdapter extends RecyclerView.Adapter<GeneralThoughtRecyclerViewAdapter.MyViewHolder> {
    private List<String>book_post_path_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_time_list;//想法时间
    private List<String>book_thought_number_list;//想法数量
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListener onItemClickListener;//接口1
    public GeneralThoughtRecyclerViewAdapter(Context context, List<String>book_post_path_list, List<String>book_name_list, List<String>book_time_list, List<String>book_thought_number_list){
        inflater=LayoutInflater.from(context);
        this.book_post_path_list=book_post_path_list;
        this.book_name_list=book_name_list;
        this.book_time_list=book_time_list;
        this.book_thought_number_list=book_thought_number_list;
        for(int i=0;i<book_name_list.size();i++){
            System.out.println("###"+book_name_list.get(i)+ " "+book_post_path_list.get(i)+" "+book_time_list.get(i)+" "+book_thought_number_list.get(i));
        }
        this.context=context;//
    }


    @NonNull
    @Override
    public GeneralThoughtRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.part_activity_general_thought,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final GeneralThoughtRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tvBookName.setText(book_name_list.get(i));
        myViewHolder.tvBookThoughtClick.setText(book_thought_number_list.get(i));
        myViewHolder.tvThoughtTime.setText(book_time_list.get(i));
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
        if(onItemClickListener!=null){
            myViewHolder.tvBookThoughtClick.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position=myViewHolder.getLayoutPosition();
                    onItemClickListener.onItemClick(myViewHolder.tvBookThoughtClick,position);


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
        private TextView tvBookName;
        private TextView tvThoughtTime;
        private TextView tvBookThoughtClick;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookPost=(ImageView)itemView.findViewById(R.id.id_tv_book_post);
            tvBookName=(TextView) itemView.findViewById(R.id.id_tv_book_name);
            tvThoughtTime=(TextView)itemView.findViewById(R.id.id_tv_thought_time);
            tvBookThoughtClick=(TextView)itemView.findViewById(R.id.id_book_thought_click);




        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public String getBookNameList(int i){
        return book_name_list.get(i).toString();
    }



}
