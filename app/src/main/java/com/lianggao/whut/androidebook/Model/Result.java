package com.lianggao.whut.androidebook.Model;

public class Result {
	private String url;
	private String result;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "Result [url=" + url + ", result=" + result + "]";
	}
	
	
}
