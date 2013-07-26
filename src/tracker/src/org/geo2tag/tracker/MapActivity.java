/**
 * 
 */
package org.geo2tag.tracker;

import org.geo2tag.tracker.gui.MapView;
import org.geo2tag.tracker.services.LocationService;
import org.geo2tag.tracker.utils.TrackerUtil;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Mark Zaslavskiy
 *
 */
public class MapActivity extends Activity {
	
	private static final int IDM_REFRESH = 101;
	
	private MapView m_mapView;

	private LocationListener m_locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			Log.d(TrackerUtil.LOG, "MapActivity.onLocationChanged");
			synchronized(this){
				if (!m_mapView.isInited() && location != null){
					initMapWidget(location);

					LocationManager locationManager = 
							(LocationManager) getSystemService(Context.LOCATION_SERVICE);
					locationManager.removeUpdates(m_locationListener);
				}
			}
		}
	};

    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		setTitle(getResources().getText(R.string.map_activity_name));
		
		m_mapView = (MapView) findViewById(R.id.map_view);
		
		requestLocation();
	}
	
	private void initMapWidget(Location location){
		m_mapView.setMapCenter(location.getLatitude(), location.getLongitude());
		m_mapView.tryToInitMapWidget();
	}
	
	
	private void requestLocation() {
		Log.d(TrackerUtil.LOG, "MapActivity.requestLocation");

		if (LocationService.isDeviceReady()){
			Location location = LocationService.getLocation(this);
			initMapWidget(location);
		}else{
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, m_locationListener );
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, m_locationListener );
		}
	}




	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, IDM_REFRESH, Menu.NONE, getString(R.string.refresh_menu_item))
			.setIcon(android.R.drawable.ic_menu_rotate);

		return super.onCreateOptionsMenu(menu);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case IDM_REFRESH:
			m_mapView.refreshMapWidget();
			Log.d(TrackerUtil.LOG, "Refreshing");
			break;

		default:
			
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
