package com.lianggao.whut.androidebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lianggao.whut.androidebook.Fragment.ViewPageFragment;
import com.lianggao.whut.androidebook.Model.MySubject;
import com.lianggao.whut.androidebook.Utils.ClassTableManger;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

public class FragmentClassTable2 extends ViewPageFragment implements View.OnClickListener {
    public static final String AD_URL="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545749786636&di=fd5483be8b08b2e1f0485e772dadace4&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F5f9fae85770bb289f790e08d778516d128f0492a114a8-TNyOSi_fw658";
    //控件
    TimetableView mTimetableView;
    WeekView mWeekView;

    LinearLayout layout;
    TextView titleTextView;
    List<MySubject> mySubjects;

    //记录切换的周次，不一定是当前周
    int target = -1;
    AlertDialog alertDialog;

    //自己添加的界面
    private Button button;

    private Schedule addSchedule;
    private ClassTableManger classTableManger;
    private List<MySubject>  mySubjectList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.activity_course_table,null);



            addSchedule=new Schedule();
            //添加新课程
            button=(Button)rootView.findViewById(R.id.add_course);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"点击了",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(),Activity_Add_Course.class);
                    startActivityForResult(intent,1);



                }
            });
            titleTextView = rootView.findViewById(R.id.id_title);
            layout = rootView.findViewById(R.id.id_layout);
            layout.setOnClickListener(this);
            initTimetableView();

            requestLocalData();
        }
        return rootView;
    }


    /**
     * 2秒后刷新界面，模拟网络请求
     */
    private void requestLocalData() {
        alertDialog=new AlertDialog.Builder(getContext())
                .setMessage("模拟请求网络中..")
                .setTitle("Tips").create();
        alertDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    classTableManger=new ClassTableManger(getContext());
                    classTableManger.createDb();
                    classTableManger.deleteTableContent();


                    MySubject adSubject=new MySubject();
                    adSubject.setName("广告");
                    adSubject.setStart(1);
                    adSubject.setStep(4);
                    adSubject.setDay(1);
                    adSubject.setRoom("航海");
                    List<Integer> list= new ArrayList<>();
                    for(int i=1;i<=20;i++) list.add(i);
                    adSubject.setWeekList(list);
                    classTableManger.addClass(adSubject);
                    mySubjectList=classTableManger.findAllClass();

                    for(int i=0;i<mySubjectList.size();i++){
                        MySubject subject2=mySubjectList.get(i);
                        System.out.println("####**" +subject2.getName() + subject2.getWeekList().get(0) + subject2.getWeekList().get(subject2.getWeekList().size()-1) +subject2.getStart() + subject2.getStep()+subject2.getDay());

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x123);
            }
        }).start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(alertDialog!=null) alertDialog.hide();

            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            mySubjects=new LinkedList<>();
            //增加广告
            MySubject adSubject=new MySubject();
            adSubject.setName("【广告】");
            adSubject.setStart(1);
            adSubject.setStep(2);
            adSubject.setDay(7);
            List<Integer> list= new ArrayList<>();
            for(int i=1;i<=20;i++) list.add(i);
            adSubject.setWeekList(list);
            adSubject.setUrl(AD_URL);
            mySubjectList.add(adSubject);

            mWeekView.source(mySubjectList).showView();
            mTimetableView.source(mySubjectList).showView();
        }
    };


    private void requestData2() {
        alertDialog=new AlertDialog.Builder(getContext())
                .setMessage("模拟请求网络中..")
                .setTitle("Tips").create();
        alertDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler2.sendEmptyMessage(0x123);
            }
        }).start();
    }

    Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(alertDialog!=null) alertDialog.hide();
            List<Schedule>mySubjectList1;
            mySubjectList1=mWeekView.dataSource();
            mySubjectList1.add(addSchedule);
            mWeekView.data(mySubjectList1).showView();
            mTimetableView.data(mySubjectList1).showView();//最后还要存储到课程表中去
        }
    };
    /**
     * 初始化课程控件
     */
    private void initTimetableView() {
        //获取控件
        mWeekView = rootView.findViewById(R.id.id_weekview);
        mTimetableView = rootView.findViewById(R.id.id_timetableView);

        //设置周次选择属性
        mWeekView.curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mTimetableView.curWeek();
                        //更新切换后的日期，从当前周cur->切换的周week
                        mTimetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        mTimetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        mTimetableView.curWeek(1)
                .curTerm("大三下学期")
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(View v, int day, int start) {
                        Toast.makeText(getContext(),
                                "长按:周" + day  + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        titleTextView.setText("第" + curWeek + "周");
                    }
                })
                .callback(new OnItemBuildAdapter(){
                    @Override
                    public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                        super.onItemUpdate(layout, textView, countTextView, schedule, gd);
                        if(schedule.getName().equals("【广告】")){
                            layout.removeAllViews();
                            ImageView imageView=new ImageView(getContext());
                            imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                            layout.addView(imageView);
                            String url= (String) schedule.getExtras().get(MySubject.EXTRAS_AD_URL);

                            Glide.with(getContext())
                                    .load(url)
                                    .into(imageView);

                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getContext(),"进入广告网页链接",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                })
                .showView();
    }

    /**
     * 更新一下，防止因程序在后台时间过长（超过一天）而导致的日期或高亮不准确问题。
     */
    @Override
    public void onStart() {
        super.onStart();
        mTimetableView.onDateBuildListener()
                .onHighLight();
    }

    /**
     * 周次选择布局的左侧被点击时回调<br/>
     * 对话框修改当前周次
     */
    protected void onWeekLeftLayoutClicked() {
        final String items[] = new String[20];
        int itemCount = mWeekView.itemCount();
        for (int i = 0; i < itemCount; i++) {
            items[i] = "第" + (i + 1) + "周";
        }
        target = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("设置当前周");
        builder.setSingleChoiceItems(items, mTimetableView.curWeek() - 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        target = i;
                    }
                });
        builder.setPositiveButton("设置为当前周", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (target != -1) {
                    mWeekView.curWeek(target + 1).updateView();
                    mTimetableView.changeWeekForce(target + 1);
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    /**
     * 显示内容
     *
     * @param beans
     */
    protected void display(List<Schedule> beans) {
        String str = "";
        for (Schedule bean : beans) {
            str += bean.getName() + ","+bean.getWeekList().toString()+","+bean.getStart()+","+bean.getStep()+"\n";
        }
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_layout:
                //如果周次选择已经显示了，那么将它隐藏，更新课程、日期
                //否则，显示
                if (mWeekView.isShowing()) hideWeekView();
                else showWeekView();
                break;
        }
    }

    /**
     * 隐藏周次选择，此时需要将课表的日期恢复到本周并将课表切换到当前周
     */
    public void hideWeekView(){
        mWeekView.isShow(false);
        titleTextView.setTextColor(getResources().getColor(R.color.app_course_textcolor_blue));
        int cur = mTimetableView.curWeek();
        mTimetableView.onDateBuildListener()
                .onUpdateDate(cur, cur);
        mTimetableView.changeWeekOnly(cur);
    }

    public void showWeekView(){
        mWeekView.isShow(true);
        titleTextView.setTextColor(getResources().getColor(R.color.app_red));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if( resultCode==1){
            MySubject mySubject=data.getParcelableExtra("mySubject");
            String startWeek=data.getStringExtra("startWeek");
            String endWeek=data.getStringExtra("endWeek");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+startWeek+endWeek+mySubject.getTeacher());
            addSchedule.setName(mySubject.getName());
            addSchedule.setRoom(mySubject.getRoom());
            addSchedule.setDay(mySubject.getDay());
            addSchedule.setStart(mySubject.getStart());
            addSchedule.setStep(mySubject.getStep());
            List<Integer>integerList=new LinkedList<>();
            for(int i=Integer.parseInt(startWeek);i<=Integer.parseInt(endWeek);i++){
                integerList.add(i);
            }
            addSchedule.setWeekList(integerList);
            requestData2();

        }
    }




    @Override
    public void onResume() {
        super.onResume();
        Log.i("FragmentClassTable","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("FragmentClassTable","onRause");
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(isVisible){
            Log.i("FragmentClassTable","aaa");
        }else{
            Log.i("FragmentClassTable","bbb");
        }
    }



}