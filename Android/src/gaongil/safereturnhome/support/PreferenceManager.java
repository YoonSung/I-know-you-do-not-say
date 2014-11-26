package gaongil.safereturnhome.support;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferenceManager extends Activity{
	
	private static final String TAG = PreferenceManager.class.getSimpleName();
	private Context context;
	
	public PreferenceManager(Context context) {
		this.context = context;
	}
	
    private SharedPreferences getPreferences() {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return context.getSharedPreferences(PreferenceManager.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    
    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    public void storeRegistrationId(String regId) {
        final SharedPreferences prefs = getPreferences();
        int appVersion = StaticUtils.getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constant.PREFERENCE_KEY_REG_ID, regId);
        editor.putInt(Constant.PREFERENCE_KEY_APP_VERSION, appVersion);
        editor.commit();
    }
    
    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    public String getRegistrationId() {
        final SharedPreferences prefs = getPreferences();
        String registrationId = prefs.getString(Constant.PREFERENCE_KEY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(Constant.PREFERENCE_KEY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = StaticUtils.getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

}
