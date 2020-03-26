package com.lianggao.whut.androidebook.Fragment;

import android.annotation.SuppressLint;
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

import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.View.DrawableTextView;
import com.lianggao.whut.androidebook.View.ImageView;
import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewDrawable;
import com.yinglan.shadowimageview.ShadowImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bigkoo.convenientbanner.utils.ScreenUtil.dip2px;

public class FragmentBookStoreList extends Fragment {
    private View rootView;
    /*private ShadowImageView shadowImageView;
    private ShadowImageView shadowImageView2;*/
    private LinearLayout linearLayout_book_list;
    private GridView gridView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> data_list;
    private List<Integer>booklist_post_list;//书单封面
    private List<String>booklist_name_list;//书单名字
    private List<Integer>booklist_number_list;//书的数目
    private View view1,view2,view3,view4;
    private CardView cardView;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_bookstore_list,null);
       /* shadowImageView=(ShadowImageView)rootView.findViewById(R.id.shadow);
        shadowImageView.setImageRadius(90);
        shadowImageView.setImageResource(R.drawable.img_booklist_recommend1);
        shadowImageView.setImageShadowColor(R.color.colorGray);

        shadowImageView2=(ShadowImageView)rootView.findViewById(R.id.shadow2);
        shadowImageView2.setImageRadius(-310);
        shadowImageView2.setImageResource(R.drawable.img_booklist_recommend1);
        shadowImageView2.setImageShadowColor(R.color.colorLiteGreen);*/
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
                Toast.makeText(getContext(),"点击gridview"+position+"个",Toast.LENGTH_SHORT).show();
            }
        });
        /*view3=(android.widget.ImageView)rootView.findViewById(R.id.id_tv_book_list3);
        view4=(TextView)rootView.findViewById(R.id.id_tv_book_list4);*/
        ShadowProperty sp = new ShadowProperty()
                .setShadowColor(R.color.colorGreen)
                .setShadowDy(dip2px(getContext(), 0f))
                .setShadowRadius(dip2px(getContext(), 3))

                .setShadowSide(ShadowProperty.LEFT | ShadowProperty.RIGHT | ShadowProperty.BOTTOM|ShadowProperty.TOP);
        ShadowViewDrawable sd = new ShadowViewDrawable(sp, Color.TRANSPARENT, 0, 0);

        /*ViewCompat.setBackground(view3, sd);
        ViewCompat.setBackground(view4, sd);*/
      /*  ViewCompat.setLayerType(view4, ViewCompat.LAYER_TYPE_SOFTWARE, null);*/

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
