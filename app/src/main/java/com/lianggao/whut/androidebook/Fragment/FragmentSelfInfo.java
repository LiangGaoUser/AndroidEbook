package com.lianggao.whut.androidebook.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lianggao.whut.androidebook.R;

public class FragmentSelfInfo extends ViewPageFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_selfinfo,null);
        }
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
    public void onStart() {
        super.onStart();
        //mVp.setCurrentItem(number);
        Log.i("FragmentSelfInfo","onStart");
        // Toast.makeText(getContext(),"点击了按钮"+number,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("FragmentSelfInfo","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("FragmentSelfInfo","onRause");
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(isVisible){
            Log.i("FragmentSelfInfo","aaa");
        }else{
            Log.i("FragmentSelfInfo","bbb");
        }
    }
}
