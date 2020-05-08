package com.lianggao.whut.androidebook;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;


import com.lianggao.whut.androidebook.Adapter.BottomAdapter;
import com.lianggao.whut.androidebook.Fragment.FragmentBookShelf;
import com.lianggao.whut.androidebook.Fragment.FragmentBookStore;
import com.lianggao.whut.androidebook.Fragment.FragmentClassTable;
import com.lianggao.whut.androidebook.Fragment.FragmentContinue;
import com.lianggao.whut.androidebook.Fragment.FragmentSelfInfo;

public class MainActivity extends FragmentActivity {
    private BottomNavigationView mBv;
    private ViewPager mVp;;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity","onCreate");
        initView();

    }



    private void initView() {
        Log.i("MainActivity","initView");
        mBv = (BottomNavigationView) findViewById(R.id.bv);
        mVp = (ViewPager) findViewById(R.id.vp);
        mVp.setOffscreenPageLimit(1);
        //BottomNavigationViewHelper.disableShiftMode(mBv);
        mBv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_tab1:
                        mVp.setCurrentItem(0);
                        return true;

                    case R.id.item_tab2:
                        mVp.setCurrentItem(1);
                        //initView2();
                        return true;

                    case R.id.item_tab3:
                        mVp.setCurrentItem(2);
                       // Log.i("MainActivityBook",mVp.getCurrentItem(2));
                        return true;

                    case R.id.item_tab4:
                        mVp.setCurrentItem(3);
                        return true;
                    case R.id.item_tab5:
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
                /*if(position==1||position==2||position==3){
                    mBv.getMenu().getItem(1).setChecked(true);
                } else if(position==4||position==5){
                    mBv.getMenu().getItem(position-2).setChecked(true);
                } else{
                    if(position>6){

                    }
                    mBv.getMenu().getItem(position).setChecked(true);
                }*/

                mBv.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //禁止ViewPager滑动
        //        mVp.setOnTouchListener(new View.OnTouchListener() {
        //                    @Override
        //                    public boolean onTouch(View v, MotionEvent event) {
        //                        return true;
        //                    }
        //                });
    }





 /*   private void initView2() {

        final BottomNavigationView mBv2;
        final ViewPager mVp2;

        final View textEntryView = View.inflate(getApplicationContext(), R.layout.fragment_bookstore,null);

    }
*/









    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentBookShelf());//0
        adapter.addFragment(new FragmentBookStore());//1
        adapter.addFragment(new FragmentContinue());//2
        adapter.addFragment(new FragmentClassTable());//3
        adapter.addFragment(new FragmentSelfInfo());//4
        Log.i("MainActivity",adapter.getCount()+"ddd");
        viewPager.setAdapter(adapter);
    }
}

