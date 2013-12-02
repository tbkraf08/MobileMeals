package com.example.mobilemeals;


import java.util.ArrayList;


import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class MainActivity extends Activity implements FragmentTruckListView.OnTruckSelectedListener, FragmentMap.OnMapListener {
	static public String MAP_TAG = "Map";
	static public String TRUCK_TAG = "Trucks";
	static public String USER_KEY = "username";
	static public String baseUrl = "http://192.168.1.3:3000";
	static public String postUserUrl = baseUrl + "/users/session";
	static public String signUpUrl = baseUrl + "/users";
	static public String truckUrl = baseUrl + "/truck";
	static public String trucksUrl = baseUrl + "/trucks";

	ArrayList<Truck> trucks;
	String username;
	ActionBar.Tab Tab1, Tab2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Check for username
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		username = prefs.getString(USER_KEY, "-1");
		
		if (username == "-1"){
			Intent intent = new Intent(this, UserLogin.class);
			startActivity(intent);
		}

		ActionBar actionBar = getActionBar();

		// Hide Actionbar Icon
		actionBar.setDisplayShowHomeEnabled(false);

		// Hide Actionbar Title
		actionBar.setDisplayShowTitleEnabled(false);

		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		
		TabListener<FragmentTruckListView> truckTabListener = 
				new TabListener<FragmentTruckListView>(this, TRUCK_TAG, FragmentTruckListView.class);
		
		TabListener<FragmentMap> mapTabListener = 
				new TabListener<FragmentMap>(this, MAP_TAG, FragmentMap.class);

		// Create tab
		Tab1 = actionBar.newTab();
		Tab2 = actionBar.newTab();

		// Set text
		Tab1.setText("Trucks");
		Tab2.setText("Map");

		// Set Tab Listeners
		Tab1.setTabListener(truckTabListener);
		Tab2.setTabListener(mapTabListener);

		// Add tabs to actionbar
		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public void onTruckSelected(Truck t) {
		String[] content = t.getTweets().getTweets().get(0).getContent().split(" ");
		for(int i = 0; i<content.length; i++){
			if (content[i].startsWith("http")){
				String url = content[i].trim();
				
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);
			}
		}
		//FragmentManager fragmentManager = getFragmentManager();
		//FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		//FragmentTweetListView tweetListView = new FragmentTweetListView();
		
		//tweetListView.setTweets(t.tweets.getTweets());
	
		//fragmentManager.
		//fragmentTransaction.replace(R.id.main_activity_layout, tweetListView);
	//	fragmentTransaction.addToBackStack(null);
		//fragmentTransaction.commit();
		
	}

	@Override
	public void passTrucks(ArrayList<Truck> trucks) {
		this.trucks = trucks;
	}
	
	@Override
	public void mapInitialized() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentMap map = (FragmentMap) fragmentManager.findFragmentByTag(MAP_TAG);
		map.setTrucks(trucks);
	
	}


	
	private class TabListener<T extends Fragment> implements ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		/**
		 * Constructor used each time a new tab is created.
		 * 
		 * @param activity
		 *            The host Activity, used to instantiate the fragment
		 * @param tag
		 *            The identifier tag for the fragment
		 * @param clz
		 *            The fragment's Class, used to instantiate the fragment
		 */
		public TabListener(Activity activity, String tag, Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Check if the fragment is already initialized
			if (mFragment == null) {
				// If not, instantiate and add it to the activity
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(R.id.main_activity_layout, mFragment, mTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(mFragment);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
				// Detach the fragment, because another one is being attached
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			
		}
	}

}
