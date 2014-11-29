package gaongil.safereturnhome.support;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

public class TimePickerDialogFragment extends DialogFragment {
	
	Handler mHandler ;
    int mHour;
    int mMinute;    

    public TimePickerDialogFragment(Handler handler){
    	//Getting the reference to the message handler instantiated in MainActivity class
        mHandler = handler;
    }

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
                mHandler.sendMessage(message);								
			}
		};        

        
		// Opening the TimePickerDialog window
		return new TimePickerDialog(getActivity(), listener, mHour, mMinute, false);
    }

}
