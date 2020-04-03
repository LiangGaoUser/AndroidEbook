package com.lianggao.whut.androidebook.View;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.lianggao.whut.androidebook.R;


/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 9:04
 */
public class LoadMoreGridView extends RelativeLayout {

    public ScrollView baseSv;//最外面的ScrollView
    public LinearLayout baseLl, footerLl;//次外面的linearlayout和页脚的linearlayout
    private ScrollWithGridView dataGv;//GridView
    private BaseAdapter dataAdapter;
    private boolean isLoadMore=true;
    private boolean isEnable = true;


    public LoadMoreGridView(Context context) {
        super(context);
        init();
    }

    public LoadMoreGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.part_auto_load_grid_view_layout, this);
        baseSv = view.findViewById(R.id.auto_load_gv_base_sv);
        baseLl = view.findViewById(R.id.auto_load_gv_base_ll);
        dataGv = view.findViewById(R.id.auto_load_gv);
        footerLl = view.findViewById(R.id.auto_load_gv_footer_ll);
        /*ImageView animatorIv = view.findViewById(R.id.auto_load_gv_footer_image_iv);
        AnimationDrawable animationDrawable = (AnimationDrawable) animatorIv.getDrawable();
        animationDrawable.start();*/

        dataGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onLoadGridListener != null)
                    onLoadGridListener.onItemClick(position);
                    if(position==6){
                        footerLl.setVisibility(View.VISIBLE);
                    }
                    if(position==7){
                        footerLl.setVisibility(View.GONE);
                    }
            }
        });

        baseSv.setOnTouchListener(new OnTouchListener() {
            private int lastY = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("点击了");
                    lastY = baseSv.getScrollY();
                    if (lastY == (baseLl.getHeight() - baseSv.getHeight()) && isLoadMore && isEnable) {
                        footerLl.setVisibility(View.VISIBLE);

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                //ScrollView滚动到底部\
                                System.out.println("7");
                                if (onLoadGridListener != null){
                                    System.out.println("加载数据");
                                    System.out.println("2");


                                    onLoadGridListener.onLoadMore();
                                    System.out.println("3");

                                }
                                  baseSv.fullScroll(ScrollView.FOCUS_DOWN);
                                System.out.println("8");
                            }
                        });


                    }
                }
                return false;
            }
        });
    }



    /**
     * 设置适配器
     */
    public void setAdapter(BaseAdapter baseAdapter) {
        dataAdapter = baseAdapter;
        dataGv.setAdapter(dataAdapter);
    }
    public BaseAdapter getAdapter(){
        return dataAdapter;
    }
    /**
     * 加载数据完成调用
     */
    public void loadDataComplete() {
        isLoadMore = false;
        footerLl.setVisibility(View.GONE);
        dataAdapter.notifyDataSetChanged();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                //ScrollView滚动到底部
                baseSv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    private OnLoadGridListener onLoadGridListener;

    public void setOnLoadGridListener(OnLoadGridListener onLoadGridListener) {
        this.onLoadGridListener = onLoadGridListener;
    }

    public interface OnLoadGridListener {

        void onItemClick(int position);

        void onLoadMore();
    }


}

