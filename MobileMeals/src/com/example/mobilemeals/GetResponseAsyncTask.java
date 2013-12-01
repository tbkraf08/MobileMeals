package com.example.mobilemeals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.util.Log;


public class GetResponseAsyncTask extends AsyncTask<String, Void, JSONObject>{
	

	@Override
	protected JSONObject doInBackground(String... arg0) {
		Log.i("AsyncTask", "Do in background");
		String url = arg0[0];
		
		
		JSONObject returnObj;
		DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpGet getRequest = new HttpGet(url);
		
		getRequest.setHeader("Content-type", "application/json");
		
		
		InputStream inputStream = null;
		String result = null;
		//Log.i("AsyncTask", "Before Request");
		try {
			HttpResponse response = httpclient.execute(getRequest);      
			//Log.i("AsyncTask", "After Request");
			HttpEntity entity = response.getEntity();

			//Log.i("AsyncTask", "got entity");
			
			inputStream = entity.getContent();
			// json is UTF-8 by default
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
			
			result = sb.toString();
			//Log.i("AsyncTask", result);
			returnObj = new JSONObject(result);
			return returnObj;
			
		} catch (Exception e) { 
			e.printStackTrace();
		}
		finally {
			try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
		}

		return null;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
