package com.lianggao.whut.androidebook;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.lianggao.whut.androidebook.Adapter.DetailThoughtRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Adapter.GeneralThoughtRecyclerViewAdapter;
import com.lianggao.whut.androidebook.Model.DetailResultThought;
import com.lianggao.whut.androidebook.Model.ResultThought;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Activity_Detail_Thought extends Activity {
    private List<String>selectedTextList;
    private List<String>thoughtTextList;
    private List<String>dateTimeList;


    private DetailThoughtRecyclerViewAdapter detailThoughtRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private final int MSG_GET_THOUGHT_SUCCESS=1;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_GET_THOUGHT_SUCCESS:
                    detailThoughtRecyclerViewAdapter=new DetailThoughtRecyclerViewAdapter(getApplicationContext(),selectedTextList,thoughtTextList,dateTimeList);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(detailThoughtRecyclerViewAdapter);

            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_thought);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        getDetailThought();

    }


    public void getDetailThought(){
        new Thread(){
            @Override
            public void run() {
                SharedPreferences sp=getApplicationContext().getSharedPreferences("QQFile", Context.MODE_PRIVATE);
                String user_id=sp.getString("flag","");
                selectedTextList=new LinkedList<>();
                thoughtTextList=new LinkedList<>();
                dateTimeList=new LinkedList<>();
                List<NameValuePair> postParam = new ArrayList<>();
                postParam.add(new NameValuePair("user_id",user_id));
                postParam.add(new NameValuePair("book_name",getIntent().getStringExtra("bookName")));
                List<DetailResultThought> detailResultThoughtList;
                Gson gson=new Gson();
                detailResultThoughtList=HttpCaller.getInstance().getSyncThoughtList(DetailResultThought.class,"http://192.168.1.4:8080/com.lianggao.whut/Get_User_Detail_Thought_Servlet",postParam)  ;


                for(int i=0;i<detailResultThoughtList.size();i++){
                    String jsonStr=gson.toJson(detailResultThoughtList.get(i));
                    DetailResultThought detailResultThought=new DetailResultThought();
                    detailResultThought=gson.fromJson(jsonStr,DetailResultThought.class);
                    selectedTextList.add(detailResultThought.getUserSelectedText());
                    thoughtTextList.add(detailResultThought.getUserThoughtText());
                    dateTimeList.add(detailResultThought.getDataTime());
                }
                Message message=new Message();
                message.what=MSG_GET_THOUGHT_SUCCESS;
                handler.sendMessage(message);

            }
        }.start();

    }


}