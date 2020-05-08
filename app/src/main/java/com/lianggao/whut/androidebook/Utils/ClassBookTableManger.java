package com.lianggao.whut.androidebook.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lianggao.whut.androidebook.Model.ClassBook;
import com.lianggao.whut.androidebook.Model.MySubject;

import java.util.LinkedList;
import java.util.List;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 20:00
 */
public class ClassBookTableManger {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper sqLiteHelper;
    public ClassBookTableManger(Context context){
        this.context=context;
    }
    public void createDb(){
        sqLiteHelper=new SQLiteHelper(context);
        sqLiteDatabase=sqLiteHelper.getWritableDatabase();
        sqLiteHelper.createClassBookTable(sqLiteDatabase);

    }
    public void deleteTable(){
        sqLiteDatabase.execSQL("drop  table  class_book_table");
    }
    public void addClassBook(ClassBook classBook){

        ContentValues contentValues = new ContentValues();
        contentValues.put("class_name", classBook.getClass_name());
        contentValues.put("file_name", classBook.getFile_name());
        contentValues.put("file_path", classBook.getFile_path());

        System.out.println("####" +classBook.toString());
        int result=(int)sqLiteDatabase.insert("class_book_table", null, contentValues);
        Log.i("ClassBookTableManger", "往class_book_table表中添加一门课程"+result);
    }
    public List<ClassBook> findAllClassBook(){
        Cursor cursor=sqLiteDatabase.query("class_book_table",new String[]{"class_name","file_name","file_path"},null,null,null,null,null);
        ClassBook classBook;
        List<ClassBook>classBookList=new LinkedList<>();
        while(cursor.moveToNext()){
            classBook=new ClassBook();
            String class_name=cursor.getString(0);
            String file_name=cursor.getString(1);
            String file_path=cursor.getString(2);
            classBook.setClass_name(class_name);
            classBook.setFile_name(file_name);
            classBook.setFile_path(file_path);
            classBookList.add(classBook);
            Log.i("databaseManger",classBook.toString());
        }
        cursor.close();
        return classBookList;
    }
    public void deleteTableContent(){
        sqLiteDatabase.execSQL("delete  from  class_book_table");
    }
    public boolean findByName(String class_name){

        Cursor cursor=sqLiteDatabase.query(" class_book_table",new String[]{"class_name"},"class_name=?",new String[]{class_name},null,null,null);
        if(cursor.moveToFirst()==false){
            System.out.println("##没有该课程" );
            return false;
        }
        else{
            return true;
        }

    }

    public void setFileNameByClassName(ClassBook classBook){
        ContentValues contentValues = new ContentValues();
        contentValues.put("file_name",classBook.getFile_name());
        contentValues.put("file_path",classBook.getFile_path());
        sqLiteDatabase.update("class_book_table",contentValues,"class_name=?",new String[]{classBook.getClass_name()});
        Log.i("ClassBookTableManger", "修改课程"+classBook.getClass_name()+classBook.getFile_name()+classBook.getFile_path());
    }

    public ClassBook findByName2(String class_name){
        Cursor cursor=sqLiteDatabase.query(" class_book_table",new String[]{"class_name","file_name","file_path"},"class_name=?",new String[]{class_name},null,null,null);
        ClassBook classBook=new ClassBook();
        while(cursor.moveToNext()){
            classBook.setClass_name(class_name);
            classBook.setFile_name(cursor.getString(1));
            classBook.setFile_path(cursor.getString(2));
        }
        return  classBook;
    }
    public void deleteClassBook(String className){
        ContentValues contentValues = new ContentValues();
        contentValues.put("file_name","");
        contentValues.put("file_path","");
        sqLiteDatabase.update("class_book_table",contentValues,"class_name=?",new String[]{className});
    }

    public void dropClassBook(String className){
        sqLiteDatabase.delete("class_book_table","class_name=?",new String[]{className});

    }

}
