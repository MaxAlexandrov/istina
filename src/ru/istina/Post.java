package ru.aleksandrov.news;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class Post {

	private int id;
	private int from_id;
	private int to_id;
	private long date;
	private String post_type;
	private String text;
	private String title;
	private Count comments;
	private Count likes;
	private Count reposts;
	private List<Attachment> attachments;
	public int getId() {
		return this.id;
	}
    
	public String getTitle(int size) {
		this.title="";
		if (this.text.toLowerCase().contains("<br>")) {	
			this.title=this.text.substring(0, this.text.indexOf("<br>"));
			return this.title;
		}else return this.title;
	}
	

	 
	public String getSmallText(int numWords) {
		final String LOG_TAG = "myLogs";
		StringBuilder sb = new StringBuilder();  
		String[] words = this.text.replace(this.title," ").replace("<br>", " ").split(" ");	
		Pattern p = Pattern.compile("#(\\w+)");
		Pattern httpp = Pattern.compile("(http|https|ftp\\w+)");
	
		  
      
		if (words.length < numWords) {
			numWords = words.length;
		}		
		for (int i = 0; i <numWords; i++) {
			Matcher m = p.matcher(words[i]);
			Matcher httpm = httpp.matcher(words[i]);
			if(httpm.find()){ Log.d(LOG_TAG, "���� "+ httpm.group());}
				else
				{       
					 if(m.find()){ Log.d(LOG_TAG, "���� "+ m.group());}  else
	                 {
	                	 sb.append(words[i]).append(" ");
	                	 }
			     }			
		}
		sb.append("...");
		return sb.toString();
	}

	public String getText() {	
		return text.replace("<br>","\r\n");
	}

	public String getDate() {
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return df.format(new Date((this.date * 1000)));
	}

	

	public List<Attachment> getAttachements() {
		return this.attachments;
	}

	public Count getComments() {
		return this.comments;
	}

	public Count getLikes() {
		return this.likes;
	}

	public Count getReposts() {
		return this.reposts;
	}
}
