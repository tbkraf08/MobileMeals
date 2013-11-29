package com.example.mobilemeals;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class FragmentTweetListView extends Fragment {
	ArrayList<Tweet> tweets;
	ListView listView;
	
	public FragmentTweetListView() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.tweets_list_view,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		TweetsAdapter adapter = new TweetsAdapter(getActivity(), tweets);
		listView = (ListView) getActivity().findViewById(R.id.listView2);
		listView.setAdapter(adapter);
	}

	public void setTweets(ArrayList<Tweet> tweets) {
		this.tweets = tweets;
	}

}
