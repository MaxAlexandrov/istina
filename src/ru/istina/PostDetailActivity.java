package ru.aleksandrov.news;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class PostDetailActivity extends Activity {

	Gson gson = new Gson();
	  static final String TAG = "myLogs";
	String id_position,tag_position, text_post;
	int id_current=0;
	private int fullText;
	private int size=0;
	ArrayList<String> Image_pages;
	List<View> prgmImages;
	 ImageView ivPhoto;
	 GridView llPostPhotos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_detail_item);	
		SharedPreferences sPref = getSharedPreferences("Posts", Context.MODE_PRIVATE);
		String postString = sPref.getString("postId"
				+ getIntent().getStringExtra("postId"), "");
		fullText=getIntent().getIntExtra("Size", fullText);
		Log.d("TAG","��������   fullText " + fullText);
		this.id_position = postString; 
		
		Post post = gson.fromJson(postString, Post.class);		
		TextView tvPostText = (TextView) findViewById(R.id.tvPostText);
		text_post=post.getText();
		
		text_post=text_post.replace(post.getTitle(size)," ");
		
		tvPostText.setText(post.getDate()+text_post);
		
		TextView tvPostTitle = (TextView) findViewById(R.id.tvPostDate);
		if (post.getTitle(size)=="") {tvPostTitle.setVisibility(View.GONE);}
		else{
		tvPostTitle.setVisibility(View.VISIBLE);
		tvPostTitle.setText(post.getTitle(35));}
		
		((TextView) findViewById(R.id.tvLikes)).setText(""
				+ post.getLikes().getCount());
		((TextView) findViewById(R.id.tvReposts)).setText(""
				+ post.getReposts().getCount());
		
		
		if (fullText>800000){tvPostText.setTextSize(22);tvPostTitle.setTextSize(25);}
		else {
			if (fullText>480000&fullText<800000){tvPostText.setTextSize(15);tvPostTitle.setTextSize(18);} 
			}
		
		
		prgmImages = new ArrayList<View>();
		Image_pages = new ArrayList<String>();
		llPostPhotos = (GridView) findViewById(R.id.llPostPhotos);
		if (HttpConnector.checkInternet(this) == true) {
		try{
			for (Attachment attachment : post.getAttachements()) {
				if (attachment.getPhoto() != null) {
					ivPhoto = new ImageView(this);
					String tag = attachment.getPhoto().getSrcBig();
					ivPhoto.setTag(tag);	
					ivPhoto.setMaxHeight(100);
					ivPhoto.setMaxWidth(100);
					ivPhoto.setBackgroundResource(R.drawable.boarder_big);				
					ivPhoto.setAdjustViewBounds(true);
					new TaskLoadImage(ivPhoto,tag, this).execute();
					prgmImages.add(ivPhoto);					
	     			Image_pages.add(tag);
					Log.d("TAG","��������" + prgmImages);
				}
 			}
		}
		catch (Exception e) {
			Thread.currentThread().interrupt();}
		
		
		
		
		try{
	  for (Attachment attachment : post.getAttachements()) {
		if(attachment.getVideo() != null)
		{
					
				ivPhoto = new ImageView(this);
				String tag = attachment.getVideo().getimageBig();
				ivPhoto.setTag(tag);	
				ivPhoto.setMaxHeight(100);
				ivPhoto.setMaxWidth(100);
				ivPhoto.setBackgroundResource(R.drawable.boarder_big);				
				ivPhoto.setAdjustViewBounds(true);
				new TaskLoadImage(ivPhoto,tag, this).execute();
				prgmImages.add(ivPhoto);			
				
     			Image_pages.add(tag);
				}
			}
		}
		catch (Exception e) {
			Thread.currentThread().interrupt();}
		
		} else {
			Toast.makeText(this, "���������� ��������� ���������� ��-�� �� ����������.\n��������� �����������.", Toast.LENGTH_LONG)
					.show();
		}
		
		
		
	    llPostPhotos.setAdapter(new CustomAdapter(this,prgmImages));
	   
	    adjustGridView();
	

	llPostPhotos.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View v,
                 int position, long id) {
        //	 tag_position = (String) view.getTag();
     		Log.d("TAG","��������" + tag_position);
     		Intent intent = new Intent(getApplicationContext(),GalleryImageView.class);
     	    intent.putExtra("id", position);
     	    intent.putStringArrayListExtra("id_tag_string_massiv",Image_pages);
     	   // intent.putExtra("tag",this.tag_position);
     	   intent.putExtra("postId",id_position);
     	  //  Log.d("TAG","��������" + Image_pages);
     	     startActivity(intent);	
         }
     });
}

	private void adjustGridView() {
		llPostPhotos.setNumColumns(GridView.AUTO_FIT);
		llPostPhotos.setColumnWidth(100);
		llPostPhotos.setVerticalSpacing(5);
		llPostPhotos.setHorizontalSpacing(5);
		llPostPhotos.setStretchMode(GridView.NO_STRETCH);
	  }
}