package ru.aleksandrov.news;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONParser {
	
	static JSONArray response = null;

	public static JSONArray parseUrlDataToArray(String dataFromUrl) {

		try {
			JSONObject jObj = new JSONObject(dataFromUrl);
			return jObj.getJSONArray("response");
		} catch (JSONException e) {
			Thread.currentThread().interrupted();
		}
		return response;

	}

}
