package gaongil.safereturnhome.scene;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.fragment.MainLeftDrawerFragment;
import gaongil.safereturnhome.fragment.MainRightDrawerFragment;
import gaongil.safereturnhome.support.ImageUtil;
import gaongil.safereturnhome.support.PreferenceUtil;

public class MainActivity extends CustomActivity implements OnClickListener{
	
	/**
	 * The drawer layout
	 */
	private DrawerLayout mDrawerLayout;
    private View mLeftDrawerView;

    private FragmentManager mFragmentManager;

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
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

        //setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.main_toolbar));

	    setupCommonData();
	    setupMainComponent();

	    setupDrawer();
	    setupLeftDrawer();
	    setupRightDrawer();

	    
	    //TODO
	    //setupSensorInfo();
	    
	    //TODO DELETE
	    //Test Start
	    //setupTestGroup();
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
	
	protected void setupLeftDrawer() {
        MainLeftDrawerFragment leftDrawerFragment = new MainLeftDrawerFragment();
        leftDrawerFragment.setData(mPreferenceUtil, mImageUtil);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.drawer_main_left, leftDrawerFragment);
        fragmentTransaction.commit();
	}

	protected void setupRightDrawer() {
        MainRightDrawerFragment rightDrawerFragment = new MainRightDrawerFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.drawer_main_right, rightDrawerFragment);
        fragmentTransaction.commit();
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
	
	protected void setupDrawer() {

        /*
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
				R.id.main_toolbar,
                0,
				R.menu.main
		);
		
		super.setDrawerLayoutOptions(mDrawerLayout, mDrawerToggle);
		*/

        test();
	}
	/*
	 * Setup Area End
	 ************************************************************************/

    private void test() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
        mLeftDrawerView = (View) findViewById(R.id.drawer_main_left);
        mRightDrawerView = (View) findViewById(R.id.drawer_main_right);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
        //mToolbar.setHapticFeedbackEnabled(true);
        //mToolbar.setTitle(null);

        /*
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
*/
        //ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,  mDrawerLayout, mToolbar,0, 0);
        //mDrawerLayout.setDrawerListener(mDrawerToggle);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //mDrawerToggle.syncState();

        //mToolbar.setNavigationIcon(R.drawable.ic_with);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            System.out.println("left!");
        } else {
            System.out.println("Right!");
        }

        return super.onOptionsItemSelected(item);
    }
*/

	
	
	

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
		} //switch end
	}
    
}
