package ru.aleksandrov.news;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
 
public class CustomAdapter extends BaseAdapter{
	private Context mContext;
   List<View> imagesIds;
      private static LayoutInflater inflater=null;
    public CustomAdapter(PostDetailActivity postdetailActivity,List<View> NewsImages) {
        // TODO Auto-generated constructor stub  	
    	Log.d("TAG","�������� CustomAdapter" );
    	mContext=postdetailActivity;
        imagesIds=NewsImages;
        inflater = ( LayoutInflater )mContext.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("TAG","�������� � imagesId" + imagesIds);
 
    }
 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
    	Log.d("TAG"," getCount() " + imagesIds.size());
        return imagesIds.size();
    }
 
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
    	Log.d("TAG","getItem" +position);
    	// ImageView v = (ImageView) imagesIds.get(position);
        return position;
    }
 
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
    	Log.d("TAG","getItemId" +position);
        return position;
    }

  
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	//View view = convertView;
    	//view = inflater.inflate(R.layout.grid_fill, parent, false);
    	    // ImageView img = (ImageView) view.findViewById(R.id.Image_fill);
           Log.d("TAG","� getView  ��������" + imagesIds.get(position));
         //  Log.d("TAG","img=" + img);
         //  Log.d("TAG","view=" + view);
           // img =(ImageView) imagesIds.get(position);
          // ((ViewGroup) view).addView(imagesIds.get(position));
          // view =imagesIds.get(position); 
        return imagesIds.get(position);
    }
 
}