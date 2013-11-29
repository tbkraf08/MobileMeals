package com.example.mobilemeals;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TweetsAdapter extends BaseAdapter{
	private Activity activity;
	private ArrayList<Tweet> data;
	private static LayoutInflater inflater=null;
	
	
	public TweetsAdapter(Activity activity2, ArrayList<Tweet> tweets) {
		activity = activity2;
		data = tweets;
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		View v = view;
		if (v==null)
			v = inflater.inflate(R.layout.tweets_list_row, null);
		
		Tweet t = (Tweet) getItem(position);
		
		TextView name = (TextView) v.findViewById(R.id.textView3);
		name.setText(t.screen_name);
		
		TextView date = (TextView) v.findViewById(R.id.textView4);
		date.setText(t.content);
		
		return v;
	}

}
