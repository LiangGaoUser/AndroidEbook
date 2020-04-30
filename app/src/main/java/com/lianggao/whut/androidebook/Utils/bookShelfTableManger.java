package com.lianggao.whut.androidebook.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.lianggao.whut.androidebook.Model.Book;

import java.util.LinkedList;
import java.util.List;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 20:00
 */
public class bookShelfTableManger {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper sqLiteHelper;
    public bookShelfTableManger(Context context){
        this.context=context;
    }
    public void createDb(){
        sqLiteHelper=new SQLiteHelper(context);
        sqLiteDatabase=sqLiteHelper.getWritableDatabase();
        sqLiteHelper.onCreate(sqLiteDatabase);

    }
   // String sql="create table user(int book_id,String book_name,String book_author,String book_post_path,String book_path)";
    public void deleteTable(){
        sqLiteDatabase.execSQL("drop  table  bookshelf");
    }

    //添加一本书
    public void addBook(Book book){
        System.out.println("+++++++++");
        ContentValues contentValues=new ContentValues();
        contentValues.put("book_id",book.getBook_id());
        contentValues.put("book_name",book.getBook_name());
        contentValues.put("book_author",book.getBook_author());
        contentValues.put("book_cover_path",book.getBook_cover_path());
        contentValues.put("book_path",book.getBook_path());
        System.out.println("+++++++++++++++++++++++++++++"+book.getBook_id()+book.getBook_name()+book.getBook_author()+book.getBook_cover_path()+book.getBook_path());
        sqLiteDatabase.insert("bookshelf",null,contentValues);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Log.i("bookShelfTableManger","往bookshelf表中添加一本图书");
    }
    //根据书的id删除书架中的书籍
    public void deleteBookById(int book_id){
        String bookid=String.valueOf(book_id);
        sqLiteDatabase.delete("bookshelf","book_id=?",new String[]{bookid});
        Log.i("databaseManger","根据书本id删除一条数据");
    }
    //根据书的书的名称删除书架中的书籍
    public void deleteBookByName(String book_name){
        sqLiteDatabase.delete("bookshelf","book_name=?",new String[]{book_name});
        Log.i("databaseManger","根据书本名称删除一条数据");
    }



    public List<Book> findAllBook(){
        Cursor cursor=sqLiteDatabase.query("bookshelf",new String[]{"book_id","book_name","book_author","book_cover_path","book_path"},null,null,null,null,null);
        Book book;
        List<Book>bookList=new LinkedList<>();
        while(cursor.moveToNext()){
            book=new Book();
            int book_id=cursor.getInt(0);
            String book_name=cursor.getString(1);
            String book_author=cursor.getString(2);
            String book_cover_path=cursor.getString(3);
            String book_path=cursor.getString(4);
            book.setBook_id(book_id);
            book.setBook_name(book_name);
            book.setBook_author(book_author);
            book.setBook_cover_path(book_cover_path);
            book.setBook_path(book_path);
            bookList.add(book);
            Log.i("databaseManger",book_id+" "+book_name+" "+book_author+" "+book_cover_path+" "+book_path );
        }
        cursor.close();
        return bookList;
    }



}
