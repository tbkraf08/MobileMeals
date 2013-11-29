package com.example.mobilemeals;


import java.io.Serializable;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Serializable {
	String _id;
	Date datePosted;
	String geo;
	String content;
	String screen_name;
	
	
	
	public Tweet(JSONObject obj){
		try {
			_id = obj.getString("_id");
			geo = obj.getString("geo");
			content = obj.getString("content");
			screen_name = obj.getString("screen_name");
					
			datePosted = Tweets.convertDate(obj.getString("datePosted"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}



	public String get_id() {
		return _id;
	}



	public void set_id(String _id) {
		this._id = _id;
	}



	public Date getDatePosted() {
		return datePosted;
	}



	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}



	public String getGeo() {
		return geo;
	}



	public void setGeo(String geo) {
		this.geo = geo;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getScreen_name() {
		return screen_name;
	}



	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}



	@Override
	public String toString() {
		return "Tweet [_id=" + _id + ", datePosted=" + datePosted + ", geo="
				+ geo + ", content=" + content + ", screen_name=" + screen_name
				+ "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((datePosted == null) ? 0 : datePosted.hashCode());
		result = prime * result + ((geo == null) ? 0 : geo.hashCode());
		result = prime * result
				+ ((screen_name == null) ? 0 : screen_name.hashCode());
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
		Tweet other = (Tweet) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (datePosted == null) {
			if (other.datePosted != null)
				return false;
		} else if (!datePosted.equals(other.datePosted))
			return false;
		if (geo == null) {
			if (other.geo != null)
				return false;
		} else if (!geo.equals(other.geo))
			return false;
		if (screen_name == null) {
			if (other.screen_name != null)
				return false;
		} else if (!screen_name.equals(other.screen_name))
			return false;
		return true;
	}

   
}
