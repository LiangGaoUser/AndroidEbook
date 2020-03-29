package com.lianggao.whut.androidebook.ViewHolder;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 21:51
 */

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CommonRcViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views = new SparseArray<>();
    private View view;//存储itemView
    public CommonRcViewHolder(View itemView ) {
        super(itemView);
        view = itemView;
    }

    public <T extends View> T getView(int viewId){//获得itemView中的控件，并把控件存储在数组中
        View v = views.get(viewId);

        if (v==null){
            v = view.findViewById(viewId);
            views.put(viewId, v);
        }
        return (T)v;
    }

    public <T extends View> T getViewWithLayoutParams(int viewId,ViewGroup.LayoutParams lp){
        View v = views.get(viewId);

        if (v==null){
            v = view.findViewById(viewId);
            v.setLayoutParams(lp);
            views.put(viewId,v);
        }
        return (T)v;
    }

    public CommonRcViewHolder setText(int viewId,String text){//将viewId的控件，设置文本
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
}
