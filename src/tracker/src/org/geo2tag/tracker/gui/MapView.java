package org.geo2tag.tracker.gui;


import org.geo2tag.tracker.TrackerActivity;
import org.geo2tag.tracker.preferences.Settings;
import org.geo2tag.tracker.utils.TrackerUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MapView extends WebView {

	private static final String MAP_FILE = "file:///android_asset/map_tracker.html"; 
	private Settings m_settings;
	
	
	@SuppressLint("SetJavaScriptEnabled")
	public MapView(Context context, AttributeSet attrs) {
		super(context, attrs);
				
		m_settings = new Settings(context);
		
		WebSettings webSettings = getSettings();
		webSettings.setJavaScriptEnabled(true);
		setWebChromeClient(new WebChromeClient());
		
		init();
		// TODO Auto-generated constructor stub
	}
	
	private void init(){
		
		loadUrl(MAP_FILE);
		initMapWidget();
	}

	private void initMapWidget(){
		SharedPreferences preferencies = m_settings.getPreferences();
		
		String login =  preferencies.getString(Settings.ITrackerNetSettings.LOGIN, "");
		String password =  preferencies.getString(Settings.ITrackerNetSettings.PASSWORD, "");
		String serverUrl =  preferencies.getString(Settings.ITrackerNetSettings.SERVER_URL, "");
		String dbName =  TrackerUtil.DB_NAME;
		int radius =  preferencies.getInt(Settings.ITrackerNetSettings.RADIUS, 0);
		
		String url = String.format("javascript:initWithSettings(%s, %s, %d, %s, %s);", 
				login, password, radius, dbName, serverUrl);
		Log.d(TrackerActivity.LOG, url);

		loadUrl(url);
	}
	
	public void updateMapWidgetCoordinates(double latitude, double longitude){
		String url = String.format("javascript:updateMapCenter(%f, %f); javascript:refresh();", 
				latitude, longitude);
		Log.d(TrackerActivity.LOG, url);
		loadUrl(url);
	}
}
