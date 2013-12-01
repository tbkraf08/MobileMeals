package com.example.mobilemeals;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class LoadBitmapAsync extends AsyncTask<Bitmap, Void, Bitmap>{
	String url_str;
	Bitmap img;

	public LoadBitmapAsync(String url) {
		// TODO Auto-generated constructor stub
		this.url_str = url;
	}

	
	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		img = result;
	}


	@Override
	protected Bitmap doInBackground(Bitmap... params) {
		// TODO Auto-generated method stub
		img = params[0];

		try {

			URL url = new URL(url_str);

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			
	
			
			return myBitmap;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}