package com.example.myanmarnews;

public class Item {

	private String title;
	private int imageID;
	
	public Item() {
		// TODO Auto-generated constructor stub
	}
	public Item(String content, int imageID){
		this.title = content;
		this.imageID = imageID;
		
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

}
