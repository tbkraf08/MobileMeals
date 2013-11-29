package com.example.mobilemeals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Truck implements Serializable{
	String name;
	ArrayList<String> locations;
	Tweets tweets;
	Date lastTweetTime;
	String lastLocation;
	
	public Truck(String name, String _id, String __v, Date dateRequested){
		this.name = name;
		locations = new ArrayList<String>();
		tweets = new Tweets(_id, __v, dateRequested);
		
		lastTweetTime = null;
		lastLocation = null;
	}

	public void addTweet(Tweet t){
		if (t.getGeo() != null){
			locations.add(t.getGeo());
			if (lastLocation == null)
				lastLocation = t.getGeo();
		}
		if (lastTweetTime == null)
			lastTweetTime = t.getDatePosted();
		
		tweets.addTweet(t);
	}
	
	
	
}
