package com.lianggao.whut.androidebook.Model;

//用户浏览记录
public class UserReadHistory {
	private String userId;
	private String bookName;
	private String dataTime;
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
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	@Override
	public String toString() {
		return "UserReadHistory [userId=" + userId + ", bookName=" + bookName + ", dataTime=" + dataTime + "]";
	}
	
	
	
	
	
	
	
	
	
}
