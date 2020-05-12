package com.lianggao.whut.androidebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lianggao.whut.androidebook.Adapter.GeneralThoughtRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Model.MySubject;
import com.lianggao.whut.androidebook.Model.ResultThought;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Activity_General_Thought extends Activity {
    private List<String>book_post_path_list;//书的封面集合
    private List<String>book_name_list;//书的名字集合
    private List<String>book_time_list;//想法时间
    private List<String>book_thought_number_list;//想法数量


    private GeneralThoughtRecyclerViewAdapter generalThoughtRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private final int MSG_GET_THOUGHT_SUCCESS=1;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_GET_THOUGHT_SUCCESS:
                    generalThoughtRecyclerViewAdapter=new GeneralThoughtRecyclerViewAdapter(getApplicationContext(),book_post_path_list,book_name_list,book_time_list,book_thought_number_list);
                    generalThoughtRecyclerViewAdapter.setOnItemClickListener(new GeneralThoughtRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent=new Intent(Activity_General_Thought.this,Activity_Detail_Thought.class);
                            intent.putExtra("bookName",generalThoughtRecyclerViewAdapter.getBookNameList(position));
                            startActivity(intent);
                        }
                    });

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(generalThoughtRecyclerViewAdapter);

            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_thought);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        getGeneralThought();

    }


    public void getGeneralThought(){
        new Thread(){
            @Override
            public void run() {
                SharedPreferences sp=getApplicationContext().getSharedPreferences("QQFile", Context.MODE_PRIVATE);
                String user_id=sp.getString("flag","");
                book_post_path_list=new LinkedList<>();
                book_thought_number_list=new LinkedList<>();
                book_name_list=new LinkedList<>();
                book_time_list=new LinkedList<>();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("user_id",user_id));
                List<ResultThought> resultThoughtList;
                Gson gson=new Gson();
                resultThoughtList=HttpCaller.getInstance().getSyncThoughtList(ResultThought.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_User_Thought_Servlet",postParam)  ;


                for(int i=0;i<resultThoughtList.size();i++){
                    String jsonStr=gson.toJson(resultThoughtList.get(i));
                    ResultThought resultThought=new ResultThought();
                    resultThought=gson.fromJson(jsonStr,ResultThought.class);
                    book_post_path_list.add(resultThought.getBookPath());
                    book_name_list.add(resultThought.getBookName());
                    book_thought_number_list.add(resultThought.getThoughtNumber());
                    book_time_list.add(resultThought.getDataTime());
                    Log.i("用户输出",resultThought.getBookName()+" "+resultThought.getBookPath()+" "+resultThought.getDataTime()+" "+resultThought.getThoughtNumber());


                }
                Message message=new Message();
                message.what=MSG_GET_THOUGHT_SUCCESS;
                handler.sendMessage(message);

            }
        }.start();

    }


}