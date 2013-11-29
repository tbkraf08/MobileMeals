package com.example.mobilemeals;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract.Constants;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity implements FragmentTruckListView.OnTruckSelectedListener {
	static public String USER_KEY = "user";
	static public String baseUrl = "http://192.168.1.5:3000";
	static public String postUserUrl = baseUrl + "/users/session";
	static public String signUpUrl = baseUrl + "/signup";


	String username;
	GoogleMap map;

	MapFragment gMap;
	FragmentTruckListView truckListFragment;

	ActionBar.Tab Tab1, Tab2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Check for username
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		username = prefs.getString(USER_KEY, "-1");

		// Create map fragment
		GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		gMap = new MapFragment(){
			@Override
			public void onActivityCreated(Bundle savedInstanceState) {
				// TODO Auto-generated method stub
				super.onActivityCreated(savedInstanceState);
				map = this.getMap();
				setupMap();
			}
		};

		// create truck fragment
		truckListFragment = new FragmentTruckListView();


		ActionBar actionBar = getActionBar();

		// Hide Actionbar Icon
		actionBar.setDisplayShowHomeEnabled(false);

		// Hide Actionbar Title
		actionBar.setDisplayShowTitleEnabled(false);

		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab1 = actionBar.newTab().setText("Trucks");
		Tab2 = actionBar.newTab().setText("Map");


		// Set Tab Listeners
		Tab1.setTabListener(new TabListener(this, "album", FragmentTruckListView.class));
		//Tab2.setTabListener();


		// Add tabs to actionbar
		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);

		/*

		//setup inital fragment
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		//FragmentTruckListView truckListFragment = new FragmentTruckListView();

		fragmentTransaction.add(R.id.main_activity_layout, gMap);
		fragmentTransaction.commit();
		 */

	}

	public void setupMap(){

		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng loc) {
				// TODO Auto-generated method stub
				drawMarkerLatLng(loc);
			}
		});

		map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker loc) {
				// TODO Auto-generated method stub
				return false;
			}
		});


		map.setMyLocationEnabled(true);

		// Getting LocationManager object from System Service LOCATION_SERVICE
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		// Creating a criteria object to retrieve provider
		Criteria criteria = new Criteria();


		// Getting the name of the best provider
		String provider = locationManager.getBestProvider(criteria, true);
		Log.i("Provider", provider);
		// Getting Current Location
		//Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {}

			public void onStatusChanged(String provider, int status, Bundle extras) {}

			public void onProviderEnabled(String provider) {}

			public void onProviderDisabled(String provider) {}
		};
		// Register the listener with the Location Manager to receive location updates
		//locationManager.requestLocationUpdates(provider, 0, 0, locationListener);

		map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.2270869, -80.8431267), 13));

	}

	public void drawMarkerLocation(Location location){
		//map.clear();
		LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

		map.addMarker(new MarkerOptions()
		.draggable(true)
		.position(currentPosition)
		.snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude()));

	}

	public void drawMarkerLatLng(LatLng location){
		map.clear();

		map.addMarker(new MarkerOptions()
		.draggable(true)
		.position(location));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public void onTruckSelected(Truck t) {

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		FragmentTweetListView tweetListView = new FragmentTweetListView();

		tweetListView.setTweets(t.tweets.getTweets());
		fragmentTransaction.replace(R.id.main_activity_layout, tweetListView);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}


}
