package com.example.mobilemeals;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserLogin extends Activity {
	EditText username;
	EditText password;
	EditText email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);
		username = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		email = (EditText) findViewById(R.id.editText3);
		
		Button submit = (Button) findViewById(R.id.button1);	
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (username.getText().toString().length() == 0){
					username.setError("Need Username");
					return;
				}
				if (password.getText().toString().length() == 0){
					password.setError("Need Password");
					return;
				}
				if (email.getText().toString().length() == 0){
					email.setError("Need Email");
					return;
				}
				
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				Editor editor = prefs.edit();
				
				editor.putString(MainActivity.USER_KEY, username.getText().toString());
				editor.commit();
				
				
				// Request parameters and other properties.
				List<NameValuePair> params = new ArrayList<NameValuePair>(3);
				params.add(new BasicNameValuePair("username", username.getText().toString()));
				params.add(new BasicNameValuePair("password", password.getText().toString()));
				params.add(new BasicNameValuePair("email", email.getText().toString()));
				
				new PostRequestAsync(MainActivity.signUpUrl).execute(params);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_login, menu);
		return true;
	}

}
