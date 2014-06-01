package com.example.myanmarnews.Items;

public class NewsItem {

	private String title;
	private int imageID;
	private String content;
	private String publicDate;
	
	public NewsItem() {
		// TODO Auto-generated constructor stub
	}
	public NewsItem(String title, int imageID, String content, String publicDate){
		this.setTitle(title);
		this.imageID = imageID;
		this.content = content;
		this.setPublicDate(publicDate);
	
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublicDate() {
		return publicDate;
	}
	public void setPublicDate(String publicDate) {
		this.publicDate = publicDate;
	}

}
