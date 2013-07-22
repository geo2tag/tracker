/**
 * 
 */
package org.geo2tag.tracker;

import org.geo2tag.tracker.gui.MapView;
import org.geo2tag.tracker.services.LocationService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;

/**
 * @author Mark Zaslavskiy
 *
 */
public class MapActivity extends Activity {
	
	private MapView m_mapView;
    private BroadcastReceiver m_locationReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			final Location location = (Location)intent.getParcelableExtra(LocationService.TYPE_LOCATION_OBJ);
			m_mapView.updateMapWidgetCoordinates(location.getLatitude(), location.getLongitude());
		}
	};
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		setTitle(getResources().getText(R.string.map_activity_name));
		
		registerReceiver(m_locationReceiver, new IntentFilter(LocationService.ACTION_LOCATION));
		
		m_mapView = (MapView) findViewById(R.id.map_view);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		unregisterReceiver(m_locationReceiver);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
}
