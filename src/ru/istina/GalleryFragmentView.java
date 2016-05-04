package ru.aleksandrov.news;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class GalleryFragmentView extends PagerAdapter {
	 private static final String TAG = null;
	List<View> pages = null;
	int position_count;
	 public GalleryFragmentView(List<View> pages){
	        this.pages = pages;
	       
	    }
	 
	 
	 public Object instantiateItem(View collection, int position){
		 Log.d(TAG, "view = " + position);
	        View v = pages.get(position);
	        ((ViewPager) collection).addView(v,0);
	        Log.d(TAG, "return view = " + position);
	        return v;
	        
	    }
	  
	 public void destroyItem(View collection, int position, Object view){
	        ((ViewPager) collection).removeView((View) view);
	        Log.d(TAG, "destroy position = " + position);
	    }
	 

		public int getCount() {
			// TODO Auto-generated method stub
			return pages.size();
		}
		
		@Override

		public boolean isViewFromObject(View view, Object object){
			 Log.d(TAG, "view23232 = " );
		    return view.equals(object);
		}

	public void finishUpdate(View arg0){
		 Log.d(TAG, "UPDATE = " );
	}
	
	

	

//	public void restoreState(Parcelable state, ClassLoader GalleryFragment)
//	{       
//	   this.position_count = ((Bundle) state).getInt("POSITION_COUNT", position_count);
//	Log.d(TAG, "������������ ��� �� ��� = " );
//	}
//	
//	
//	public Parcelable saveState(){	
//		 Bundle bundle = (Bundle) saveState();
//		bundle.putInt("POSITION_COUNT", position_count);
//	   Log.d(TAG, "������������ ��� �� ��� = " );
//	    return null;
//    }
//	
	public void startUpdate(View arg0){
    }
	
}