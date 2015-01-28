package gaongil.safereturnhome.scene;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import gaongil.safereturnhome.support.DrawableListener;
import gaongil.safereturnhome.support.ImageUtil;
import gaongil.safereturnhome.support.PreferenceUtil;

public class MainActivity extends FragmentActivity implements OnClickListener {

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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupCommonData();
        setupMainComponent();
        setupDrawer();

        //TODO
        //setupSensorInfo();
    }

    /**
     * *********************************************************************
     * Setup Area Start
     */
    private void setupCommonData() {
        mPreferenceUtil = new PreferenceUtil(this);
        mImageUtil = new ImageUtil(this);
        mProfileSize = mPreferenceUtil.getProfileSize();
        mFragmentManager = getSupportFragmentManager();
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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
        mLeftDrawerView = (View) findViewById(R.id.drawer_main_left);
        mRightDrawerView = (View) findViewById(R.id.drawer_main_right);


        DrawerLayout.DrawerListener drawerListener = new DrawableListener(findViewById(R.id.main_content_layout), mDrawerLayout, mLeftDrawerView, mRightDrawerView);

        mDrawerLayout.setDrawerListener(drawerListener);

        //left fragment
        MainLeftDrawerFragment leftDrawerFragment = new MainLeftDrawerFragment();
        leftDrawerFragment.setData(mPreferenceUtil, mImageUtil);

        //right fragment
        MainRightDrawerFragment rightDrawerFragment = new MainRightDrawerFragment();

        //fragment transaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.drawer_main_left, leftDrawerFragment);
        fragmentTransaction.replace(R.id.drawer_main_right, rightDrawerFragment);

        fragmentTransaction.commit();

        /*
        switch (v.getId()) {
                    case R.id.main_toolbar_left_toggle:
                        mDrawerLayout.closeDrawer(mRightDrawerView);
                        mDrawerLayout.openDrawer(mLeftDrawerView);
                        break;

                    case R.id.main_toolbar_right_toggle:
                        mDrawerLayout.closeDrawer(mLeftDrawerView);
                        mDrawerLayout.openDrawer(mRightDrawerView);
                        break;

                }
         */
    }

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
