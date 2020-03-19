package com.lianggao.whut.androidebook;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

/**
 * @author LiangGao
 * @description:用来搜索的视图，点击搜索按钮跳转到该布局
 * @data:${DATA} 16:41
 */
public class Activity_SearchView extends Activity{
    private SearchView searchView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);
        searchView=(SearchView)findViewById(R.id.id_search_view);
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                System.out.println("我收到了"+string);
            }
        });
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }
}
