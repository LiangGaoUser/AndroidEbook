package com.lianggao.whut.androidebook.View;

import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.lianggao.whut.androidebook.R;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 21:15
 */

public class LocalImageHolderView extends Holder<Integer> {

    private ImageView mImageView;

    public LocalImageHolderView(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        mImageView = itemView.findViewById(R.id.item_image);
    }

    @Override
    public void updateUI(Integer data) {
        mImageView.setImageResource(data);
    }

}