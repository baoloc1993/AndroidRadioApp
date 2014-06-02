package com.example.myanmarnews.Items;

public class SocialNetworkItem {

	private String url;
	private int iconId;
	
	public SocialNetworkItem() {
		// TODO Auto-generated constructor stub
	}
	public SocialNetworkItem(String url, int iconId){
		this.url = url;
		this.iconId = iconId;
		
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getImageID() {
		return iconId;
	}

	public void setImageID(int imageID) {
		this.iconId = imageID;
	}
	

}
