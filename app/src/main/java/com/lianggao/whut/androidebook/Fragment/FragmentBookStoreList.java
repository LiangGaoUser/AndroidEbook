package com.lianggao.whut.androidebook.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lianggao.whut.androidebook.R;
import com.yinglan.shadowimageview.ShadowImageView;

public class FragmentBookStoreList extends Fragment {
    private View rootView;
    private ShadowImageView shadowImageView;
    private ShadowImageView shadowImageView2;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_bookstore_list,null);
        shadowImageView=(ShadowImageView)rootView.findViewById(R.id.shadow);
        shadowImageView.setImageRadius(90);
        shadowImageView.setImageResource(R.drawable.img_booklist_recommend1);
        shadowImageView.setImageShadowColor(R.color.colorGray);

        shadowImageView2=(ShadowImageView)rootView.findViewById(R.id.shadow2);
        shadowImageView2.setImageRadius(-310);
        shadowImageView2.setImageResource(R.drawable.img_booklist_recommend1);
        shadowImageView2.setImageShadowColor(R.color.colorLiteGreen);

        return rootView;
    }
}
