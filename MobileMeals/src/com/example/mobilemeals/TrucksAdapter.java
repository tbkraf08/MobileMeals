package com.example.mobilemeals;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
		
		TextView name = (TextView) v.findViewById(R.id.textView1);
		name.setText(t.name);
		
		TextView date = (TextView) v.findViewById(R.id.textView2);
		date.setText(t.lastTweetTime.toString());
		
		TextView content = (TextView) v.findViewById(R.id.textView3);
		content.setText(t.tweets.getTweets().get(0).content);
		
		return v;
	}
}
