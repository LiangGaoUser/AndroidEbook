package com.lianggao.whut.androidebook.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Activity_BookDetail;
import com.lianggao.whut.androidebook.Activity_More_BookList;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.View.DrawableTextView;
import com.yinglan.shadowimageview.ShadowImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bigkoo.convenientbanner.utils.ScreenUtil.dip2px;

public class FragmentBookStoreList extends Fragment {
    private View rootView;
private ShadowImageView shadowImageView;
    private ShadowImageView shadowImageView2;

    private LinearLayout linearLayout_book_list;
    private GridView gridView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> data_list;
    private List<Integer>booklist_post_list;//书单封面
    private List<String>booklist_name_list;//书单名字
    private List<Integer>booklist_number_list;//书的数目
    private View view1,view2,view3,view4;
    private CardView cardView;
    private DrawableTextView moreBookList;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_bookstore_list,null);
        gridView=(GridView)rootView.findViewById(R.id.id_gv_book_list);
        data_list = new ArrayList<Map<String, Object>>();
        //新建适配器
        String [] from ={"book_post","book_name","book_progress"};
        int [] to = {R.id.book_post,R.id.book_name,R.id.book_progress};
        getData();
        simpleAdapter = new SimpleAdapter(getContext(), data_list,R.layout.part_activity_book_gridview_new, from, to);
        //配置适配器
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Activity_BookDetail.class);
                startActivity(intent);
                //Toast.makeText(getContext(),"点击gridview"+position+"个",Toast.LENGTH_SHORT).show();
            }
        });

        moreBookList=(DrawableTextView)rootView.findViewById(R.id.id_tv_all_booklist);
        Drawable drawable = getResources().getDrawable(R.drawable.icon_book_more2);
        drawable.setBounds(0, 0, 50, 50);
        moreBookList.setCompoundDrawables(null, null, drawable, null);
        moreBookList.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                Intent intent=new Intent(getActivity(), Activity_More_BookList.class);
                startActivity(intent);

            }
        });
        return rootView;
    }
    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        booklist_name_list=new LinkedList<>();
        booklist_post_list=new LinkedList<>();
        booklist_number_list=new LinkedList<>();
        for(int i=0;i<20;i++){
            booklist_name_list.add("爆裂无声"+i);
            booklist_post_list.add(R.drawable.img_bookshelf_everybook);
            booklist_number_list.add(32);
        }
        for(int i=0;i<20;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("book_post", booklist_post_list.get(i));
            map.put("book_name", booklist_name_list.get(i));
            map.put("book_progress",booklist_number_list.get(i));
            data_list.add(map);
        }

        return data_list;
    }
}
