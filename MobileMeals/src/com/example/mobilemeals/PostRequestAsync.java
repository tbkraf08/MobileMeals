package com.example.mobilemeals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class PostRequestAsync extends AsyncTask<List<NameValuePair>, Void, JSONObject>{
	String url;
	
	public PostRequestAsync(String url){	
		this.url = url;
	}
	@Override
	protected JSONObject doInBackground(List<NameValuePair>... arg0) {
		Log.i("Post", "Starting post");
		List<NameValuePair> params = arg0[0];
		
		InputStream inputStream = null;
		String result = null;
		JSONObject returnObj;
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			//Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				inputStream = entity.getContent();
			    try {
			    	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();

					String line = null;
					while ((line = reader.readLine()) != null)
					{
						sb.append(line + "\n");
					}
					
					result = sb.toString();
					Log.i("Post", result);
					returnObj = new JSONObject(result);
					return returnObj;
			    } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
			    	inputStream.close();
			    }
			    
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return null;
	}
	@Override
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
