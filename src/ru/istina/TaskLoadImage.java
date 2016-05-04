package ru.aleksandrov.news;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class TaskLoadImage extends AsyncTask<String, Void, Bitmap> {

	private final WeakReference<ImageView> imageViewReference;
	private String tag;
	private Context context;
	
	public TaskLoadImage(ImageView ivPhoto, String _tag, Context _context) {
		this.imageViewReference = new WeakReference<ImageView>(ivPhoto);
		this.tag = _tag;
		this.context = _context;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = null;
		String paths[] = (this.tag).split("/");
		String name = paths[(paths.length - 1)];
		File filePath = this.context.getFileStreamPath(name);
		if (filePath.exists()) {
			try {
				FileInputStream fi;
				fi = new FileInputStream(filePath);
				bitmap = BitmapFactory.decodeStream(fi);
				return bitmap;
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}
		} else {
			try {
				bitmap = BitmapFactory.decodeStream(HttpConnector
						.getISFromUrl(this.tag));
				FileOutputStream fos = context.openFileOutput(name,
						Context.MODE_PRIVATE);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.close();
				return bitmap;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (bitmap != null) {
			if (imageViewReference != null) {
				ImageView imageView = (ImageView) imageViewReference.get();
				if (imageView != null) {
					if ((this.tag).equals(imageView.getTag())) {
						imageView.setImageBitmap(bitmap);
					}
				}
			}
		}
	}

}