package com.example.mobilemeals;



import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class SignUpWebView extends Activity {

	static public String KEY_URL = "URL";
	WebView browser;
	String url;
	
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_web_view);
		
		if(getIntent().getExtras() != null){
			url = getIntent().getExtras().getString(KEY_URL);
		}
		
		browser = (WebView) findViewById(R.id.webView1);
		WebSettings webSettings = browser.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		browser.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				if (url.equals(MainActivity.signUpUrl)){
					view.loadUrl(url);
				}else{
					finish();
				}
				return true;
				//return super.shouldOverrideUrlLoading(view, url);
			}
			
		});
		
		
		
		
		browser.loadUrl(url);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up_web_view, menu);
		return true;
	}

}
