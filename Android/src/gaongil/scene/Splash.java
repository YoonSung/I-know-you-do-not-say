package gaongil.scene;

import gaongil.safereturnhome.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
public class Splash extends Activity {

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	// You need to do the Play Services APK check here too.
	@Override
	protected void onResume() {
	    super.onResume();
	    checkPlayServices();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		// Check device for Play Services APK.
		if (checkPlayServices()) {
			// If this check succeeds, proceed with normal processing.
			// Otherwise, prompt user to get valid Play Services APK.
		    //    ...
		}
	}
	
	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i("TEST", "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
}
