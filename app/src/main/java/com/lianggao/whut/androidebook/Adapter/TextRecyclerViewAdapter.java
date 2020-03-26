package com.lianggao.whut.androidebook.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.View.BookNameTextView;

import java.util.ArrayList;
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
    private int kindNumber;
    private List<Boolean>isClicks;
    public TextRecyclerViewAdapter(Context context,List<String>bookKind_list){
        inflater=LayoutInflater.from(context);
        this.bookKind_list=bookKind_list;
        isClicks=new ArrayList<>();
        for(int i=0;i<bookKind_list.size();i++){
            isClicks.add(false);
        }
    }


    @NonNull
    @Override
    public TextRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.part_activity_book_recycler_text,null);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final TextRecyclerViewAdapter.MyViewHolder myViewHolder, final int i) {
        final int j=i;
        myViewHolder.tvbookKind.setText(bookKind_list.get(i));


        if(isClicks.get(i)){
           // myViewHolder.tvbookKind.setTextColor(Color.parseColor("#00a0e9"));
            myViewHolder.itemView.setBackgroundColor(Color.parseColor("#817F95"));
        }else{
           // myViewHolder.tvbookKind.setTextColor(Color.parseColor("#ffffff"));
            myViewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }



        //判断是否在activity中设置监听，回调
        if(onItemClickListener!=null){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    for(int i = 0; i <isClicks.size();i++){
                        isClicks.set(i,false);
                    }
                    isClicks.set(i,true);
                    notifyDataSetChanged();
                    //int position=myViewHolder.getLayoutPosition();
                    //Log.i("!!!",j+" "+position);//1
                    onItemClickListener.onItemClick(myViewHolder.itemView,i);//该方法返回FragmentBookStoreKind中进行调用2后再返回继续执行

                   // Log.i("!!!!",j+" "+position);//3

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

    public int getKindNumber() {
        return kindNumber;
    }

    public void setKindNumber(int kindNumber) {
        this.kindNumber = kindNumber;
    }
}
