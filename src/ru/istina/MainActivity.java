package ru.aleksandrov.news;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Purchase;
import com.google.gson.Gson;

public class MainActivity extends Activity {
	//All activity is good work
	
	private static final String TAG = null;
	static final String SKU_DONATE_1 = "donate_1_usd";
	static final int REQUEST_CODE = 505;
    private	IabHelper mHelper;
	Gson gson = new Gson();
	private Wall myWall = new Wall();
	private PostListAdapter postListAdapter = null;
	private SharedPreferences spPosts;
	final String LOG_TAG = "myLogs";
	private Context context = this;
	private ListView lvPosts;
	private String id_position;
	private int fullText;	

	private final String URL_STRING = "https://api.vk.com/method/wall.get?owner_id=-10199589";
	private int offset = 0;
	private int count = 10;
public int width;
public int height;
Button mDonateBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
		
		mDonateBtn = (Button) findViewById(R.id.mDonateBtn);
	    String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiL6I+LmupIy+1195WhBXgtvy+XHkX1h12qNNH7rU5MzdTx+ak6psr1XMg+pqRBxdkWHpJ0lexR/q/YMwmnUmDLkcn4JX0tyOvO+S3rhVBSEoLPYKVCuL3oK/UlVKunrE8YJYfsz8nURU9nuGYIaINowPLqY+0MOhUS/jk+gOA0lsotx7Cm1oMmjFj01DGp0227+GmV28+cFmeW8ptJCId9xzLxZURFous+aZsmal2rUxvuKk238/OHNba74ucwVe9/t7BfbgK5QC8bNSu9aMpXIC3LeVk4GQZadKYmRtWD0+yzM9N2KHdYIukaqC23abGjBCmJwcqWpq+6uTOQA7DQIDAQAB";
		mHelper = new IabHelper(this, base64EncodedPublicKey);
		mHelper.enableDebugLogging(false);
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			 
	            public void onIabSetupFinished(IabResult result) {
	                if (!result.isSuccess()) {
	                    // ��������� ������ ����������� ����������, �������� ������ �� ������������
	                	mDonateBtn.setVisibility(View.INVISIBLE);
	                    return;
	                }
	            }
	        });
		
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width=metrics.widthPixels;
		height=metrics.heightPixels;	
		fullText= height*width;
		lvPosts = (ListView) findViewById(R.id.lvPosts);
		spPosts = getSharedPreferences("Posts", MODE_PRIVATE);
	
		
		 
	    if (HttpConnector.checkInternet(this) != true) {
			Map<String, ?> posts = spPosts.getAll();
			if (posts.size() == 0) {
				Toast.makeText(this, "���� ����������\n", Toast.LENGTH_LONG)
						.show();
				finish();
			}
			for (String key : posts.keySet()) {
				Post post = gson
						.fromJson(posts.get(key).toString(), Post.class);
				myWall.addPost(post);
			}
			ListAdapter postListAdapter = new PostListAdapter(context,
					(ArrayList<Post>) myWall.getPosts(),fullText);
			lvPosts.setAdapter(postListAdapter);
			Toast.makeText(this, "���� ����������\n������� �����",
					Toast.LENGTH_LONG).show();
			setPostsClickListener();
			
		} else {
			spPosts.edit().clear();
			File dir = this.context.getFileStreamPath("");
			if (dir.isDirectory()) {
				String[] children = dir.list();
				for (String child : children) {
					File file = new File(dir, child);
					file.delete();
				}
			}
			new TaskLoadWall().execute(URL_STRING, "" + count, "" + offset);
		}
	}

	
	@Override
	protected void onDestroy() {
		
		if (mHelper != null) mHelper.dispose();
		mHelper = null;   
	       
		super.onDestroy();
	
	}
	
	
	public void onActivityResult(int requestCode,int resultCode,Intent data) {
		
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
		
	
	}
	

	private class TaskLoadWall extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			count = Integer.parseInt(params[1]);
			offset = Integer.parseInt(params[2]);
			String urlString = params[0] + "&count=" + count + "&offset="
					+ offset;
			try {
				return HttpConnector.getStringFromIS(HttpConnector
						.getISFromUrl(urlString));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}
			return null;
		}

		public void onPreExecute() {
				
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.loadprogresst,null,true);
			Toast toast = new Toast(getApplicationContext()); 
			toast.setGravity(Gravity.CENTER_HORIZONTAL, 0,0); 
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(view); 
			toast.show(); 
		}

		@Override
		protected void onPostExecute(String result) {
			
			buildWall(result);
			setAdapter();
			setPostsClickListener();
			setPostScrollListener();
		}
	}
	
  
	
	private void setPostsClickListener() {
		lvPosts.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			
			
			
			{
				int postId = (Integer) view.getTag();
				Intent intent = new Intent(getApplicationContext(),PostDetailActivity.class);
				intent.putExtra("postId", "" + postId);
				intent.putExtra("Size",fullText);
				startActivity(intent);
			}
		});
	}

	
	private void setPostScrollListener() {
		lvPosts.setOnScrollListener(new OnScrollListener() {

			private int visibleItemCount;
			private int firstVisibleItem;
			private int totalItemCount;
			private int currentScrollState;
			private boolean isScrollCompleted() {
				if (((this.visibleItemCount + this.firstVisibleItem) == this.totalItemCount)
						&& this.currentScrollState == SCROLL_STATE_IDLE) {
					return true;
				}
				return false;
			}

			@Override
			public void onScroll(AbsListView view, int _firstVisibleItem,
					int _visibleItemCount, int _totalItemCount) {
				this.visibleItemCount = _visibleItemCount;
				this.firstVisibleItem = _firstVisibleItem;
				this.totalItemCount = _totalItemCount;
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				this.currentScrollState = scrollState;
				if (this.isScrollCompleted()) {
					int total = myWall.getPosts().size() + count;
					if (total < 100) {
						TaskLoadWall tlw = new TaskLoadWall();
						tlw.execute(URL_STRING, "" + count, ""
								+ myWall.getPosts().size());
					}
				}
			}

		});
	}

	private void buildWall(String jsonString) {
		try {
			JSONArray response = JSONParser.parseUrlDataToArray(jsonString);
			for (int i = 1; i < response.length(); i++) {
				String jsonPost = response.getJSONObject(i).toString();
				Post post = gson.fromJson(jsonPost, Post.class);				
				Editor ed = spPosts.edit();
				ed.putString("postId" + post.getId(), gson.toJson(post));
				ed.commit();
				myWall.addPost(post);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Thread.currentThread().interrupt();
		}

	}

	private void setAdapter() {
		if (postListAdapter == null) {
			postListAdapter = new PostListAdapter(context,
					(ArrayList<Post>) myWall.getPosts(),fullText);
			lvPosts.setAdapter(postListAdapter);
			
			
		} else {
			((BaseAdapter) postListAdapter).notifyDataSetChanged();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

private final static class ViewHolder {
    public String id;
    public ImageView picture;
}


public void mDonateBtn(View view) {
	
	 mHelper.launchPurchaseFlow(this, SKU_DONATE_1, REQUEST_CODE, mPurchaseFinishedListener);	
  }

IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
        if (result.isFailure()) {
            // ��������� ������������ ������ �������
            return;
        }

        if (purchase.getSku().equals(SKU_DONATE_1)) {
            mHelper.consumeAsync(purchase, null);
            // ������� ������������ ������� �� ������������ �������
        }
    }
};

}