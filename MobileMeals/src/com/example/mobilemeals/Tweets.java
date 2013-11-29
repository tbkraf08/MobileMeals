package com.example.mobilemeals;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweets implements Serializable{
	String _id;
	String __v;
	ArrayList<Tweet> tweets;
	Date dateRequested;
	
	
	public Tweets(String _id, String __v, Date date){
		this._id = _id;
		this.__v = __v;
		tweets = new ArrayList<Tweet>();
		dateRequested = date;
	}
	
	public Tweets(JSONObject obj){
		
		
		try {
			_id = obj.getString("_id");
			__v = "";//obj.getString("__v");
			JSONArray temp_tweets = obj.getJSONArray("tweets");
			
			tweets = new ArrayList<Tweet>();
			for (int i=0; i<temp_tweets.length(); i++){
				JSONObject tweet_obj = (JSONObject) temp_tweets.get(i);
				tweets.add(new Tweet(tweet_obj));
			}
			
			dateRequested = Tweets.convertDate(obj.getString("dateRequested"));
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Date convertDate(String temp_date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			return sdf.parse(temp_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void addTweet(Tweet tweet){
		tweets.add(tweet);
	}


	public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}


	public String get__v() {
		return __v;
	}


	public void set__v(String __v) {
		this.__v = __v;
	}


	public ArrayList<Tweet> getTweets() {
		return tweets;
	}


	public void setTweets(ArrayList<Tweet> tweets) {
		this.tweets = tweets;
	}


	public Date getDateRequested() {
		return dateRequested;
	}


	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}


	@Override
	public String toString() {
		return "Tweets [_id=" + _id + ", __v=" + __v + ", tweets=" + tweets
				+ ", dateRequested=" + dateRequested + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((__v == null) ? 0 : __v.hashCode());
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result
				+ ((dateRequested == null) ? 0 : dateRequested.hashCode());
		result = prime * result + ((tweets == null) ? 0 : tweets.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweets other = (Tweets) obj;
		if (__v == null) {
			if (other.__v != null)
				return false;
		} else if (!__v.equals(other.__v))
			return false;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		if (dateRequested == null) {
			if (other.dateRequested != null)
				return false;
		} else if (!dateRequested.equals(other.dateRequested))
			return false;
		if (tweets == null) {
			if (other.tweets != null)
				return false;
		} else if (!tweets.equals(other.tweets))
			return false;
		return true;
	}
	
}
