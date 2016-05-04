package ru.aleksandrov.news;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class ToaskLoadVideo extends AsyncTask<String, Void, Bitmap>{
	 private VideoView videoView = null;
     private ProgressBar prog = null;
     private Context ctx = null;
     private MediaController mediaController = null;
     private final WeakReference<VideoView> imageViewReference;
 	private String tag;
 	private Context context;
 	
 	public ToaskLoadVideo(VideoView ivVideo, String _tag, Context _context) {
 		this.imageViewReference = new WeakReference<VideoView>(ivVideo);
 		this.tag = _tag;
 		this.context = _context;
 	}
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}

}
