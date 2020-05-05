package com.lianggao.whut.androidebook;


import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Adapter.BookShelfHistoryRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Adapter.BottomAdapter;
import com.lianggao.whut.androidebook.Fragment.FragmentBookStoreBook;
import com.lianggao.whut.androidebook.Fragment.FragmentBookStoreKind;
import com.lianggao.whut.androidebook.Fragment.FragmentBookStoreList;
import com.lianggao.whut.androidebook.Fragment.FragmentComputer;
import com.lianggao.whut.androidebook.Fragment.FragmentEcnomy;
import com.lianggao.whut.androidebook.Fragment.FragmentEncourage;
import com.lianggao.whut.androidebook.Fragment.FragmentLiterature;
import com.lianggao.whut.androidebook.Fragment.FragmentLocal;
import com.lianggao.whut.androidebook.Fragment.FragmentNovel;
import com.lianggao.whut.androidebook.Fragment.FragmentScience;
import com.lianggao.whut.androidebook.Fragment.MyAlertDialogFragment;
import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Utils.Util;
import com.lianggao.whut.androidebook.Utils.bookShelfHistoryTableManger;

import java.util.LinkedList;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class Activity_BookShelf_Kind extends FragmentActivity implements MaterialTabListener {

    //xml页面元素
    private TextView returnTextView;


    private MaterialTabHost tabHost;
    private BottomAdapter adapter;
    private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf_kind);


        //tabhost
        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        tabHost.setPrimaryColor(Color.WHITE);
        tabHost.setTextColor(Color.BLACK);
        pager=(ViewPager)findViewById(R.id.vp2) ;
        adapter = new BottomAdapter(getSupportFragmentManager());
        setupViewPager(pager);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }

        });
        tabHost.addTab(
                tabHost.newTab()
                        .setText("本地")
                        .setTabListener(this)
        );
        tabHost.addTab(
                tabHost.newTab()
                        .setText("小说")
                        .setTabListener(this)
        );
        tabHost.addTab(
                tabHost.newTab()
                        .setText("文学")
                        .setTabListener(this)
        );
        tabHost.addTab(
                tabHost.newTab()
                        .setText("社科")
                        .setTabListener(this)
        );
        tabHost.addTab(
                tabHost.newTab()
                        .setText("计算机")
                        .setTabListener(this)
        );
        tabHost.addTab(
                tabHost.newTab()
                        .setText("励志")
                        .setTabListener(this)
        );
        tabHost.addTab(
                tabHost.newTab()
                        .setText("经济")
                        .setTabListener(this)
        );

        //返回
        returnTextView=(TextView)findViewById(R.id.bookshelf_kind_tv);
        Drawable back= getResources().getDrawable(R.drawable.icon_book_return2);
        back.setBounds(0, 0, 50, 50);
        returnTextView.setCompoundDrawables(back, null, null, null);
        returnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentLocal());
        adapter.addFragment(new FragmentNovel());
        adapter.addFragment(new FragmentLiterature());
        adapter.addFragment(new FragmentScience());
        adapter.addFragment(new FragmentComputer());
        adapter.addFragment(new FragmentEncourage());
        adapter.addFragment(new FragmentEcnomy());

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        Toast.makeText(this,"选中了"+tab.getPosition(),Toast.LENGTH_LONG).show();
        pager.setCurrentItem(tab.getPosition());
    }
    @Override
    public void onTabReselected(MaterialTab tab) {
    }
    @Override
    public void onTabUnselected(MaterialTab tab) {
    }
}
