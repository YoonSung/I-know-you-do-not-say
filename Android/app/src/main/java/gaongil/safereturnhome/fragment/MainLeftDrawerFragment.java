package gaongil.safereturnhome.fragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.Group;
import gaongil.safereturnhome.model.UserStatus;
import gaongil.safereturnhome.scene.GroupActivity;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.ImageUtil;
import gaongil.safereturnhome.support.PreferenceUtil;
import gaongil.safereturnhome.support.StaticUtils;
import gaongil.safereturnhome.adapter.StatusSpinnerAdapter;
import gaongil.safereturnhome.support.TimePickerDialogFragment;


@EFragment(R.layout.drawer_main_left)
public class MainLeftDrawerFragment extends Fragment implements View.OnClickListener {


    @ViewById(R.id.drawer_main_left_user_spinner_status)
    Spinner mLeftDrawerStatusSpinner;

    @ViewById(R.id.drawer_main_left_user_img_profile)
    ImageButton mLeftDrawerProfileImageButton;

    @ViewById(R.id.drawer_main_left_user_btn_alarm)
    Button mLeftDrawerAlarmButton;

    @ViewById(R.id.main_user_txt_currentstatus)
    TextView mMainTextViewCurrentStatus;

    //TimePickerDialog
    private TimePickerDialogFragment mTimePickerDialogFragment;

    private PreferenceUtil mPreferenceUtil;
    private ImageUtil mImageUtil;
    private int mProfileSize;
    // This handles the message send from TimePickerDialogFragment on setting Time
    Handler mTimePickerDialogHandler = new Handler(){
        @Override
        public void handleMessage(Message message){
            Bundle bundleFromTimePickerFragment = message.getData();

            int hour = bundleFromTimePickerFragment.getInt(Constant.BUNDLE_KEY_TIMEPICKER_HOUR);
            int minute = bundleFromTimePickerFragment.getInt(Constant.BUNDLE_KEY_TIMEPICKER_MINUTE);


            // Save New Alarm Time
            mPreferenceUtil.storeAlarmTime(hour, minute);
            updateAlarmView(hour, minute);
        }
    };

    @AfterViews
    void init() {
        Log.d(Constant.TAG,"test");
        initSetting();
        updateViewBySavedData();

    }

    private void initSetting() {

        //TODO Optimize
        this.mPreferenceUtil = new PreferenceUtil(getActivity());
        this.mImageUtil = new ImageUtil(getActivity());


        mProfileSize = mPreferenceUtil.getProfileSize();

        /**
         * Alarm Setting
         */
        mLeftDrawerAlarmButton.setOnClickListener(this);
        mTimePickerDialogFragment = new TimePickerDialogFragment(mTimePickerDialogHandler);


        /**
         * Profile ImageButton
         */
        mLeftDrawerProfileImageButton.setOnClickListener(this);


        /**
         * Spinner Setting
         */
        StatusSpinnerAdapter statusSpinnerAdapter = new StatusSpinnerAdapter(this.getActivity(), R.layout.status_list_row, UserStatus.getList());

        Log.d(Constant.TAG,"statusSpinnerAdapter : "+statusSpinnerAdapter);
        Log.d(Constant.TAG,"mLeftDrawerStatusSpinner : "+mLeftDrawerStatusSpinner);
        Log.d(Constant.TAG,"UserStatus.getList() : "+UserStatus.getList());

        mLeftDrawerStatusSpinner.setAdapter(statusSpinnerAdapter);
        mLeftDrawerStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private boolean isInitializedCall = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isInitializedCall) {
                    isInitializedCall = false;
                    return;
                }

                int enumPosition = view.getId();
                //check if status same previous status, did not update anithing especially network things
                mPreferenceUtil.storeUserStatusEnumPosition(position);
                updateStatusView(enumPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mLeftDrawerStatusSpinner.notify();
    }

    private void updateProfileImageView(Bitmap profile) {

        if (profile == null) {
            return;
        }

        mImageUtil.setCircleImageToTargetView(mLeftDrawerProfileImageButton, profile);
        //TODO doing
        //mImageUtil.setCircleImageToTargetView(mMainUserProfileImage, profile);
    }

    private void updateStatusView(int position) {
        mLeftDrawerStatusSpinner.setSelection(position);
        UserStatus userStatus = UserStatus.getList().get(position);


        Log.d(Constant.TAG, userStatus.toString());

        //update text
        //TODO doing
        mMainTextViewCurrentStatus.setText(userStatus.getStringValue(getActivity()));

        //update emoticon
        //TODO doing
        //mMainUserEmoticonImage.setImageDrawable(getResources().getDrawable(userStatus.getImageResourceId()));
    }

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
        mLeftDrawerAlarmButton.setText(displayTime);

        //TODO doing
        //mMainTextViewAlarmTime.setText(displayTime);
    }

    /************************************************************************
     * Update View Area Start
     */
    private void updateViewBySavedData() {

		/*
		 * Alarm
		 */
        updateAlarmView(mPreferenceUtil.getAlarmHour(), mPreferenceUtil.getAlarmMinute());

		/*
		 * Profile
		 */
        ViewGroup.LayoutParams profileLayoutParam = mLeftDrawerProfileImageButton.getLayoutParams();
        profileLayoutParam.width = mProfileSize;
        profileLayoutParam.height = mProfileSize;
        Drawable profile = mImageUtil.getProfileImage(Constant.PROFILE_IMAGE_NAME);
        updateProfileImage(profile);

		/*
		 * Status
		 */
        updateStatusView(mPreferenceUtil.getUserStatusEnumPosition());


		/*
		 * Group
		 */
        //TODO GetData From DB.
        //TODO DELETE Initialize Declation. Test GroupData
        ArrayList<Group> groupList = new ArrayList<Group>();

        //If Group is Null, Create View for adding new group
        if (groupList.size() == 0) {

        }
    }

    private void updateProfileImage(Drawable profile) {

        if (profile == null) {
            return;
        }

        mImageUtil.setCircleImageToTargetView(mLeftDrawerProfileImageButton, profile);

        //TODO doing
        //mImageUtil.setCircleImageToTargetView(mMainUserProfileImage, profile);
    }


	/*
	 * Update View Area End
	 ************************************************************************/



    /************************************************************************
     * LeftDrawer Image Crop Start
     */
    @Override
    public void onActivityResult(int requestCode	, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == this.getActivity().RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(this.getActivity().getCacheDir(), "cropped"));
        new Crop(source).output(outputUri).asSquare().start(this.getActivity());
    }

    private void handleCrop(int resultCode, Intent result) {
        System.out.println("handleCrop Called");
        System.out.println("resultCode : "+resultCode);

        if (resultCode == this.getActivity().RESULT_OK) {
            Bitmap croppedImage = null;

            try {
                croppedImage = StaticUtils.scaleBitmap(this.getActivity(), Crop.getOutput(result), mProfileSize, mProfileSize);
                System.out.println("croppedImage : "+croppedImage);
            } catch (Exception e) {
                //TODO
                e.printStackTrace();
            }

            // TODO Send Image by Network. (All Device Common Size Image)
            // Save Proper Image

            //TODO DELETE
            //Test Start
            try {
                //mImageUtil.saveProfileImage(croppedImage, ""+getGenerateUserId());
                mImageUtil.saveProfileImage(croppedImage, Constant.PROFILE_IMAGE_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //TODO DELETE
            //Test End

            updateProfileImageView(croppedImage);

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this.getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    /*
	 * LeftDrawer Image Crop End
	 ************************************************************************/

    private void showTimePicker() {
        Bundle bundleToTimePickerDialogFragment = new Bundle();
        bundleToTimePickerDialogFragment.putInt(
                Constant.BUNDLE_KEY_TIMEPICKER_HOUR,
                mPreferenceUtil.getAlarmHour()
        );
        bundleToTimePickerDialogFragment.putInt(
                Constant.BUNDLE_KEY_TIMEPICKER_MINUTE,
                mPreferenceUtil.getAlarmMinute()
        );

        mTimePickerDialogFragment.setArguments(bundleToTimePickerDialogFragment);

        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        // Adding the fragment object to the fragment transaction
        ft.add(mTimePickerDialogFragment, Constant.TIME_PICKER);
        ft.commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /**
             * Click MainView Component
             */
            case R.id.main_btn_addgroup:
                //TODO Modify
                startActivity(new Intent(this.getActivity(), GroupActivity.class));
                break;

            /**
             * Click LeftDrawer Component
             */
            case R.id.drawer_main_left_user_spinner_status:
                TimePickerDialog alert = new TimePickerDialog(this.getActivity(), null, 0, 0, false);
                alert.show();
                break;
            case R.id.drawer_main_left_user_img_profile:
                // Cropped Image Next Routine - onActivityResult, beginCrop
                Crop.pickImage(this.getActivity());
                break;
            case R.id.drawer_main_left_user_btn_alarm:
                showTimePicker();
                break;
        } //switch end
    }
}
