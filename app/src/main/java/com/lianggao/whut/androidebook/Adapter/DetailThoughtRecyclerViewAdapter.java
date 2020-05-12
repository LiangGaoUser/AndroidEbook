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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author LiangGao
 * @description:recyclerView的适配器，点击批量删除时进入，对应地是part_activity_book_recycler_delete.xml
 * @data:${DATA} 16:17
 */
public class DetailThoughtRecyclerViewAdapter extends RecyclerView.Adapter<DetailThoughtRecyclerViewAdapter.MyViewHolder> {
    private List<String>selectedTextList;//书的封面集合
    private List<String>thoughtTextList;//书的名字集合
    private List<String>dateTimeList;//想法时间
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListener onItemClickListener;//接口1
    public DetailThoughtRecyclerViewAdapter(Context context, List<String>selectedTextList, List<String>thoughtTextList, List<String>dateTimeList){
        inflater=LayoutInflater.from(context);
        this.selectedTextList=selectedTextList;
        this.thoughtTextList=thoughtTextList;
        this.dateTimeList=dateTimeList;
        this.context=context;//
    }


    @NonNull
    @Override
    public DetailThoughtRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.part_activity_detail_thought,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailThoughtRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tvSelectedText.setText(selectedTextList.get(i));
        myViewHolder.tvThoughtText.setText(thoughtTextList.get(i));
        myViewHolder.tvDateText.setText(dateTimeList.get(i));



    }

    @Override
    public int getItemCount() {
        return dateTimeList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvSelectedText;
        private TextView tvThoughtText;
        private TextView tvDateText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSelectedText=(TextView) itemView.findViewById(R.id.id_tv_selected_text);
            tvThoughtText=(TextView) itemView.findViewById(R.id.id_tv_thought_text);
            tvDateText=(TextView)itemView.findViewById(R.id.id_tv_datatime);




        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }



}
