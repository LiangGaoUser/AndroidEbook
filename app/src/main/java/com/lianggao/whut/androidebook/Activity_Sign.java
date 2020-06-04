package com.lianggao.whut.androidebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lianggao.whut.androidebook.Action.ActionActivity;
import com.lianggao.whut.androidebook.Checkin.CheckinActivity;
import com.sch.calendar.CalendarView;
import com.sch.calendar.adapter.SampleVagueAdapter;
import com.sch.calendar.annotation.DayOfMonth;
import com.sch.calendar.annotation.Month;
import com.sch.calendar.entity.Date;
import com.sch.calendar.listener.OnDateClickedListener;
import com.sch.calendar.listener.OnMonthChangedListener;

import java.util.Locale;

public class Activity_Sign extends AppCompatActivity {


    CalendarView calendarView;
    Button btn_goto_checkin;
    Button btn_look_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        calendarView=(CalendarView)findViewById(R.id.calendar_view) ;
        initCalendarView();
        btn_goto_checkin=(Button)findViewById(R.id.btn_goto_checkin);
        btn_look_action=(Button)findViewById(R.id.btn_look_action);
        btn_goto_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCheckin();
            }
        });
        btn_look_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookAction();
            }
        });

    }


    public void gotoCheckin() {
        startActivity(new Intent(this, CheckinActivity.class));
    }

    public void lookAction() {
        startActivity(new Intent(this, ActionActivity.class));
    }

    // Initialize view for calendar
    private void initCalendarView() {
        calendarView.setCanDrag(true); // can't change month by slide
        calendarView.setScaleEnable(false); // can't auto scale calendar when month changed.
        calendarView.setShowOverflowDate(true); // hide overflow date of showing month.
        calendarView.setCanFling(true);
        calendarView.setTitleFormat("yyyy-MM", Locale.CHINA);
        // Set a listener，callback when month changed.
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(Date date) {
            }
        });
        // Set a listener，callback when one of date be clicked.
        calendarView.setOnDateClickedListener(new OnDateClickedListener() {
            @Override
            public void onDateClicked(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {

                Toast.makeText(Activity_Sign.this, String.format("%s年%s月%s日", year, month, dayOfMonth), Toast.LENGTH_SHORT).show();
            }
        });
        // using SampleVagueAdapter
        calendarView.setVagueAdapter(new SampleVagueAdapter());
    }

}
