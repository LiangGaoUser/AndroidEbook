package com.lianggao.whut.androidebook.Model;
//返回用户请求的想法，包括书籍的路径，书籍的名称，书籍的想法上传时间，书籍的想法数量
public class ResultThought {
	private String bookPath;//书籍的路径
	private String bookName;//书籍的名称
	private String dataTime;//书籍的想法上传时间
	private String thoughtNumber;//书籍的想法数量
	public String getBookPath() {
		return bookPath;
	}
	public void setBookPath(String bookPath) {
		this.bookPath = bookPath;
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
	public String getThoughtNumber() {
		return thoughtNumber;
	}
	public void setThoughtNumber(String thoughtNumber) {
		this.thoughtNumber = thoughtNumber;
	}
	@Override
	public String toString() {
		return "ResultThought [bookPath=" + bookPath + ", bookName=" + bookName + ", dataTime=" + dataTime
				+ ", thoughtNumber=" + thoughtNumber + "]";
	}
	
	
	
}
