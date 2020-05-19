package com.lianggao.whut.androidebook.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lianggao.whut.androidebook.Model.Book;

import java.util.LinkedList;
import java.util.List;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 20:00
 */
public class bookShelfHistoryTableManger {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper sqLiteHelper;
    public bookShelfHistoryTableManger(Context context){
        this.context=context;
    }
    public void createDb(){
        sqLiteHelper=new SQLiteHelper(context);
        sqLiteDatabase=sqLiteHelper.getWritableDatabase();
        sqLiteHelper.createBookShelfHistoryTable(sqLiteDatabase);
    }
    public void deleteTable(){
        sqLiteDatabase.execSQL("drop  table  bookshelf_history");
    }
    //添加一本书
    public void addBook(Book book){
        ContentValues contentValues = new ContentValues();

        contentValues.put("book_name", book.getBook_name());
        contentValues.put("book_author", book.getBook_author());
        contentValues.put("book_cover_path", book.getBook_cover_path());
        contentValues.put("book_main_kind", book.getBook_main_kind());
        contentValues.put("book_detail_kind", book.getBook_detail_kind());

        sqLiteDatabase.insert("bookshelf_history", null, contentValues);
        Log.i("bookShelfTableManger", "往bookshelf_history表中添加一本图书");
    }

    //根据书的书的名称删除书架中的书籍
    public void deleteBookByName(String book_name){
        sqLiteDatabase.delete("bookshelf_history","book_name=?",new String[]{book_name});
        Log.i("databaseManger","根据书本名称删除一条数据");
    }

    public boolean findBookByName(String book_name){
        Cursor cursor=sqLiteDatabase.query("bookshelf_history",new String[]{"book_name"},"book_name=?",new String[]{book_name},null,null,null);
        if(cursor.moveToFirst()==false){
            System.out.println("##有该图书");
            return false;
        }
        else{
            return true;
        }

    }
    public List<Book> findAllBook(){
        Cursor cursor=sqLiteDatabase.query("bookshelf_history",new String[]{"book_name","book_author","book_cover_path","book_main_kind","book_detail_kind"},null,null,null,null,null);
        Book book;
        List<Book>bookList=new LinkedList<>();
        while(cursor.moveToNext()){
            book=new Book();

            String book_name=cursor.getString(0);
            String book_author=cursor.getString(1);
            String book_cover_path=cursor.getString(2);
            String book_main_kind=cursor.getString(3);
            String book_detail_kind=cursor.getString(4);

            book.setBook_name(book_name);
            book.setBook_author(book_author);
            book.setBook_cover_path(book_cover_path);
            book.setBook_main_kind(book_main_kind);
            book.setBook_detail_kind(book_detail_kind);

            bookList.add(book);
            Log.i("databaseManger", "book_name"+ "+book_author"+" "+book_cover_path);
        }
        cursor.close();
        return bookList;
    }
    public List<String> findAllCoverList(){
        Cursor cursor=sqLiteDatabase.query("bookshelf_history",new String[]{"book_cover_path"},null,null,null,null,null);
        List<String>coverList=new LinkedList<>();
        while(cursor.moveToNext()){
            String cover=cursor.getString(0);
            System.out.println("##@@"+cover);
            coverList.add(cover);
        }
        cursor.close();
        return coverList;
    }

    public void deleteTableContent(){
        sqLiteDatabase.execSQL("delete  from  bookshelf");
    }
    public List<String> findCoverListByNameList(List<String>nameList){
        Cursor cursor;
        List<String>coverPathList;
        coverPathList=new LinkedList<>();
        for(int i=0;i<nameList.size();i++){
            cursor=sqLiteDatabase.query("bookshelf_history",new String[]{"book_cover_path"},"book_name=?",new String[]{nameList.get(i)},null,null,null);
            while(cursor.moveToNext()){
                String path=cursor.getString(0);
                System.out.println("***"+path);
                coverPathList.add(path);
            }

        }
        return coverPathList;
    }
    public void deleteByNameList(List<String>nameList){
        for(int i=0;i<nameList.size();i++){
            sqLiteDatabase.delete("bookshelf_history","book_name=?",new String[]{nameList.get(i)});
        }
    }

}
