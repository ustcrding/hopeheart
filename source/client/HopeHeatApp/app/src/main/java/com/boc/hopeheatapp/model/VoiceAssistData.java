package com.boc.hopeheatapp.model;

public class VoiceAssistData {

	public static final int SEND = 1;
	public static final int RECEIVER = 2;
	private String content;
	private int flag;

	public VoiceAssistData(String content, int flag) {
		setContent(content);
		setFlag(flag);
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
