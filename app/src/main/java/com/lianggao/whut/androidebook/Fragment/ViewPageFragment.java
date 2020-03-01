package com.lianggao.whut.androidebook.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public class ViewPageFragment extends Fragment {
    private boolean hasCreateView;
    private boolean isFragmentVisible;
    protected View rootView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Log.i("viewPageFragmnet",isVisibleToUser+"qqq");
        if(rootView==null){
            return;
        }
        hasCreateView=true;
        if(isVisibleToUser){
            onFragmentVisibleChange(true);//不可见->可见
            isFragmentVisible=true;
            return;
        }
        if(isFragmentVisible){
            onFragmentVisibleChange(false);//可见->不可见
            isFragmentVisible=false;
        }



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }
    private void initVariable(){
        hasCreateView=false;
        isFragmentVisible=false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Log.i("Fragment","onViewCreated");
        if(hasCreateView&&getUserVisibleHint()){
            onFragmentVisibleChange(true);
            isFragmentVisible=true;
        }*/
    }
    protected void onFragmentVisibleChange(boolean isVisible){
        if(isVisible){
            Log.i("viewPageFragmnet",isVisible+"@@@@true");
        }else{
            Log.i("viewPageFragmnet",isVisible+"@@@@false");
        }

        return;
    }
}
