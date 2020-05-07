package com.lianggao.whut.androidebook.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 19:52
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static String db_name="android_ebook";
    public SQLiteHelper(Context context){
        super(context,db_name,null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //书本id，书本名称，书本作者，书本封面路径，书本内容文件路径
        String sql="create table if not exists bookshelf( book_id int , book_name varchar(100),book_author varchar(100), book_cover_path varchar(100), book_path varchar(100),book_main_kind varchar(50),book_detail_kind varchar(50))";
        db.execSQL(sql);
        Log.i("SQLiteHelper","建立了bookshelf表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("SQLiteHelper","update database...");
    }

    public void createBookShelfHistoryTable(SQLiteDatabase db){
        String sql="create table if not exists bookshelf_history(book_id int ,book_name varchar(100),book_author varchar(100), book_cover_path varchar(100),book_main_kind varchar(50),book_detail_kind varchar(50))";
        db.execSQL(sql);
        Log.i("SQLiteHelper","建立了bookshelf_history表");
    }



    public void createClassTable(SQLiteDatabase db){
        String sql="create table if not exists class_table(name varchar(50),location varchar(50), start_week int ,end_week int,class_start int,class_step int,class_day int)";
        db.execSQL(sql);
        Log.i("SQLiteHelper","建立了class_table表");
    }



}
