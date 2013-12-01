package com.example.mobilemeals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class PostRequestJsonAsync extends AsyncTask<JSONObject, Void, JSONObject>{
	String url;
	
	
	public PostRequestJsonAsync(String url){
		this.url = url;
	}
	
	@Override
	protected JSONObject doInBackground(JSONObject... params) {
		JSONObject jsonObject = params[0];
		
		InputStream inputStream = null;
		String result = "";
		try {

		    // 1. create HttpClient
		    HttpClient httpclient = new DefaultHttpClient();

		    // 2. make POST request to the given URL
		    HttpPost httpPost = new HttpPost(url);
		    Log.i("JSON Post url", url);
		   

		    // 4. convert JSONObject to JSON to String
		    String json = jsonObject.toString();

		    // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
		    // ObjectMapper mapper = new ObjectMapper();
		    // json = mapper.writeValueAsString(person); 

		    // 5. set json to StringEntity
		    StringEntity se = new StringEntity(json);

		    // 6. set httpPost Entity
		    httpPost.setEntity(se);

		    // 7. Set some headers to inform server about the type of the content   
		    //httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json");

		    // 8. Execute POST request to the given URL
		    HttpResponse httpResponse = httpclient.execute(httpPost);

		    // 9. receive response as inputStream
		    inputStream = httpResponse.getEntity().getContent();

		    // 10. convert inputstream to string
		    if(inputStream != null){
		    	BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		        String line = "";
		       
		        while((line = bufferedReader.readLine()) != null)
		            result += line;
		 
		        inputStream.close();
		        
		        Log.i("JSON post", result);
		    }else{
		        result = "Did not work!";
		    }

		} catch (Exception e) {
		    Log.d("InputStream", e.getLocalizedMessage());
		}

		
		return null;
	}

}


