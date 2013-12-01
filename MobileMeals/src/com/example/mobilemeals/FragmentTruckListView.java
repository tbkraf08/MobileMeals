package com.example.mobilemeals;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;



import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class FragmentTruckListView extends Fragment implements AdapterView.OnItemClickListener {
	static public String getTweetsUrl = MainActivity.baseUrl + "/tweets";
	Tweets tweets;
	boolean hasTweets = false;
	HashMap<String, Truck> hashMapTrucks;
	boolean hasTrucks = false;

	ArrayList<Truck> trucks;

	ListView listView;
	OnTruckSelectedListener mListener;
	

	public FragmentTruckListView() {
		// Required empty public constructor
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i("Fragment", "Starting Fragment");
		
		//Loads Trucks
		//Then sets up list view
		if (!hasTweets)
			getRequest(getTweetsUrl);
		else
			setupListView();


	}


	public void setupListView(){
		Log.i("Adapter","Setup listview");
		TrucksAdapter adapter = new TrucksAdapter(getActivity(), trucks);
		listView = (ListView) getActivity().findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_fragment_tweet_list_view,
				container, false);
	}

	public void getRequest(String url){
		new GetResponseAsyncTask(){

			@Override
			protected void onPostExecute(JSONObject result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

				if (result.has("tweets")){
					tweets = new Tweets(result);
					hasTweets = true;

					loadTrucks();
				}
			}

		}.execute(url);
		Log.i("Adapter", "Executed Request");
	}



	protected void loadTrucks() {
		Log.i("Adapter","Parsing Trucks");
		if (!hasTweets)
			return;

		if (!hasTrucks){
			hashMapTrucks = new HashMap<String, Truck>();
			hasTrucks = true;	
		}

		for (Tweet t : tweets.getTweets()){
			String truck_name = t.getScreen_name();


			if (!hashMapTrucks.containsKey(truck_name))
				hashMapTrucks.put(truck_name, new Truck(truck_name, tweets.get_id(), tweets.get__v(), tweets.getDateRequested()));

			// seperate tweets based on food truck
			hashMapTrucks.get(truck_name).addTweet(t);
		}

		trucks = new ArrayList<Truck>();

		for (Truck t : hashMapTrucks.values())
			trucks.add(t);
		
		
		mListener.passTrucks(trucks);
		
		Log.i("Fragment", trucks.toString());
		
		//Populate Listview
		setupListView();
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mListener = (OnTruckSelectedListener) activity;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	
		mListener.onTruckSelected(trucks.get(position));
	}

	public interface OnTruckSelectedListener {
		public void onTruckSelected(Truck t);
		public void passTrucks(ArrayList<Truck> trucks);
	}


}
