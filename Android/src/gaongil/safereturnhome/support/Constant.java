package gaongil.safereturnhome.support;

public class Constant {

	/********************************************************************************
	 * 										Preference
	 *********************************************************************************/
	public static final String PREFERENCE_KEY_REG_ID = "registrationId";
	public static final String PREFERENCE_KEY_APP_VERSION = "appVersion";
	public static final String PREFERENCE_KEY_PROFILE_SIZE = "profileSize";
	public static final String PREFERENCE_KEY_ALARM_HOUR = "alarmHour";
	public static final String PREFERENCE_KEY_ALARM_MINUTE = "alarmMinute";
	
	/********************************************************************************
	 * 										Network Key
	 *********************************************************************************/
	public static final String NETWORK_PARAM_KEY_REGID = "regId";
	
	/********************************************************************************
	 * 										Request URL
	 *********************************************************************************/
	public static final String NETWORK_URL_REGISTER_ID = "/user";
	
	/********************************************************************************
	 * 										Constant
	 *********************************************************************************/
	public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    // Project number from the API Console
	public static final String PROJECT_ID = "342931063456";
	
	//private static final String ROOT_PATH = "http://localhost:8080";
	public static final String ROOT_PATH = "http://10.73.43.247:8080";
	public static final String LINE_END = "\r\n";
	public static final String TWO_HYPHENS = "--";
	public static final String DATA_BOUNDARY = "*****";
	
	//private static final String ROOT_PATH = "http://localhost:8080";
	public static final String MESSAGE_PLAY_SERVICES_ERROR = "This device is not supported.";
	public static final int  SPLASH_WAIT_TIME = 1000;
	public static final float DRAWER_SLIDE_WIDTH_RATE = 0.7f;
	public static final String DATE_FORMAT = "a h:mm";
	public static final int PROFILE_IMAGE_RATE_BY_DEVICE_WIDTH = 4;
	public static final String PROFILE_IMAGE_NAME = "profile.png";
	
	public static final String BUNDLE_KEY_TIMEPICKER_HOUR = "timepickerHour";
	public static final String BUNDLE_KEY_TIMEPICKER_MINUTE = "timepickerMinute";
	public static final String TIME_ZONE_AM = "AM";
	public static final String TIME_ZONE_PM = "PM";
	public static final String TIME_PICKER = "timePicker";
}
