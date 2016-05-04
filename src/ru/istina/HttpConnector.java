package ru.aleksandrov.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpConnector  {
	
	public static InputStream getISFromUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        int response = conn.getResponseCode();
        return conn.getInputStream();
	}

	public static String getStringFromIS(InputStream is)
			throws UnsupportedEncodingException, IOException {
		 StringBuilder inputStringBuilder = new StringBuilder();
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String line = bufferedReader.readLine();
	        while(line != null){
	            inputStringBuilder.append(line);inputStringBuilder.append('\n');
	            line = bufferedReader.readLine();
	        }
	        return inputStringBuilder.toString();
	}

	public static boolean checkInternet(Context context) {
		ConnectivityManager myCM = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = myCM.getActiveNetworkInfo();
		if (i == null)
			return false;
		if (!i.isConnected())
			return false;
		if (!i.isAvailable())
			return false;
		return true;
	}

	
}
