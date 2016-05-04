package ru.aleksandrov.news;


public class Attachment {

	private String type;
	private Photo photo;
	private VideoClass video;
	
	
	public String getType() {
		return this.type;
	}

	public Photo getPhoto() {
		if ("photo".equals(this.type)) {
		return this.photo;
		}
		return null;
	}
	public VideoClass getVideo()
	{
		if ("video".equals(this.type)){
			return this.video;}
		return null;
	}
}
