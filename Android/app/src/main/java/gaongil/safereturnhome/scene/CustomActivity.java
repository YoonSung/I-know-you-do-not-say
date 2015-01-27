package gaongil.safereturnhome.scene;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.support.Constant;

public abstract class CustomActivity extends ActionBarActivity {

    private ActionBarDrawerToggle leftDrawerToggle;
    private int rightDrawerToggleDrawableId;
    private DrawerLayout mDrawerLayout;
    private View mRightDrawerView, mLeftDrawerView;
    private Toolbar mToolbar;

    protected abstract boolean onRightDrawerToggleSelected(MenuItem item);

    protected abstract void setupDrawer();

    protected abstract void setupLeftDrawer();

    protected abstract void setupRightDrawer();

    protected void setupActionBar(int leftDrawerToggleDrawableId) {
        ActionBar actionBar = getSupportActionBar();
        System.out.println("actionBar : " + actionBar);

        if (actionBar == null)
            return;

        //mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(true);
        //actionBar.setLogo(leftDrawerToggleDrawableId);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_background)));

        //Its default system menu graphical icon
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    //TODO DELETE drawableId
    protected ActionBarDrawerToggle getActionBarDrawerToggle(
            final Activity activity,
            final DrawerLayout mDrawerLayout,
            final View mLeftDrawerView,
            final View mRightDrawerView,
            final LinearLayout mainContentLayout,
            final int toolbarId,
            final int rightDrawerToggleDrawableId) {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);


        mToolbar = (Toolbar) activity.findViewById(toolbarId);
        //set toolbar
        //setSupportActionBar((Toolbar) activity.findViewById(toolbarId));

        /*
        //test start
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                System.out.println("test");
                return false;
            }
        });
        //test end
        */

        this.rightDrawerToggleDrawableId = rightDrawerToggleDrawableId;

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout,
                0, 0) {

            // It's for lastTranslate Saved Variation
            private float lastTranslate = 0.0f;

            @Override
            public void onDrawerClosed(View view) {
                syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                syncState();
            }

            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Its Drawer action Conflict Prevent Code
                if (drawerView == mLeftDrawerView) {
                    mDrawerLayout.closeDrawer(mRightDrawerView);
                } else {
                    mDrawerLayout.closeDrawer(mLeftDrawerView);
                }

                if (drawerView == mRightDrawerView) {
                    // if rightDrawer Action, Opposite Direction Set
                    slideOffset *= -1;
                }

                float moveFactor = (mDrawerLayout.getWidth()
                        * Constant.DRAWER_SLIDE_WIDTH_RATE * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    mainContentLayout.setTranslationX(moveFactor);
                } else {
                    TranslateAnimation anim = new TranslateAnimation(
                            lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setFillAfter(true);
                    mainContentLayout.startAnimation(anim);

                    lastTranslate = moveFactor;
                }
                super.onDrawerSlide(drawerView, slideOffset);

            } //onDrawerSlide

        }; //new ActionBarDrawerToggle


        //saveData in Parent Because OptionItemSelected or etc actions
        this.mDrawerLayout = mDrawerLayout;
        this.leftDrawerToggle = actionBarDrawerToggle;
        this.mRightDrawerView = mRightDrawerView;
        this.mLeftDrawerView = mLeftDrawerView;

        return actionBarDrawerToggle;
    }

    protected void setDrawerLayoutOptions(DrawerLayout mDrawerLayout, ActionBarDrawerToggle mDrawerToggle) {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStackImmediate();
            } else
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(rightDrawerToggleDrawableId, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("Menu Clicked!!!");
        System.out.println(item.getItemId() == android.R.id.home);

        //Left Drawer Toggle Clicked
        //if (leftDrawerToggle.onOptionsItemSelected(item)) {
        if (item.getItemId() == android.R.id.home) {
            return onLeftDrawerToggleSelected(item);
            //return true;

            //Right Drawer Toggle Clicked
        } else {
            return onRightDrawerToggleSelected(item);
        }

        //return super.onOptionsItemSelected(item);
    }

    private boolean onLeftDrawerToggleSelected(MenuItem item) {
        if (mDrawerLayout.isDrawerOpen(mLeftDrawerView)) {
            mDrawerLayout.closeDrawer(mLeftDrawerView);
        } else {
            mDrawerLayout.openDrawer(mLeftDrawerView);
        }

        return true;
    }

    public boolean defaultRightDrawerToggleSelected() {
        if (mDrawerLayout.isDrawerOpen(mRightDrawerView)) {
            mDrawerLayout.closeDrawer(mRightDrawerView);
        } else {
            mDrawerLayout.openDrawer(mRightDrawerView);
        }

        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        leftDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        leftDrawerToggle.onConfigurationChanged(newConfig);
    }
}
