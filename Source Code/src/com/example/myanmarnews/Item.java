package com.example.myanmarnews;

public class Item {

	private String title;
	private int imageID;
	private int notification;
	
	public Item() {
		// TODO Auto-generated constructor stub
	}
	public Item(String content, int imageID){
		this.title = content;
		this.imageID = imageID;
		notification = 0;
		
	}

	public String getContent() {
		return title;
	}

	public void setContent(String content) {
		this.title = content;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	public int getNotification() {
		return notification;
	}
	public void setNotification(int notification) {
		this.notification = notification;
	}

}
