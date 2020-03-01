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

        /*Button button = (Button)rootView.findViewById(R.id.button_bookstore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","post登录结果");
                Toast.makeText(getContext(),"点击了按钮",Toast.LENGTH_LONG).show();

            }
        });*/
        initView();
        return rootView;
    }


   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            //Log.i("MainActivityBook","MainActivity出现时这里可以加载数据");
        }else{
            //Log.i("MainActivityBook","退出时执行操作");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }*/

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
                    case R.id.item_tab12:
                        mVp.setCurrentItem(0);
                        number=0;
                        return true;

                    case R.id.item_tab22:
                        mVp.setCurrentItem(1);
                        //initView2();
                        number=1;
                        return true;

                    case R.id.item_tab32:
                        mVp.setCurrentItem(2);
                        number=2;
                        return true;

                    case R.id.item_tab42:
                        mVp.setCurrentItem(3);
                        number=3;
                        return true;
                    case R.id.item_tab52:
                        number=4;
                        mVp.setCurrentItem(4);
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
       /* mVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });*/





    }


    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getFragmentManager());
        adapter.addFragment(new FragmentBookShelf());//0
        adapter.addFragment(new FragmentBookStoreBook());//1
        adapter.addFragment(new FragmentContinue());//2
        adapter.addFragment(new FragmentClassTable());//3
        adapter.addFragment(new FragmentSelfInfo());//4
        viewPager.setAdapter(adapter);
    }
}
