package com.lianggao.whut.androidebook.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lianggao.whut.androidebook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiangGao
 * @description:只包含文字的适配器，用在分类上面
 * @data:${DATA} 16:17
 */
public class ClassBookRecyclerViewAdapter extends RecyclerView.Adapter<ClassBookRecyclerViewAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<String>classNameList;
    private List<String>fileNameList;
    private List<String>filePathList;

    private RecyclerViewAdapter recyclerViewAdapter;
    private OnItemClickListener onItemClickListener;//接口1
    public ClassBookRecyclerViewAdapter(Context context, List<String>classNameList,List<String>fileNameList,List<String>filePathList){
        inflater=LayoutInflater.from(context);
        this.classNameList=classNameList;
        this.fileNameList=fileNameList;
        this.filePathList=filePathList;

    }


    @NonNull
    @Override
    public ClassBookRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.part_activity_book_recycler_class_book,null);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ClassBookRecyclerViewAdapter.MyViewHolder myViewHolder, final int i) {
        final int j=i;
        myViewHolder.class_name.setText(classNameList.get(i));
        myViewHolder.file_name.setText(fileNameList.get(i));
        myViewHolder.file_path.setText(filePathList.get(i));
        if(onItemClickListener!=null){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(myViewHolder.handle_textView,i);//该方法返回FragmentBookStoreKind中进行调用2后再返回继续执行
                }
            });
        }


    }
    @Override
    public int getItemCount() {
        return classNameList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView class_name;
        private TextView file_name;
        private TextView file_path;
        private TextView handle_textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            class_name=(TextView)itemView.findViewById(R.id.class_name);
            file_name=(TextView)itemView.findViewById(R.id.file_name);
            file_path=(TextView)itemView.findViewById(R.id.file_path);
            handle_textView=(TextView)itemView.findViewById(R.id.handle_textView);





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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }



}
