package com.lianggao.whut.androidebook.Model;

public class DetailResultThought {
	private String userSelectedText;
	private String userThoughtText;
	private String dataTime;
	public String getUserSelectedText() {
		return userSelectedText;
	}
	public void setUserSelectedText(String userSelectedText) {
		this.userSelectedText = userSelectedText;
	}
	public String getUserThoughtText() {
		return userThoughtText;
	}
	public void setUserThoughtText(String userThoughtText) {
		this.userThoughtText = userThoughtText;
	}
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	@Override
	public String toString() {
		return "DetailResultThought [userSelectedText=" + userSelectedText + ", userThoughtText=" + userThoughtText
				+ ", dataTime=" + dataTime + "]";
	}
	
	
}
