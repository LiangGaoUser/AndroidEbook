package com.lianggao.whut.androidebook.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.lianggao.whut.androidebook.R;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 22:19
 */
public class MyAlertDialogFragment extends DialogFragment {
    private DialogInterface.OnClickListener onClickListener;
    public static MyAlertDialogFragment newInstance(){
        return new MyAlertDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.icon_notice)
                .setTitle("确定删除选择的书架内容?");
        if(onClickListener!=null){
            builder.setPositiveButton("确定",onClickListener);
            builder.setNegativeButton("取消",onClickListener);
        }
        return builder.create();
    }
    public void setOnClickListener(DialogInterface.OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }

}
