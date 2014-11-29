package gaongil.safereturnhome.support;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferenceUtil extends Activity{
	
	private static final String TAG = PreferenceUtil.class.getSimpleName();
	private Context context;
	private SharedPreferences spf;
	private SharedPreferences.Editor editor;
	
	public PreferenceUtil(Context context) {
		this.context = context;
		spf = context.getSharedPreferences(PreferenceUtil.class.getSimpleName(),  Context.MODE_PRIVATE);
		editor = spf.edit();
	}
	
    // Stores the registration ID and the app versionCode in the application's
    public void storeRegistrationId(String regId) {
        int appVersion = StaticUtils.getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        
        savePreference(Constant.PREFERENCE_KEY_REG_ID, regId);
        savePreference(Constant.PREFERENCE_KEY_APP_VERSION, appVersion);
    }
    
    //Gets the current registration ID for application on GCM service, if there is one.
    public String getRegistrationId() {
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = readIntPreference(Constant.PREFERENCE_KEY_APP_VERSION);
        int currentVersion = StaticUtils.getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return readStringPreference(Constant.PREFERENCE_KEY_REG_ID);
    }

	public void storeProfileSize(int profileWidth) {
		savePreference(Constant.PREFERENCE_KEY_PROFILE_SIZE, profileWidth);
	}
    
    public int getProfileSize() {
		return readIntPreference(Constant.PREFERENCE_KEY_PROFILE_SIZE);
	}
    
    public void storeAlarmTime(int hour, int minute) {
    	savePreference(Constant.PREFERENCE_KEY_ALARM_HOUR, hour);
    	savePreference(Constant.PREFERENCE_KEY_ALARM_MINUTE, minute);
    }
    
    public int getAlarmHour() {
    	return readIntPreference(Constant.PREFERENCE_KEY_ALARM_HOUR);
    }
    
    public int getAlarmMinute() {
    	return readIntPreference(Constant.PREFERENCE_KEY_ALARM_MINUTE);
    }
    
    /************************************************************************************************************************
	 *	Private PreferenceUtil Methods 
	 */
	private void savePreference(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	private void savePreference(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}
	
	private void savePreference(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	private String readStringPreference(String key) {
		return spf.getString(key, null);
	}

	private int readIntPreference(String key) {
		return spf.getInt(key, 0);
	}

	//TODO
	public void resetPreference() {
		savePreference("id", null);
		savePreference("isAlreadyShared", false);
	}
}
