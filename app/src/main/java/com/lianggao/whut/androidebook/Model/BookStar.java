package com.lianggao.whut.androidebook.Model;

public class BookStar {
	private String userId;
	private String bookName;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	@Override
	public String toString() {
		return "BookStar [userId=" + userId + ", bookName=" + bookName + "]";
	}
	
	
}
