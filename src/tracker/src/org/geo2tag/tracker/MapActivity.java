/**
 * 
 */
package org.geo2tag.tracker;

import org.geo2tag.tracker.utils.TrackerUtil;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Mark Zaslavskiy
 *
 */
public class MapActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
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
}
