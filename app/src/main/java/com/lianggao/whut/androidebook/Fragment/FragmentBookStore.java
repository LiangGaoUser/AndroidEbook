/*
package com.lianggao.whut.androidebook.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Adapter.BottomAdapter;
import com.lianggao.whut.androidebook.Adapter.NoScrollViewPager;
import com.lianggao.whut.androidebook.R;

public class FragmentBookStore extends ViewPageFragment {

    private BottomNavigationView mBv;
    private NoScrollViewPager mVp;
    private static int number=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }
        Log.i("MainActivityBook","onCreateView");
        rootView=inflater.inflate(R.layout.fragment_bookstore,container,false);

        */
/*Button button = (Button)rootView.findViewById(R.id.button_bookstore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","post登录结果");
                Toast.makeText(getContext(),"点击了按钮",Toast.LENGTH_LONG).show();

            }
        });*//*

        initView();
        return rootView;
    }


   */
/* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            //Log.i("MainActivityBook","MainActivity出现时这里可以加载数据");
        }else{
            //Log.i("MainActivityBook","退出时执行操作");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }*//*


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(isVisible){
            Log.i("FragmentBookStore","aaa");
        }else{
            Log.i("FragmentBookStore","bbb");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mVp.setCurrentItem(number);
        Log.i("FragmentBookStore","onStart");
       // Toast.makeText(getContext(),"点击了按钮"+number,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("FragmentBookStore","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("FragmentBookStore","onRause");
    }

    private void initView() {
        Log.i("MainActivityBook","initView");
        mBv = (BottomNavigationView) rootView.findViewById(R.id.bv2);
        mVp = (NoScrollViewPager) rootView.findViewById(R.id.vp2);
        //BottomNavigationViewHelper.disableShiftMode(mBv);
        mBv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_tab_book:
                        mVp.setCurrentItem(0);
                        number=0;
                        return true;

                    case R.id.item_tab_kind:
                        mVp.setCurrentItem(1);
                        //initView2();
                        number=1;
                        return true;

                    case R.id.item_tab_list:
                        mVp.setCurrentItem(2);
                        number=2;
                        return true;
                }
                return false;
            }
        });

        //数据填充
        setupViewPager(mVp);
        //ViewPager监听
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mBv.getMenu().getItem(position).setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //禁止ViewPager滑动
       */
/* mVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });*//*






    }


    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getFragmentManager());
        */
/*adapter.addFragment(new FragmentBookShelf());//0
        adapter.addFragment(new FragmentBookStoreBook());//1
        adapter.addFragment(new FragmentContinue());//2
        adapter.addFragment(new FragmentClassTable());//3
        adapter.addFragment(new FragmentSelfInfo());//4*//*

        adapter.addFragment(new FragmentBookStoreBook());
        adapter.addFragment(new FragmentBookStoreBook());
        adapter.addFragment(new FragmentBookStoreBook());
        viewPager.setAdapter(adapter);
    }
}
*/
package com.lianggao.whut.androidebook.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Activity_SearchView;
import com.lianggao.whut.androidebook.Adapter.BottomAdapter;
import com.lianggao.whut.androidebook.Adapter.NoScrollViewPager;
import com.lianggao.whut.androidebook.R;

import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class FragmentBookStore extends ViewPageFragment implements MaterialTabListener {

    private BottomNavigationView mBv;
    private ViewPager pager;
    private static int number = 0;

    private MaterialTabHost tabHost;
    private BottomAdapter adapter;
    private TextView id_tv_search;//搜索图标
    private CardView cardview_book_search;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }
        Log.i("MainActivityBook", "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_bookstore, container, false);
        initView();
        return rootView;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        Log.i("MainActivityBook", "initView");

        id_tv_search=(TextView)rootView.findViewById(R.id.id_tv_search);
        Typeface iconfont = Typeface.createFromAsset(this.getActivity().getAssets(), "iconfont/iconfont.ttf");
        id_tv_search.setTypeface(iconfont);
        cardview_book_search=(CardView)rootView.findViewById(R.id.cardview_book_search);
        cardview_book_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_SearchView.class);
                startActivity(intent);
            }
        });



        pager = (ViewPager) rootView.findViewById(R.id.vp2);
        tabHost = (MaterialTabHost) rootView.findViewById(R.id.bv2);

        tabHost.setPrimaryColor(Color.WHITE);
        tabHost.setTextColor(Color.BLACK);
        adapter = new BottomAdapter(getFragmentManager());
        setupViewPager(pager);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }

        });
        tabHost.addTab(
                tabHost.newTab()
                        .setText("图书")
                        .setTabListener(this)
        );
        tabHost.addTab(
                tabHost.newTab()
                        .setText("分类")
                        .setTabListener(this)
        );
        tabHost.addTab(
                tabHost.newTab()
                        .setText("书单")
                        .setTabListener(this)
        );

       /* for (int i = 0; i <3; i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText("年后")
                            .setTabListener(this)
            );
        }*/

    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getFragmentManager());
        adapter.addFragment(new FragmentBookStoreBook());
        adapter.addFragment(new FragmentBookStoreKind());
        adapter.addFragment(new FragmentBookStoreList());
        viewPager.setAdapter(adapter);
    }
}
