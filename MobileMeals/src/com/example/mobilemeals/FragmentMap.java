package com.example.mobilemeals;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class FragmentMap extends Fragment {
	GoogleMap map;
	View view;
	HashMap<String, Truck> trucks_map;
	ArrayList<Truck> trucks;
	OnMapListener mListener;
	Button setLocationButton;
	Spinner spinner;
	Bitmap selectedTruckImg;
	LatLng curTruckLocation;

	public FragmentMap() {
		// Required empty public constructor
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		trucks_map = new HashMap<String, Truck>();
		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		selectedTruckImg = Bitmap.createBitmap(50, 50, conf);
		curTruckLocation = null;


		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		setLocationButton = (Button) getActivity().findViewById(R.id.button2);
		spinner = (Spinner) getActivity().findViewById(R.id.spinner1);

		setButtonListener();
		setSpinnerListener();

		GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getApplicationContext());
		setupMap();
		getCurrentLocation();
		mListener.mapInitialized();
		getUserTaggedTrucks();
		//zoomOnCharlotte();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mListener = (OnMapListener) activity;
	}

	public void setSpinnerListener(){

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				String truck_name = (String) parent.getItemAtPosition(pos);
				String url = trucks_map.get(truck_name).avatar;

				// set the marker icon to the avatar of selected truck
				new LoadBitmapAsync(url){

					@Override
					protected void onPostExecute(Bitmap result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						setTruckBitmap(result);
					}

				}.execute(selectedTruckImg);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				String truck_name = (String) arg0.getItemAtPosition(0);
				String url = trucks_map.get(truck_name).avatar;

				// set the marker icon to the avatar of selected truck
				new LoadBitmapAsync(url).execute(selectedTruckImg);

			}
		});
	}

	public void setButtonListener(){
		setLocationButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (curTruckLocation != null){
					String truckName = (String) spinner.getSelectedItem();
					Log.i("Set Location", truckName);
					Log.i("Set Location", curTruckLocation.toString());


					JSONObject obj = new JSONObject();
					try {
						obj.put("id",trucks_map.get(truckName).tweets.getTweets().get(0)._id);
						obj.put("location", Double.valueOf(curTruckLocation.longitude)+","+Double.valueOf(curTruckLocation.latitude));
						obj.put("truckname", truckName);
						Log.i("JSON obj", obj.toString());
						new PostRequestJsonAsync(MainActivity.truckUrl).execute(obj);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
			}
		});
	}

	public void setTruckBitmap(Bitmap img){
		selectedTruckImg = img;
	}

	public void setTrucks(ArrayList<Truck> trucks){
		this.trucks = trucks;

		for (Truck t : trucks)
			trucks_map.put(t.name, t);

		addTruckMarkers();
		populateSpinner();
	}

	public void populateSpinner(){
		List<String> list = new ArrayList<String>();
		for (Truck t : trucks)
			list.add(t.name);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}

	public void addTruckMarkers(){
		for (Truck t : trucks){

			if (!t.lastLocation.equals("null")){
				new LoadTruckImageAsync().execute(t);
				Log.i("TRUCK GEO", t.name+" "+t.lastLocation);
			}else{
				Log.i("TRUCK GEO NULL", t.name);
			}
		}
	}

	public void  getUserTaggedTrucks(){
		new GetResponseJSONAsync().execute(MainActivity.trucksUrl);
	}

	public void parseTrucks(JSONArray obj){
		//Log.i("JSON OBject", obj.toString());
		for (int i=0;i<obj.length(); i++){
			JSONObject jsonObj;
			try {
				jsonObj = obj.getJSONObject(i);
				String name = jsonObj.getString("truckName");
				String _id = jsonObj.getString("_id");
				String __v = jsonObj.getString("__v");
				Date date = Tweets.convertDate(jsonObj.getString("date"));
				String location = jsonObj.getString("location");
				
				Truck t = new Truck(name, _id, __v, date);
				t.setLastLocation(location);
				
				Log.i("Loaded Truck", name);
				Log.i("Loaded Truck loc", location);
				
				if (trucks_map.containsKey(name)){
					t.setAvatar(trucks_map.get(name).getAvatar());
					new LoadTruckImageAsync().execute(t);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//Truck(String name, String _id, String __v, Date dateRequested)
		}
	}

	public void setupMap(){
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng loc) {
				drawMarkerLatLng(loc);
			}
		});



		map.setMyLocationEnabled(true);


	}

	public void getCurrentLocation(){
		// Getting LocationManager object from System Service LOCATION_SERVICE
		LocationManager locationManager = (LocationManager) getActivity().getSystemService(MainActivity.LOCATION_SERVICE);

		// Creating a criteria object to retrieve provider
		Criteria criteria = new Criteria();

		// Getting the name of the best provider
		String provider = locationManager.getBestProvider(criteria, true);
		Log.i("Provider", provider);
		// Getting Current Location
		//Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				//drawMarkerLocation(location);
				if (location != null)
					map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
				((LocationManager) getActivity().getSystemService(MainActivity.LOCATION_SERVICE)).removeUpdates(this);
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {}

			public void onProviderEnabled(String provider) {}

			public void onProviderDisabled(String provider) {}
		};
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
	}

	public void markTruck(Truck t){
		String[] loc = t.lastLocation.split(",");
		Log.i("Marking truck", loc.toString());

		if (loc.length >= 2){
			Double lat = Double.valueOf(loc[0]);
			Double lng = Double.valueOf(loc[1]);
			LatLng pos = new LatLng(lat, lng);
			Log.i("Marking truck geo", pos.toString());

			map.addMarker(new MarkerOptions()
			.title(t.name)
			.position(pos)
			.icon(BitmapDescriptorFactory.fromBitmap(t.img)));
			Log.i("MARKED TRUCK","work now");
		}
	}



	public void drawMarkerLocation(Location location){
		LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

		map.addMarker(new MarkerOptions()
		.draggable(true)
		.position(currentPosition)
		.snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude()));

	}

	public void drawMarkerLatLng(LatLng location){
		//map.clear();

		curTruckLocation = location;
		map.addMarker(new MarkerOptions()
		.icon(BitmapDescriptorFactory.fromBitmap(selectedTruckImg))
		.position(location));
	}

	public void zoomOnCharlotte(){
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.2270869, -80.8431267), 13));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		if (view == null)
			view = inflater.inflate(R.layout.fragment_fragment_map, container,false);

		return view;
	}



	public interface OnMapListener {
		public void mapInitialized();
	}

	private class LoadTruckImageAsync extends AsyncTask<Truck,Void,Truck>{

		@Override
		protected void onPostExecute(Truck result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			markTruck(result);
		}

		@Override
		protected Truck doInBackground(Truck... params) {
			Truck t = params[0];

			URL url;
			try {
				url = new URL(t.avatar);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);
				t.img = myBitmap;


				return t;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return null;
		}

	}

	private class GetResponseJSONAsync extends AsyncTask<String,Void,JSONArray>{

		
		
		
		@Override
		protected void onPostExecute(JSONArray result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			parseTrucks(result);
		}
		@Override
		protected JSONArray doInBackground(String... params) {
			Log.i("AsyncTask", "Do in background");
			String url = params[0];
			
			
			JSONArray returnObj;
			DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
			HttpGet getRequest = new HttpGet(url);
			
			getRequest.setHeader("Content-type", "application/json");
			
			
			InputStream inputStream = null;
			String result = null;
			//Log.i("AsyncTask", "Before Request");
			try {
				HttpResponse response = httpclient.execute(getRequest);      
				//Log.i("AsyncTask", "After Request");
				HttpEntity entity = response.getEntity();

				//Log.i("AsyncTask", "got entity");
				
				inputStream = entity.getContent();
				// json is UTF-8 by default
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();

				String line = null;
				while ((line = reader.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				
				result = sb.toString();
				//Log.i("AsyncTask", result);
				returnObj = new JSONArray(result);
				return returnObj;
				
			} catch (Exception e) { 
				e.printStackTrace();
			}
			finally {
				try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
			}

			return null;
		}



	}

}
