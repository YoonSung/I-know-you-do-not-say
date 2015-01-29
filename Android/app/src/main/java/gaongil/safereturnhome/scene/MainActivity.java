package gaongil.safereturnhome.scene;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.fragment.MainLeftDrawerFragment;
import gaongil.safereturnhome.fragment.MainRightDrawerFragment;
import gaongil.safereturnhome.support.DrawableListener;
import gaongil.safereturnhome.support.ImageUtil;
import gaongil.safereturnhome.support.PreferenceUtil;


@EActivity(R.layout.activity_main)
@WindowFeature({Window.FEATURE_NO_TITLE})
public class MainActivity extends FragmentActivity {

    /**
     * Drawer
     */
    @ViewById(R.id.drawer_main_layout)
    DrawerLayout mDrawerLayout;

    @ViewById(R.id.drawer_main_left)
    View mLeftDrawerView;

    @ViewById(R.id.drawer_main_right)
    View mRightDrawerView;

    @FragmentById(R.id.drawer_main_left)
    MainLeftDrawerFragment mLeftDrawerFragment;

    @FragmentById(R.id.drawer_main_right)
    MainRightDrawerFragment mRightDrawerFragment;

    /**
     * MainContents
     */
    @ViewById(R.id.main_btn_addgroup)
    Button mMainBtnAddGroup;

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
    }

    @AfterViews
    public void init() {
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
    }

    private void setupMainComponent() {

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

        DrawerLayout.DrawerListener drawerListener = new DrawableListener(findViewById(R.id.main_content_layout), mDrawerLayout, mLeftDrawerView, mRightDrawerView);
        mDrawerLayout.setDrawerListener(drawerListener);

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


    @Click(R.id.main_btn_addgroup)
    void addGroup(View v) {
        startActivity(new Intent(MainActivity.this, GroupActivity.class));
    }

}
