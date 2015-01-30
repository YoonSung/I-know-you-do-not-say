package gaongil.safereturnhome.scene;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.eventbus.OttoBus;
import gaongil.safereturnhome.model.UserStatus;
import gaongil.safereturnhome.support.*;
import gaongil.safereturnhome.fragment.*;

@EActivity(R.layout.activity_main)
@WindowFeature({Window.FEATURE_NO_TITLE})
public class MainActivity extends FragmentActivity {


    @Bean
    OttoBus bus;

    /**
     * Drawer
     */
    @ViewById(R.id.main_toolbar_left_toggle)
    ImageButton leftDrawerToggle;

    @ViewById(R.id.main_toolbar_right_toggle)
    ImageButton rightDrawerToggle;

    @ViewById(R.id.drawer_main_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.drawer_main_left)
    View leftDrawerLayout;

    @ViewById(R.id.drawer_main_right)
    View rightDrawerLayout;

    //@FragmentArg
    MainLeftDrawerFragment mainLeftDrawerFragment;

    /**
     * MainContents
     */
    @ViewById(R.id.main_btn_addgroup)
    Button addGroup;

    @ViewById(R.id.main_user_img_profile)
    ImageView userProfile;

    @ViewById(R.id.main_user_img_emoticon)
    ImageView userEmoticon;

    @ViewById(R.id.main_user_txt_currentstatus)
    TextView currentStatus;

    @ViewById(R.id.main_user_txt_returnhome_time)
    TextView alarmTime;


    /**
     * Common Data
     */
    private int profileSize;

    @Pref
    PreferenceUtil_ preferenceUtil;


    @AfterViews
    public void init() {
        bus.register(this);
        setupCommonData();
        setupDrawer();

        //TODO
        //setupSensorInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    //It is need to fragment onActivityResult Called (Fragment Request startActivityForResult, result method called in main activity)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mainLeftDrawerFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Click(R.id.main_btn_addgroup)
    void addGroup() {
        startActivity(new Intent(MainActivity.this, GroupActivity_.class));
    }

    @Click(R.id.main_toolbar_left_toggle)
    void leftToggle(View v) {
        drawerLayout.closeDrawer(rightDrawerLayout);
        drawerLayout.openDrawer(leftDrawerLayout);
    }

    @Click(R.id.main_toolbar_right_toggle)
    void rightToggle(View v) {
        drawerLayout.closeDrawer(leftDrawerLayout);
        drawerLayout.openDrawer(rightDrawerLayout);
    }

    @Subscribe
    public void updateStatus(UserStatus userStatus) {
        currentStatus.setText(userStatus.getStringValue(this));
        userEmoticon.setImageDrawable(getResources().getDrawable(userStatus.getImageResourceId()));
    }

    @Subscribe
    public void updateAlarm(String displayTime) {
        alarmTime.setText(displayTime);
    }

    @Subscribe
    public void updateProfile(Drawable profile) {
        ImageUtil.setCircleImageToTargetView(userProfile, profile);
    }

    private void setupCommonData() {
        profileSize = preferenceUtil.profileSize().get();

        //Appripriate Image Size Set
        LayoutParams profileLayoutParam = userProfile.getLayoutParams();
        profileLayoutParam.width = profileSize;
        profileLayoutParam.height = profileSize;
    }

    private void setupDrawer() {

        mainLeftDrawerFragment = new MainLeftDrawerFragment_();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.drawer_main_left, mainLeftDrawerFragment);
        fragmentTransaction.replace(R.id.drawer_main_right, new MainRightDrawerFragment_());
        fragmentTransaction.commit();

        DrawerLayout.DrawerListener drawerListener = new DrawableListener(findViewById(R.id.main_content_layout), drawerLayout, leftDrawerLayout, rightDrawerLayout);
        drawerLayout.setDrawerListener(drawerListener);
    }

    //TODO DELETE
    public void tempEventHandler(View v) {
        startActivity(new Intent(MainActivity.this, ChatActivity_.class));
    }

}
