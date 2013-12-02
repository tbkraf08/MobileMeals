package com.example.mobilemeals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class TrucksAdapter extends BaseAdapter{
	private Activity activity;
	private ArrayList<Truck> data;
	private static LayoutInflater inflater=null;


	public TrucksAdapter(Activity a, ArrayList<Truck> d){
		this.activity = a;
		this.data = d;
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		View v = view;
		if (v==null)
			v = inflater.inflate(R.layout.trucks_list_row, null);

		

		Truck t = (Truck) getItem(position);

		
		Log.i("Truck Adapter", t.getLastLocation());
		// let user know there is location
		if (!t.getLastLocation().equals("null"))
			v.setBackgroundColor(Color.CYAN);
		else
			v.setBackgroundColor(Color.WHITE);



		// Load Image
		ImageView avatar = (ImageView) v.findViewById(R.id.imageView1);
		new LoadImageAsync(t.avatar).execute(avatar);

		// Set name
		TextView name = (TextView) v.findViewById(R.id.textView1);
		name.setText(t.name);

		// Set Date
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		TextView date = (TextView) v.findViewById(R.id.textView2);
		date.setText(dt.format(t.lastTweetTime));

		// Set Content
		TextView content = (TextView) v.findViewById(R.id.textView3);
		content.setText(t.tweets.getTweets().get(0).content);

		return v;
	}
}
