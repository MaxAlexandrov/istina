package ru.aleksandrov.news;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;


public class GalleryImageView extends Activity {

	static final String TAG = "myLogs";
	Gson gson = new Gson();
	 View page;
     ImageView ivPhoto;
     int position_current=1;
     List<View> pages;
     ArrayList<String> image_page;
     Parcelable state;
     ViewPager viewPager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		if ("count"!=null) {
//			Log.d( TAG, "�������� ����������� ������" );
//			this.RestoreInstanceState(state);}
		
		LayoutInflater inflater = LayoutInflater.from(this);
        pages = new ArrayList<View>();
		SharedPreferences sPref = getSharedPreferences("Posts",
		Context.MODE_PRIVATE);
		image_page = getIntent().getStringArrayListExtra("id_tag_string_massiv");
		Log.d( TAG, "�������� ����������� ������"+image_page );
		String postString = getIntent().getStringExtra("postId");
		position_current = getIntent().getIntExtra("id", position_current);
		Post post = gson.fromJson(postString, Post.class);
       
        
if (HttpConnector.checkInternet(this) == true) {
	try {
		//for (Attachment attachment : post.getAttachements()) {
			//if (attachment.getPhoto() != null) {
				for(int position=0; position< image_page.size();position++){
				page = inflater.inflate(R.layout.gallery_fragment,null);
				ivPhoto = new ImageView(this);
				//String tag = attachment.getPhoto().getSrcBig();
				ivPhoto.setTag(image_page.get(position).toString());
				ivPhoto.setAdjustViewBounds(true);
				new TaskLoadImage(ivPhoto, image_page.get(position).toString(), this).execute();
				Log.d( TAG, "�������� ����������� ������"+image_page.get(position).toString());
				pages.add(ivPhoto);
				Log.d( TAG, "�������� ����������� ������"+pages);
			}				
		//}
		
		
	} catch (Exception e) {
		Thread.currentThread().interrupt();
	}

} else {
	Toast.makeText(
			this,
			"Невозможно подгрузить изображения.\n Сеть недоступна.",
			Toast.LENGTH_LONG).show();
}
        
        GalleryFragmentView pagerAdapter = new GalleryFragmentView(pages);
        viewPager =  new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);  
        viewPager.setCurrentItem(position_current);
        Log.d(TAG, "������ ������� = " + this.position_current);    
        setContentView(viewPager); 
	}
	
//	protected void SaveInstanceState(Bundle outState) {
//	    super.onSaveInstanceState(outState);	  
//	    outState.putInt("count", position);
//	    Log.d(TAG, "����������� ������� = " + position);
//	  }
//	
//	protected void RestoreInstanceState(Bundle savedInstanceState) {
//	    super.onRestoreInstanceState(savedInstanceState);
//	    savedInstanceState.getInt("count", position);
//	    Log.d(TAG, "�������������� ������� = " + position);
//	  
//	  }
}
