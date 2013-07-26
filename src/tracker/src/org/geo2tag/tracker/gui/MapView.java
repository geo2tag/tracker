package org.geo2tag.tracker.gui;

import java.util.Locale;

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
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MapView extends WebView {

	private int m_mapWidgetInitCounter = 0;
	
	private static final String MAP_FILE = "file:///android_asset/map_tracker.html"; 
	private static final String REFRESH_URL = "javascript:refresh();"; 
	
	private Settings m_settings;
	private double m_latitude;
	private double m_longitude;
	private boolean m_inited = false;
	
	
	@SuppressLint("SetJavaScriptEnabled")
	public MapView(Context context, AttributeSet attrs) {
		super(context, attrs);
				
		m_settings = new Settings(context);
		
		WebSettings webSettings = getSettings();
		webSettings.setJavaScriptEnabled(true);
		setWebChromeClient(new WebChromeClient());
		
		setWebViewClient(new WebViewClient() {
		    public void onPageFinished(WebView view, String url) {
		    	if (url.equals(MAP_FILE))
		    		
		    		tryToInitMapWidget();
		    }
		});
		Toast.makeText(getContext(),"Determining current location", Toast.LENGTH_LONG).show();
		
		loadUrl(MAP_FILE);
		//init();
	}
	
	public synchronized void tryToInitMapWidget(){
		if (m_mapWidgetInitCounter == 1)
			initMapWidget();
		else
			m_mapWidgetInitCounter++;
	}
	
	
	
	public boolean isInited(){
		return m_inited;
	}
	
	
	public void setMapCenter(double latitude, double longitude){
		m_inited = true;
		m_latitude = latitude;
		m_longitude = longitude;
		
		
	}

	private void initMapWidget(){
		SharedPreferences preferencies = m_settings.getPreferences();
		
		String login =  preferencies.getString(Settings.ITrackerNetSettings.LOGIN, "");
		String password =  preferencies.getString(Settings.ITrackerNetSettings.PASSWORD, "");
		String serverUrl =  preferencies.getString(Settings.ITrackerNetSettings.SERVER_URL, "");
		String dbName =  TrackerUtil.DB_NAME;
		int radius =  preferencies.getInt(Settings.ITrackerNetSettings.RADIUS, 0);
		
		String url = String.format(Locale.US,"javascript:initWithSettings('%s', '%s', %d, '%s', '%s', %.10f, %.10f);", 
				login, password, radius, dbName, serverUrl, m_latitude, m_longitude);
		Log.d(TrackerUtil.LOG, url);

		loadUrl(url);
	}
	
	public void updateMapWidgetCoordinates(double latitude, double longitude){
		String url = String.format("javascript:updateMapCenter(%f, %f);", 
				latitude, longitude);
		Log.d(TrackerUtil.LOG, url);
		loadUrl(url);
	}
	
	public void refreshMapWidget(){
		loadUrl(REFRESH_URL);
	}
	

}
