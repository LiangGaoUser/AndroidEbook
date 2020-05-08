package com.lianggao.whut.androidebook.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lianggao.whut.androidebook.Model.Book;
import com.lianggao.whut.androidebook.Model.MySubject;

import java.util.LinkedList;
import java.util.List;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 20:00
 */
public class ClassTableManger {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper sqLiteHelper;
    public ClassTableManger(Context context){
        this.context=context;
    }
    public void createDb(){
        sqLiteHelper=new SQLiteHelper(context);
        sqLiteDatabase=sqLiteHelper.getWritableDatabase();
        sqLiteHelper.createClassTable(sqLiteDatabase);

    }
    //  String sql="create table if not exists class_table(name varchar(50),location varchar(50), start_week int ,end_week int,class_start int,class_step int,class_day int)";
    public void deleteTable(){
        sqLiteDatabase.execSQL("drop  table  class_table");
    }
    //添加一本书
    public void addClass(MySubject mySubject){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", mySubject.getName());
        contentValues.put("location", mySubject.getRoom());
        contentValues.put("start_week", mySubject.getWeekList().get(0));
        contentValues.put("end_week",mySubject.getWeekList().get(mySubject.getWeekList().size()-1));
        contentValues.put("class_start",mySubject.getStart());
        contentValues.put("class_step",mySubject.getStep());
        contentValues.put("class_day",mySubject.getDay());
        System.out.println("####" +mySubject.getName() + mySubject.getWeekList().get(0) + mySubject.getWeekList().get(mySubject.getWeekList().size()-1) +mySubject.getStart() + mySubject.getStep()+mySubject.getDay());
        int result=(int)sqLiteDatabase.insert("class_table", null, contentValues);
        Log.i("ClassTableManger", "往class_table表中添加一门课程"+result);
    }
    public List<MySubject> findAllClass(){
        Cursor cursor=sqLiteDatabase.query("class_table",new String[]{"name","location","start_week","end_week","class_start","class_step","class_day"},null,null,null,null,null);
        MySubject mySubject;
        List<MySubject>mySubjectList=new LinkedList<>();
        while(cursor.moveToNext()){
            mySubject=new MySubject();
            String name=cursor.getString(0);
            String location=cursor.getString(1);
            int start_week=cursor.getInt(2);
            int end_week=cursor.getInt(3);
            int class_start=cursor.getInt(4);
            int class_step=cursor.getInt(5);
            int class_day=cursor.getInt(6);
            List<Integer>integerList;
            integerList=new LinkedList<>();
            for(int i=start_week;i<=end_week;i++){
                integerList.add(i);
            }
            mySubject.setName(name);
            mySubject.setRoom(location);
            mySubject.setStart(class_start);
            mySubject.setStep(class_step);
            mySubject.setDay(class_day);
            mySubject.setWeekList(integerList);
            mySubjectList.add(mySubject);
            Log.i("databaseManger",name+" "+location+" "+start_week+" "+end_week+" "+class_start +" "+class_step+" "+class_day);
        }
        cursor.close();
        return mySubjectList;
    }
    public void deleteTableContent(){
        sqLiteDatabase.execSQL("delete  from  class_table");
    }
    public void dropTable(String className){
        sqLiteDatabase.delete("class_table","name=?",new String[]{className});
    }



}
