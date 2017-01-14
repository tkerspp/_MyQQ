package com.example.administrator.myqq.Entity;

public class ChatEntity {
	private int avatar;
	private String message;
	private String time;
	private boolean isLeft;//是否为收到的消息，在左边
	
	public ChatEntity(int avatar,String message,String time,boolean isLeft){
		this.avatar = avatar;
		this.message = message;
		this.time = time;
		this.isLeft = isLeft;
	}
	
	public int getAvatar() {
		return avatar;
	}
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public boolean isLeft() {
		return isLeft;
	}
	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}
}
