package com.lianggao.whut.androidebook.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 15:11
 */
public class Book implements Parcelable {

    private String book_name;//书本名称
    private String book_author;//书本作者
    private String book_short_content_path;//书本简介路径
    private double book_evalute;//书本评分
    private int book_word_number;//书本总字数
    private String book_cover_path;//书本封面路径
    private String book_publish;//书本出版社
    private int book_download_number;//书本下载数量
    private String book_use;//书本类型比如热门书籍,推荐书籍

    //private int book_detail_kind_id;//书的详细类型编号比如13心理学
    private String book_main_kind;
    private String book_detail_kind;


    private int book_catalog_total_number;//书的总共章节数目
    private String book_path;//书的文件路径





    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_short_content_path() {
        return book_short_content_path;
    }

    public void setBook_short_content_path(String book_short_content_path) {
        this.book_short_content_path = book_short_content_path;
    }

    public double getBook_evalute() {
        return book_evalute;
    }

    public void setBook_evalute(double book_evalute) {
        this.book_evalute = book_evalute;
    }

    public int getBook_word_number() {
        return book_word_number;
    }

    public void setBook_word_number(int book_word_number) {
        this.book_word_number = book_word_number;
    }

    public String getBook_cover_path() {
        return book_cover_path;
    }

    public void setBook_cover_path(String book_cover_path) {
        this.book_cover_path = book_cover_path;
    }

    public String getBook_publish() {
        return book_publish;
    }

    public void setBook_publish(String book_publish) {
        this.book_publish = book_publish;
    }

    public int getBook_download_number() {
        return book_download_number;
    }

    public void setBook_download_number(int book_download_number) {
        this.book_download_number = book_download_number;
    }

    public String getBook_use() {
        return book_use;
    }

    public void setBook_use(String book_use) {
        this.book_use = book_use;
    }



    public int getBook_catalog_total_number() {
        return book_catalog_total_number;
    }

    public void setBook_catalog_total_number(int book_catalog_total_number) {
        this.book_catalog_total_number = book_catalog_total_number;
    }

    public String getBook_path() {
        return book_path;
    }

    public void setBook_path(String book_path) {
        this.book_path = book_path;
    }

    public String getBook_main_kind() {
        return book_main_kind;
    }

    public void setBook_main_kind(String book_main_kind) {
        this.book_main_kind = book_main_kind;
    }

    public String getBook_detail_kind() {
        return book_detail_kind;
    }

    public void setBook_detail_kind(String book_detail_kind) {
        this.book_detail_kind = book_detail_kind;
    }

    public static final Parcelable.Creator<Book>    CREATOR=new Parcelable.Creator<Book>(){

        @Override
        public Book createFromParcel(Parcel source) {
            Book book=new Book();

            book.setBook_name(source.readString());
            book.setBook_author(source.readString());
            book.setBook_short_content_path(source.readString());
            book.setBook_evalute(source.readDouble());
            book.setBook_word_number(source.readInt());
            book.setBook_cover_path(source.readString());
            book.setBook_publish(source.readString());
            book.setBook_download_number(source.readInt());
            book.setBook_use(source.readString());
            book.setBook_main_kind(source.readString());
            book.setBook_detail_kind(source.readString());
            book.setBook_catalog_total_number(source.readInt());
            book.setBook_path(source.readString());
            return book;


        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(book_name);
        dest.writeString(book_author);
        dest.writeString(book_short_content_path);
        dest.writeDouble(book_evalute);
        dest.writeInt(book_word_number);
        dest.writeString(book_cover_path);
        dest.writeString(book_publish);
        dest.writeInt(book_download_number);
        dest.writeString(book_use);
        dest.writeString(book_main_kind);
        dest.writeString(book_detail_kind);
        dest.writeInt(book_catalog_total_number);
        dest.writeString(book_path);




    }
}
