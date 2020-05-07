package com.lianggao.whut.androidebook;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.lianggao.whut.androidebook.Model.MySubject;

import java.util.LinkedList;
import java.util.List;

public class Activity_Add_Course extends Activity {
    private Spinner course_start_week;//开始周
    private Spinner course_end_week;//结束周
    private Spinner course_day;//星期几
    private Spinner course_class_start;//第几节课开始
    private Spinner course_class_last;//持续几节课

    private MySubject mySubject;
    private Button button;
    private TextView course_name;
    private TextView course_location;
    private int  day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        mySubject=new MySubject();
        course_start_week=(Spinner)findViewById(R.id.course_start_week) ;
        course_end_week=(Spinner)findViewById(R.id.course_end_week);
        course_day=(Spinner)findViewById(R.id.course_day) ;
        course_class_start=(Spinner)findViewById(R.id.course_class_start);
        course_class_last=(Spinner)findViewById(R.id.course_class_last);
        course_name=(TextView)findViewById(R.id.course_name);
        course_location=(TextView)findViewById(R.id.course_location);
        course_start_week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),course_start_week.getSelectedItem().toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        course_end_week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        course_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        course_class_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        course_class_last.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button=(Button)findViewById(R.id.btn_add_course);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course_name.getText()==""||course_location.getText()==""){
                    Toast.makeText(getApplicationContext(),"填写完整信息",Toast.LENGTH_LONG).show();
                }else{

                    mySubject.setName(course_name.getText().toString());
                    mySubject.setRoom(course_location.getText().toString());
                    mySubject.setStart(Integer.parseInt(course_class_start.getSelectedItem().toString()));
                    mySubject.setStep(Integer.parseInt(course_class_last.getSelectedItem().toString()));
                    mySubject.setDay(Integer.parseInt(course_day.getSelectedItem().toString()));
                    List<Integer>weeklist;
                    weeklist=new LinkedList<>();
                    for(int i=(Integer.parseInt(course_start_week.getSelectedItem().toString()));i<=(Integer.parseInt(course_end_week.getSelectedItem().toString()));i++){
                        weeklist.add(i);
                    }
                    mySubject.setWeekList(weeklist);
                    Intent intent=new Intent();
                    intent.putExtra("mySubject",mySubject);
                    intent.putExtra("startWeek",course_start_week.getSelectedItem().toString());
                    intent.putExtra("endWeek",course_end_week.getSelectedItem().toString());
                    setResult(1,intent);
                    Toast.makeText(getApplicationContext(),mySubject.toString(),Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });




    }


}