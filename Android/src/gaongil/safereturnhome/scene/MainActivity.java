package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.Group;
import gaongil.safereturnhome.model.UserStatus;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.ImageUtil;
import gaongil.safereturnhome.support.PreferenceUtil;
import gaongil.safereturnhome.support.StaticUtils;
import gaongil.safereturnhome.support.StatusSpinnerAdapter;
import gaongil.safereturnhome.support.TimePickerDialogFragment;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	private final String TAG = MainActivity.class.getSimpleName();

	/**
	 * The drawer layout
	 */
	private DrawerLayout mDrawerLayout;
	
	
	/**
	 * Left drawer
	 */
	private View mLeftDrawerView;
	private Spinner mLeftDrawerStatusSpinner;
	private ImageButton mLeftDrawerProfileImageButton;
	private Button mLeftDrawerAlarmButton;
	//TimePickerDialog
	private TimePickerDialogFragment mTimePickerDialogFragment;
	private FragmentManager mFragmentManager; 
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
	
	
	/**
	 * Right drawer
	 */
	private View mRightDrawerView;
	
	
	/**
	 * MainContents
	 */
	private ActionBarDrawerToggle mDrawerToggle;
	private Button mMainBtnAddGroup;
	private ImageView mMainUserProfileImage, mMainUserEmoticonImage;
	private TextView mMainTextViewCurrentStatus, mMainTextViewAlarmTime;
	
	/**
	 * Common Data 
	 */
	private int mProfileSize;
	private ImageUtil mImageUtil;
	private PreferenceUtil mPreferenceUtil;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
	    setupCommonData();
	    setupMainComponent();
	    setupDrawer();
	    setupActionBar();
	    setupGroupInfo();
	    setupLeftDrawer();
	    //setupUserInfo();
	    
	    //TODO
	    //setupSensorInfo();
	    
	}
	
	private void setupCommonData() {
		mPreferenceUtil = new PreferenceUtil(this);
		mImageUtil = new ImageUtil(this);
		mProfileSize = mPreferenceUtil.getProfileSize();
	}
	
	/************************************************************************
	 * Setup Area Start
	 */
	private void setupLeftDrawer() {
		
		/**
		 * Alarm Setting
		 */
		mLeftDrawerAlarmButton = (Button) mLeftDrawerView.findViewById(R.id.drawer_main_left_user_btn_alarm);
		mLeftDrawerAlarmButton.setOnClickListener(this);
		mTimePickerDialogFragment = new TimePickerDialogFragment(mTimePickerDialogHandler);
		// Getting fragment manger for this activity
		mFragmentManager = getSupportFragmentManager();
		updateAlarmView(mPreferenceUtil.getAlarmHour(), mPreferenceUtil.getAlarmMinute());
		
		/**
		 * Profile ImageButton
		 */
		mLeftDrawerProfileImageButton = (ImageButton) mLeftDrawerView.findViewById(R.id.drawer_main_left_user_img_profile);
		LayoutParams profileLayoutParam = mLeftDrawerProfileImageButton.getLayoutParams();
		profileLayoutParam.width = mProfileSize;
		profileLayoutParam.height = mProfileSize;
		
		Drawable profile = mImageUtil.getProfileImage();
		mLeftDrawerProfileImageButton.setOnClickListener(this);
		updateProfileImage(profile);
		
		/**
		 * Spinner Setting
		 */
		mLeftDrawerStatusSpinner = (Spinner) mLeftDrawerView.findViewById(R.id.drawer_main_left_user_spinner_status);
		StatusSpinnerAdapter statusSpinnerAdapter = new StatusSpinnerAdapter(this, R.layout.status_list_row, UserStatus.getList());
		mLeftDrawerStatusSpinner.setAdapter(statusSpinnerAdapter);
		
		//apply Last saved status.
		updateStatusView(mPreferenceUtil.getUserStatusEnumPosition());
		
		mLeftDrawerStatusSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			
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
	}

	private void setupMainComponent() {
		// AddGroup
		mMainBtnAddGroup = (Button) findViewById(R.id.main_btn_addgroup);
		mMainBtnAddGroup.setOnClickListener(this);
		
		// UserStatus
		mMainUserProfileImage = (ImageView) findViewById(R.id.main_user_img_profile);
		LayoutParams profileLayoutParam = mMainUserProfileImage.getLayoutParams();
		profileLayoutParam.width = mProfileSize;
		profileLayoutParam.height = mProfileSize;
		
		mMainTextViewCurrentStatus = (TextView) findViewById(R.id.main_user_txt_currentstatus);
		mMainTextViewAlarmTime = (TextView) findViewById(R.id.main_user_txt_returnhome_time);
		mMainUserEmoticonImage = (ImageView) findViewById(R.id.main_user_img_emoticon);
		
	}
	
	private void setupGroupInfo() {
		
		//TODO GetData From DB.
		//TODO DELETE Initialize Declation. Test GroupData
		ArrayList<Group> groupList = new ArrayList<Group>();
		
		//If Group is Null, Create View for adding new group 
		if (groupList.size() == 0) {
			
		}
		
	}

	private void setupActionBar() {
		ActionBar mActionBar = getActionBar();
		if (mActionBar == null)
			return;
		
		//mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayUseLogoEnabled(true);
		mActionBar.setLogo(R.drawable.ic_menu);
		mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_color_blue)));
		
		//Its default system menu graphical icon
		//mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
	}

	private void setupDrawer() {
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
		
		mLeftDrawerView = (View) findViewById(R.id.drawer_main_left);
		mRightDrawerView = (View) findViewById(R.id.drawer_main_right);
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_menu, 0, 0) {
			
			//It's for lastTranslate Saved Variation
			private float lastTranslate = 0.0f;
			LinearLayout mainContentLayout = (LinearLayout) findViewById(R.id.main_content_layout);
			
			@Override
			public void onDrawerClosed(View view) {
			}

			@Override
			public void onDrawerOpened(View drawerView) {
			}
			
			@SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset) {
				
				//Its Drawer action Conflict Prevent Code
				if (drawerView == mLeftDrawerView) {
					mDrawerLayout.closeDrawer(mRightDrawerView);
				} else {
					mDrawerLayout.closeDrawer(mLeftDrawerView);
				}
				
				if (drawerView.getId() == R.id.drawer_main_right) {
					//if rightDrawer Action, Opposite Direction Set
					slideOffset *= -1;
				}
				
                float moveFactor = (mDrawerLayout.getWidth() * Constant.DRAWER_SLIDE_WIDTH_RATE* slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
                {
                    mainContentLayout.setTranslationX(moveFactor);
                }
                else
                {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setFillAfter(true);
                    mainContentLayout.startAnimation(anim);

                    lastTranslate = moveFactor;
                }
            }
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.closeDrawers();
	}
	
	/*
	 * Setup Area End
	 ************************************************************************/
	
	/************************************************************************
	 * Update View Area Start
	 */

	private void updateProfileImage(Drawable profile) {
		
		if (profile == null) {
			return;
		}
		
		mImageUtil.setCircleImageToTargetView(mLeftDrawerProfileImageButton, profile);
		mImageUtil.setCircleImageToTargetView(mMainUserProfileImage, profile);
	}
	
	private void updateProfileImageView(Bitmap profile) {
		
		if (profile == null) {
			return;
		}
		
		mImageUtil.setCircleImageToTargetView(mLeftDrawerProfileImageButton, profile);
		mImageUtil.setCircleImageToTargetView(mMainUserProfileImage, profile);
	}
	
	private void updateStatusView(int position) {
		mLeftDrawerStatusSpinner.setSelection(position);
		UserStatus userStatus = UserStatus.getList().get(position);
		
		//update text
		mMainTextViewCurrentStatus.setText(userStatus.getStringValue(MainActivity.this));
		
		//update emoticon
		mMainUserEmoticonImage.setImageDrawable(getResources().getDrawable(userStatus.getImageResourceId()));
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
        mMainTextViewAlarmTime.setText(displayTime);
	}
	
	/*
	 * Update View Area End
	 ************************************************************************/
	
	/************************************************************************
	 * LeftDrawer Image Crop Start
	 */
	@Override
	protected void onActivityResult(int requestCode	, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
        	beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
        	handleCrop(resultCode, result);
        }
	}
	
	private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(getCacheDir(), "cropped"));
        new Crop(source).output(outputUri).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
        	Bitmap croppedImage = null;
        	
        	try {
				croppedImage = StaticUtils.scaleBitmap(this, Crop.getOutput(result), mProfileSize, mProfileSize);
			} catch (Exception e) {
				//TODO
				e.printStackTrace();
			}
        	
			// TODO Send Image by Network. (All Device Common Size Image) 
			// Save Proper Image
        	updateProfileImageView(croppedImage);
        	
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    /*
	 * LeftDrawer Image Crop End
	 ************************************************************************/

    @Override
	public void onClick(View v) {
		switch (v.getId()) {

			/**
			 * Click MainView Component
			 */
			case R.id.main_btn_addgroup:
				//TODO Modify
				//startActivity(new Intent(MainActivity.this, GroupActivity.class));
				startActivity(new Intent(MainActivity.this, ContactsActivity.class));
				break;
				
			/**
			 * Click LeftDrawer Component	
			 */
			case R.id.drawer_main_left_user_spinner_status:
				TimePickerDialog alert = new TimePickerDialog(this, null, 0, 0, false);
			    alert.show();
			    break;
			case R.id.drawer_main_left_user_img_profile:
				// Cropped Image Next Routine - onActivityResult, beginCrop
				Crop.pickImage(MainActivity.this);
				break;
			case R.id.drawer_main_left_user_btn_alarm:
				showTimePicker();
				break;
		} //switch end
	}
	
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
		
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		// Adding the fragment object to the fragment transaction
		ft.add(mTimePickerDialogFragment, Constant.TIME_PICKER);
		ft.commit();
	}
    
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggle
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//Left Drawer Toggle Clicked
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
			
		//Right Drawer Toggle Clicked
		} else if (item.getItemId() == R.id.main_toggle_timeline) {
			if (mDrawerLayout.isDrawerOpen(mRightDrawerView)) {
				mDrawerLayout.closeDrawer(mRightDrawerView);
			} else {
				mDrawerLayout.openDrawer(mRightDrawerView);
			}
			
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
				getSupportFragmentManager().popBackStackImmediate();
			}
			else
				finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
}
