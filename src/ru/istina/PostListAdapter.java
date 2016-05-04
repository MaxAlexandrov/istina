package ru.aleksandrov.news;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostListAdapter extends BaseAdapter {

	private Context ctx;
	private LayoutInflater lInflater;
	private DisplayMetrics Inf;
	private ArrayList<Post> posts;
	private int size=35;
	final String LOG_TAG = "myLogs";
	public int fullText;

	Attachment attachment;
	
	
	
	PostListAdapter(Context context, ArrayList<Post> _posts,int _fullText) {
		ctx = context;
		posts = _posts;
		fullText=_fullText;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
			
	}
	 

	@Override
	public int getCount() {
		return posts.size();
	}

	@Override
	public Object getItem(int position) {
		return posts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Bitmap bitmap = null;
		int sizewidth;
		int sizehieght;
		
		if (view == null) {
			view = lInflater.inflate(R.layout.post_list_item, parent, false);
		}
		Post post = (Post) getItem(position);
		
		
		ImageView ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
		TextView textTittle =  (TextView) view.findViewById(R.id.text_title);
		TextView text_post =  (TextView) view.findViewById(R.id.text);
		SpannableString ss,dd;
		String text;
		
		
		
		//	if (fullText>480000){size=50;textTittle.setTextSize(18);text_post.setTextSize(15);
		//	} 
		//	else
		  //     {size=35;}
		
		
		if (post.getTitle(size)=="") {textTittle.setVisibility(View.GONE);}
		else{
		textTittle.setVisibility(View.VISIBLE);
		text=post.getTitle(size);
		ss=new SpannableString (text);
		ss.setSpan(new MyLeadingMarginSpan2(5, 5), 0, ss.length(), 0);
		textTittle.setText(ss);
		}
		
		text=null;
		text=post.getSmallText(size);
		dd=new SpannableString (text);
		dd.setSpan(new MyLeadingMarginSpan2(5, 150), 0, dd.length(), 0);
		text_post.setText(dd);
		
		
		
		
		view.setTag(post.getId());
	
		
			ivPhoto.setImageResource(R.drawable.istina);
			ivPhoto.setVisibility(View.VISIBLE);
	
			try {
		for (Attachment attachment : post.getAttachements()) {			
			if (attachment.getPhoto() != null) {
				
				String tag = attachment.getPhoto().getSrc();
				ivPhoto.setTag(tag);
				String paths[] = (tag).split("/");
				String name = paths[(paths.length - 1)];
				File filePath = ctx.getFileStreamPath(name);
			
				if (filePath.exists()) {
					FileInputStream fi;
					try {
						fi = new FileInputStream(filePath);
						bitmap = BitmapFactory.decodeStream(fi);
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						Thread.currentThread().interrupt();
					}
					ivPhoto.setImageBitmap(bitmap);
				} else {
					new TaskLoadImage(ivPhoto, tag, ctx).execute();
				}
			
				break;
		  }
		}
				
	}
		catch (Exception e) {
			Thread.currentThread().interrupt();}
			return view;
	}
	
	
}
