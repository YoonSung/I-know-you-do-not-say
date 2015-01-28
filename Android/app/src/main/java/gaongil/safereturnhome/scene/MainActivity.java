package gaongil.safereturnhome.scene;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.fragment.MainLeftDrawerFragment;
import gaongil.safereturnhome.model.MessageData;
import gaongil.safereturnhome.model.MessageType;
import gaongil.safereturnhome.support.ImageUtil;
import gaongil.safereturnhome.support.PreferenceUtil;
import gaongil.safereturnhome.support.TimeLineAdapter;

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
