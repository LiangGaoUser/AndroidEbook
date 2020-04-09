package com.lianggao.whut.androidebook;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 20:24
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.lianggao.whut.androidebook.Model.QQLoginManager;
import com.lianggao.whut.androidebook.Utils.Util;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class Activity_QQ extends AppCompatActivity implements QQLoginManager.QQLoginListener {

    private QQLoginManager.QQLoginListener qqLoginListener;
    private QQLoginManager qqLoginManager;
    private Button button;
    private ImageView imageView;
    private TextView textView;
    private final int  MSG_LOGIN_SUCCESS=1;
    private final int MSG_LOGIN_CANCLE=0;
    private final int MSG_LOGIN_ERROR=-1;
    private static String nickname;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_LOGIN_SUCCESS:
                    Bitmap bitmap=(Bitmap)msg.obj;
                    imageView.setImageBitmap(bitmap);
                    textView.setText(nickname);
                    break;
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        button=(Button)findViewById(R.id.btn_login);
        imageView=(ImageView)findViewById(R.id.iv_touxiang);
        textView=(TextView)findViewById(R.id.tv_nickname);
        qqLoginManager=new QQLoginManager("1110404432",this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqLoginManager.launchQQLogin();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 回调
        qqLoginManager.onActivityResultData(requestCode, resultCode, data);
    }
    @Override
    public void onQQLoginSuccess(final JSONObject jsonObject) {




        new Thread(){
            @Override
            public void run() {
                Message msg=new Message();
                msg.what=MSG_LOGIN_SUCCESS;
                Bitmap bitmap= null;
                try {
                    bitmap = Util.getbitmap(jsonObject.getString("figureurl_qq_2"));
                    nickname=jsonObject.getString("nickname");
                    msg.obj=bitmap;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    @Override
    public void onQQLoginCancel() {
        System.out.println("取消登录");
    }

    @Override
    public void onQQLoginError(UiError uiError) {
        System.out.println("登录失败");
    }




}


