package com.lianggao.whut.androidebook;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Fragment.MyAlertDialogFragment;
import com.lianggao.whut.androidebook.Model.Result;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.Net.RequestDataCallback;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Activity_My_Thought extends Activity {
    private TextView textView;
    private Button button;
    private TextView bookNameTextView;
    private TextView returnTextView;
    private EditText editText;





    private final int MSG_UPLOAD_SUCCESS=1;
    private final int MSG_UPLOAD_FAILURE=2;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPLOAD_SUCCESS:

                    new AlertDialog.Builder(Activity_My_Thought.this)
                            .setTitle("操作结果")
                            .setMessage("上传成功")
                            .setPositiveButton("确定返回", onClickListener)
                            .show();
                    break;
                case MSG_UPLOAD_FAILURE:
                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("操作结果")
                            .setMessage("上传失败")
                            .setPositiveButton("确定", null)
                            .show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_thought);
        bookNameTextView=(TextView)findViewById(R.id.bookNameTextView) ;
        bookNameTextView.setText(getIntent().getStringExtra("FileName"));
        textView=(TextView)findViewById(R.id.copyTextView);
        button=(Button)findViewById(R.id.sureBtn);
        textView.setText(getIntent().getStringExtra("copytext"));
        editText=(EditText)findViewById(R.id.editText);


        returnTextView=(TextView)findViewById(R.id.returnTextView);
        Drawable back= getResources().getDrawable(R.drawable.icon_book_return2);
        back.setBounds(0, 0, 50, 50);
        returnTextView.setCompoundDrawables(back, null, null, null);;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"开始上传想法",Toast.LENGTH_SHORT).show();
                upLoadMyThought();
            }
        });


    }

    public void upLoadMyThought(){
        new Thread(){
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance(); // get current instance of the calendar
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String dataTime=formatter.format(calendar.getTime());

                SharedPreferences sp=getApplicationContext().getSharedPreferences("QQFile", Context.MODE_PRIVATE);
                String user_id=sp.getString("flag","");
                System.out.println("###################"+user_id);

                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("user_id", user_id));
                postParam.add(new NameValuePair("book_name", getIntent().getStringExtra("FileName")));
                postParam.add(new NameValuePair("user_selected_text", getIntent().getStringExtra("copytext")));
                postParam.add(new NameValuePair("user_thought_text", editText.getText().toString()));
                postParam.add(new NameValuePair("user_upload_time", dataTime));

                Result result=HttpCaller.getInstance().postSyncResult(Result.class, "http://192.168.1.4:8080/com.lianggao.whut/Post_User_Thought_Servlet",postParam);
                Message message=new Message();
                if(result.getResult().equals("上传成功")){
                   message.what=MSG_UPLOAD_SUCCESS;
                   handler.sendMessage(message);
                }else{
                    message.what=MSG_UPLOAD_FAILURE;
                    handler.sendMessage(message);
                }
            }
        }.start();



    }
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(Activity_My_Thought.this);
        normalDialog.setIcon(R.drawable.icon_notice);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    private DialogInterface.OnClickListener onClickListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which){
                case DialogInterface.BUTTON_POSITIVE:
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
}
