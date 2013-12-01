package com.example.mobilemeals;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class TabListener implements ActionBar.TabListener {
	private Fragment mFragment;

	// Called to create an instance of the listener when adding a new tab
	public TabListener(Fragment fragment) {
		mFragment = fragment;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.add(R.id.main_activity_layout, mFragment, null);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(mFragment);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// do nothing
	}

}