package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.PreferenceUtil;
import gaongil.safereturnhome.support.StaticUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
public class SplashScreen extends Activity {

	private final String TAG = SplashScreen.class.getSimpleName();

	private PreferenceUtil preferenceManager;
	private GoogleCloudMessaging gcm;
    private String regId;

	/** Check if the app is running. */
	private boolean isRunning;

	// You need to do the Play Services APK check here too.
	@Override
	protected void onResume() {
        super.onResume();
        // Check device for Play Services APK.
        checkPlayServices();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
        init();
		checkEssentialInfomation();

	}

	private void checkEssentialInfomation() {
		// Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        // Otherwise, prompt user to get valid Play Services APK.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regId = preferenceManager.getRegistrationId();

            if (regId.isEmpty()) {
                registerInBackground();
            }

            int profileImageSize = preferenceManager.getProfileSize();
            if (profileImageSize == 0) {
            	saveProfileImageWidth();
            }

            isRunning = true;
            startSplash();

        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
	}

	private void saveProfileImageWidth() {
		Display display = getWindowManager().getDefaultDisplay();

        int deviceWidth;

        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB){
            Point size = new Point();
            display.getSize(size);
            deviceWidth = size.x;
        }
        else{
            deviceWidth = display.getWidth();
        }

		preferenceManager.storeProfileSize(deviceWidth / Constant.PROFILE_IMAGE_RATE_BY_DEVICE_WIDTH);
	}

	private void init() {
        preferenceManager = new PreferenceUtil(this);
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
	                    Constant.PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i(TAG, Constant.MESSAGE_PLAY_SERVICES_ERROR);
	            finish();
	        }
	        return false;
	    }
	    return true;
	}

	/**
     * @return Application's version code from the {@code PackageManager}.
     */
    public int getAppVersion() {
        try {
            PackageInfo packageInfo = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }



    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend(final String registerationId) {
    	// Send Data To Server
    	// 서버로 유저 고유값인 registrationID 를 전달한다.
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Constant.NETWORK_PARAM_KEY_REGID, registerationId));

    	String response = null;
		try {
			//response = StaticUtils.postRequest(Constant.NETWORK_URL_REGISTER_ID, nameValuePairs);
		} catch (Exception e) {
			e.printStackTrace();
		}

    	Log.i(TAG, "responseString = "+response);
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(SplashScreen.this);
                    }
                    regId = gcm.register(Constant.PROJECT_ID);
                    msg = "Device registered, registration ID=" + regId;

                    //TODO 네트워크 통신, 휴대전화번호에 해당하는 데이터가 존재할 경우, 해당 regId를 반환

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend(regId);

                    // Persist the regID - no need to register again.

                    //TODO DELETE
                    //common.storeRegistrationId(regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {

            }
        }.execute(null, null, null);
    }

	private void startSplash() {

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					Thread.sleep(Constant.SPLASH_WAIT_TIME);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					runOnUiThread(new Runnable() {
						@Override
						public void run()
						{
							doFinish();
						}
					});
				}
			}
		}).start();
	}

	/**
	 * If the app is still running than this method will start the Login activity
	 * and finish the Splash.
	 */
	private synchronized void doFinish() {

		if (isRunning) {
			isRunning = false;
			Intent i = new Intent(SplashScreen.this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isRunning = false;
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
