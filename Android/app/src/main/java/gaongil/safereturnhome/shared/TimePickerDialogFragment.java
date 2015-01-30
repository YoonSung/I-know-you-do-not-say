package gaongil.safereturnhome.shared;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.support.Constant;

@EFragment
public class TimePickerDialogFragment extends DialogFragment {
	
    int mHour;
    int mMinute;

    @Pref
    PreferenceUtil_ preferenceUtil;

    //TODO DELETE
    @ViewById(R.id.drawer_main_left_user_btn_alarm)
    Button mLeftDrawerAlarmButton;

    // This handles the message send from TimePickerDialogFragment on setting Time
    Handler mTimePickerDialogHandler = new Handler(){
        @Override
        public void handleMessage(Message message){
            Bundle bundleFromTimePickerFragment = message.getData();

            int hour = bundleFromTimePickerFragment.getInt(Constant.BUNDLE_KEY_TIMEPICKER_HOUR);
            int minute = bundleFromTimePickerFragment.getInt(Constant.BUNDLE_KEY_TIMEPICKER_MINUTE);


            // Save New Alarm Time
            preferenceUtil.alarmHour().put(hour);
            preferenceUtil.alarmMinute().put(minute);

            updateAlarmView(hour, minute);
        }
    };


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
    	//Creating a bundle object to pass currently set time to the fragment
    	Bundle b = getArguments();

    	//Getting the hour of day from bundle
        mHour = b.getInt(Constant.BUNDLE_KEY_TIMEPICKER_HOUR);

        //Getting the minute of hour from bundle
        mMinute = b.getInt(Constant.BUNDLE_KEY_TIMEPICKER_MINUTE);
        
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        	
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				
				mHour = hourOfDay;
                mMinute = minute;
                
                // Creating a bundle object to pass currently set time to the fragment
                Bundle bundle = new Bundle();

                // Adding currently set hour to bundle object
                bundle.putInt(Constant.BUNDLE_KEY_TIMEPICKER_HOUR, mHour);

                // Adding currently set minute to bundle object
                bundle.putInt(Constant.BUNDLE_KEY_TIMEPICKER_MINUTE, mMinute);
                
                // Creating an instance of Message
                Message message = new Message();

                // Setting bundle object on the message object m
                message.setData(bundle);

                // Message m is sending using the message handler instantiated in MainActivity class
                mTimePickerDialogHandler.sendMessage(message);
			}
		};        

        
		// Opening the TimePickerDialog window
		return new TimePickerDialog(getActivity(), listener, mHour, mMinute, false);
    }

    //TODO otto apply to MainLeftDrawerFraglement
    private void updateAlarmView(int hour, int minute) {
        /**
         * Change Button Text
         */
        String timezone = Constant.TIME_ZONE_AM;
        if (hour > 12) {
            timezone = Constant.TIME_ZONE_PM;
            hour -= 12;
        }

        String displayTime = String.format("%s %02d:%02d", timezone, hour, minute);

        //mLeftDrawerAlarmButton.setText(displayTime);

        //TODO doing
        //mMainTextViewAlarmTime.setText(displayTime);
    }
}
