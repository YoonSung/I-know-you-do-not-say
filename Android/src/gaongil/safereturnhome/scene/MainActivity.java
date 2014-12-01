package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.db.DatabaseHelper;
import gaongil.safereturnhome.exception.saveImageFileException;
import gaongil.safereturnhome.model.Group;
import gaongil.safereturnhome.model.MessageData;
import gaongil.safereturnhome.model.MessageType;
import gaongil.safereturnhome.model.User;
import gaongil.safereturnhome.model.UserStatus;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.ImageUtil;
import gaongil.safereturnhome.support.PreferenceUtil;
import gaongil.safereturnhome.support.StaticUtils;
import gaongil.safereturnhome.support.StatusSpinnerAdapter;
import gaongil.safereturnhome.support.TimeLineAdapter;
import gaongil.safereturnhome.support.TimePickerDialogFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.soundcloud.android.crop.Crop;

public class MainActivity extends CustomActivity implements OnClickListener{
	
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
	private TimeLineAdapter mTimeLineAdapter;
	private ListView mRightDrawerListView;
	
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
	    
	    super.setupActionBar(R.drawable.ic_menu);
	    setupCommonData();
	    setupMainComponent();
	    
	    setupDrawer();
	    setupLeftDrawer();
	    setupRightDrawer();
	    updateViewBySavedData();
	    
	    //TODO
	    //setupSensorInfo();
	    
	    //TODO DELETE
	    //Test Start
	    setupTestGroup();
	    //Test End
	}
	
	/************************************************************************
	 * Setup Area Start
	 */
	private void setupCommonData() {
		mPreferenceUtil = new PreferenceUtil(this);
		mImageUtil = new ImageUtil(this);
		mProfileSize = mPreferenceUtil.getProfileSize();
		mFragmentManager = getSupportFragmentManager();
	}
	
	@Override
	protected void setupLeftDrawer() {
		
		/**
		 * Alarm Setting
		 */
		mLeftDrawerAlarmButton = (Button) mLeftDrawerView.findViewById(R.id.drawer_main_left_user_btn_alarm);
		mLeftDrawerAlarmButton.setOnClickListener(this);
		mTimePickerDialogFragment = new TimePickerDialogFragment(mTimePickerDialogHandler);
		
		
		/**
		 * Profile ImageButton
		 */
		mLeftDrawerProfileImageButton = (ImageButton) mLeftDrawerView.findViewById(R.id.drawer_main_left_user_img_profile);
		mLeftDrawerProfileImageButton.setOnClickListener(this);
		
		
		/**
		 * Spinner Setting
		 */
		mLeftDrawerStatusSpinner = (Spinner) mLeftDrawerView.findViewById(R.id.drawer_main_left_user_spinner_status);
		StatusSpinnerAdapter statusSpinnerAdapter = new StatusSpinnerAdapter(this, R.layout.status_list_row, UserStatus.getList());
		mLeftDrawerStatusSpinner.setAdapter(statusSpinnerAdapter);
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

	@Override
	protected void setupRightDrawer() {
		//TODO DELETE
		//Test Start
		ArrayList<MessageData> testList = new ArrayList<MessageData>();
		testList.add(new MessageData(0, 3, "경로를 벗어났습니다. 관심을 가져주세요", new Date(1,1,1, 21, 02),  MessageType.WARN, true ));
		testList.add(new MessageData(0, 1, "충격이 감지되었습니다 !!", new Date(1,1,1, 20, 51),  MessageType.URGENT, true ));
		testList.add(new MessageData(0, 4, "(집)으로 귀가를 시작했습니다.", new Date(1,1,1, 18, 30),  MessageType.ANNOUNCE, true ));
		testList.add(new MessageData(0, 2, "상태가 (외로움) 으로 변경되었습니다.", new Date(1, 1, 1, 17, 11),  MessageType.NORMAL, true ));
		testList.add(new MessageData(0, 4, "이제 슬슬 집으로 가봐야겠다~ 다들 오늘 몇시에 들어와?.", new Date(1, 1, 1, 16, 55),  MessageType.NORMAL, true ));
		//Test End
		
		this.mTimeLineAdapter = new TimeLineAdapter(MainActivity.this, testList);
		
		mRightDrawerListView = (ListView) mRightDrawerView.findViewById(R.id.drawer_main_right_listview);
		
		mRightDrawerListView.setAdapter(mTimeLineAdapter);
		mRightDrawerListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		mRightDrawerListView.setStackFromBottom(false);
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
	
	@Override
	protected void setupDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
		mLeftDrawerView = (View) findViewById(R.id.drawer_main_left);
		mRightDrawerView = (View) findViewById(R.id.drawer_main_right);
		LinearLayout mainContentLayout = (LinearLayout) findViewById(R.id.main_content_layout);
		
		mDrawerToggle = super.getActionBarDrawerToggle(
				this, 
				mDrawerLayout, 
				mLeftDrawerView, 
				mRightDrawerView, 
				mainContentLayout, 
				R.drawable.ic_menu,
				R.menu.main
		);
		
		super.setDrawerLayoutOptions(mDrawerLayout, mDrawerToggle);
	}
	/*
	 * Setup Area End
	 ************************************************************************/
	
	
	
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
		LayoutParams profileLayoutParam = mLeftDrawerProfileImageButton.getLayoutParams();
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
    	System.out.println("handleCrop Called");
    	System.out.println("resultCode : "+resultCode);
    	
        if (resultCode == RESULT_OK) {
        	Bitmap croppedImage = null;
        	
        	try {
				croppedImage = StaticUtils.scaleBitmap(this, Crop.getOutput(result), mProfileSize, mProfileSize);
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
				mImageUtil.saveProfileImage(croppedImage, ""+getGenerateUserId());
				//mImageUtil.saveProfileImage(croppedImage, Constant.PROFILE_IMAGE_NAME);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	//TODO DELETE
        	//Test End
        	
        	updateProfileImageView(croppedImage);
        	
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    /*
	 * LeftDrawer Image Crop End
	 ************************************************************************/
    
    public void tempEventHandler(View v) {
    	startActivity(new Intent(MainActivity.this, ChatActivity.class));
    }
    

    @Override
	public void onClick(View v) {
		switch (v.getId()) {

			/**
			 * Click MainView Component
			 */
			case R.id.main_btn_addgroup:
				//TODO Modify
				startActivity(new Intent(MainActivity.this, GroupActivity.class));
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
    
    /**
     * TODO DELETE
	 * Test start
	 */
    private void setupTestGroup() {
    	
    	int[] testUserIdList = new int[]{
    			R.id.main_group_testuser_1,
    			R.id.main_group_testuser_2,
    			R.id.main_group_testuser_3,
    			R.id.main_group_testuser_4,
    			R.id.main_group_testuser_5
    	};
    	
    	RuntimeExceptionDao<User, Integer> userDao;
		try {
			userDao = getHelper().getUserDao();
			List<User> userList = userDao.queryForAll();

			RelativeLayout groupLayout = (RelativeLayout) findViewById(R.id.main_group_testgroup);
			LinearLayout userLayout =  null;
			
			for (int i = 0; i < testUserIdList.length; i++) {
				
				userLayout = (LinearLayout) findViewById(testUserIdList[i]);
				
				User testUser = userList.get(i);
				
				ImageView userImage = (ImageView) userLayout.findViewById(R.id.group_user_profile);
				
				//TODO add _ in prefix
				Drawable drawable = mImageUtil.getProfileImage(""+testUser.getId());
				userImage.setImageDrawable(drawable);
				
				TextView userName = (TextView) userLayout.findViewById(R.id.group_user_name);
				userName.setText(testUser.getName());
				
				TextView lastCheckedTime = (TextView) userLayout.findViewById(R.id.group_user_checkedtime);
				lastCheckedTime.setText("확인 "+new Random().nextInt(60)+"분 이전");
				
				ArrayList<UserStatus> statusList = UserStatus.getList();
				
				ImageView emoticon = (ImageView) userLayout.findViewById(R.id.group_user_emoticon);
				emoticon.setImageResource(statusList.get(i).getImageResourceId());
				
				//not safe
				if (i == 2 || i == 4) {
					TextView textView = (TextView) userLayout.findViewById(R.id.group_user_status);
					textView.setText("UNSAFE");
					
					LinearLayout linearLayout = (LinearLayout) userLayout.findViewById(R.id.group_user_linearlayout_statuscolor);
					linearLayout.setBackgroundColor(getResources().getColor(R.color.main_color_lightred));
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
	private DatabaseHelper databaseHelper = null;

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    if (databaseHelper != null) {
	        OpenHelperManager.releaseHelper();
	        databaseHelper = null;
	    }
	}

	private DatabaseHelper getHelper() {
	    if (databaseHelper == null) {
	        databaseHelper =
	            OpenHelperManager.getHelper(this, DatabaseHelper.class);
	    }
	    return databaseHelper;
	}
	
	private int getGenerateUserId() {
		int generateImageNum = 1;
		
		try {
			RuntimeExceptionDao<User, Integer> userDao = getHelper().getUserDao();
			List<User> userList = userDao.queryForAll();
			
			String[] generateNames = new String[]{"정윤성", "문교수", "김효개", "최카멕", "갓동찬", "정윤성"};
			
			if (userList.size() > 0) {
				User lastUser = userList.get(userList.size()-1);
				generateImageNum = lastUser.getId() + 1;
			}

			String generateName = generateNames[generateImageNum];
			
			int createResult = userDao.create(new User(generateName , generateImageNum+Constant.IMAGE_EXTENSION, "testNickname"));
			Toast.makeText(
					MainActivity.this, 
					"CreateResult : "
					+(createResult == 1)
					+"\nGeneratedImageName : "
					+ generateName
					+"\nGeneratedImageNum : "
					+ generateImageNum, 
					Toast.LENGTH_LONG
			).show();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return generateImageNum;
	}
	/**
	 * TODO DELETE
	 * Test End
	 */
	
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
	public boolean onRightDrawerToggleSelected(MenuItem item) {
		return super.defaultRightDrawerToggleSelected();
	}
	
}
