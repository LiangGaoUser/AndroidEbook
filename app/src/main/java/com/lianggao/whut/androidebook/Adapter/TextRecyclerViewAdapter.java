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

import java.util.List;

/**
 * @author LiangGao
 * @description:只包含文字的适配器，用在分类上面
 * @data:${DATA} 16:17
 */
public class TextRecyclerViewAdapter extends RecyclerView.Adapter<TextRecyclerViewAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<String>bookKind_list;//文字集合
    private RecyclerViewAdapter recyclerViewAdapter;
    private OnItemClickListener onItemClickListener;//接口1
    private OnItemLongClickListener onItemLongClickListener;//接口2
    public TextRecyclerViewAdapter(Context context,List<String>bookKind_list){
        inflater=LayoutInflater.from(context);
        this.bookKind_list=bookKind_list;
    }


    @NonNull
    @Override
    public TextRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.part_activity_book_recycler_text,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TextRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tvbookKind.setText(bookKind_list.get(i));
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
        return bookKind_list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvbookKind;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvbookKind=(TextView)itemView.findViewById(R.id.id_tv_book_kind_name);
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
        void onItemClick(View view,int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }

}
