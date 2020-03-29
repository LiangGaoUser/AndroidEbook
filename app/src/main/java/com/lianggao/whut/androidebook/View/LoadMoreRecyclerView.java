package com.lianggao.whut.androidebook.View;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 21:48
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.lianggao.whut.androidebook.Adapter.LoadMoreAdapter;

public class LoadMoreRecyclerView extends RecyclerView {

    private LinearLayoutManager mLinearLayoutManager;
    private LoadMoreAdapter mAdapter;
    private GridLayoutManager gridLayoutManager;

    public LoadMoreRecyclerView(Context context) {
        this(context,null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        int endCompletelyPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        if (endCompletelyPosition == mAdapter.getItemCount()-1){
            this.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.loadMore();
                }
            });

        }
    }

    public void setManager(){
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(mLinearLayoutManager);


        //gridLayoutManager=new GridLayoutManager(getContext(),3);
       // setLayoutManager(gridLayoutManager);
    }
    public void setManager2(){



        gridLayoutManager=new GridLayoutManager(getContext(),3);
        setLayoutManager(gridLayoutManager);
    }
    public LinearLayoutManager getLayoutManager() {
        return mLinearLayoutManager;
    }

    public void setLoadMoreAdapter(LoadMoreAdapter mAdapter) {
        this.mAdapter = mAdapter;
        setAdapter(mAdapter);
    }
}
