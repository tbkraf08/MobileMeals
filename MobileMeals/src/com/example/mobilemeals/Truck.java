package com.example.mobilemeals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;

public class Truck implements Serializable{
	String name;
	ArrayList<String> locations;
	Tweets tweets;
	Date lastTweetTime;
	String lastLocation;
	String avatar;
	Bitmap img = null;
	
	public Truck(String name, String _id, String __v, Date dateRequested){
		this.name = name;
		locations = new ArrayList<String>();
		tweets = new Tweets(_id, __v, dateRequested);
		
		lastTweetTime = null;
		lastLocation = null;
		avatar = null;
	}
	
	public void addTweet(Tweet t){
		if (t.getGeo() != null){
			locations.add(t.getGeo());
			if (lastLocation == null)
				lastLocation = t.getGeo();
		}
		if (lastTweetTime == null)
			lastTweetTime = t.getDatePosted();
		
		if (avatar == null)
			avatar = t.getAvatar();
		
		tweets.addTweet(t);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<String> locations) {
		this.locations = locations;
	}

	public Tweets getTweets() {
		return tweets;
	}

	public void setTweets(Tweets tweets) {
		this.tweets = tweets;
	}

	public Date getLastTweetTime() {
		return lastTweetTime;
	}

	public void setLastTweetTime(Date lastTweetTime) {
		this.lastTweetTime = lastTweetTime;
	}

	public String getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(String lastLocation) {
		this.lastLocation = lastLocation;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

	

	
	
	
	
}
