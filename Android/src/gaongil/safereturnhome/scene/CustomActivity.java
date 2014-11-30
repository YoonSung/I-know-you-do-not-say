package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.support.Constant;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

@SuppressWarnings("deprecation")
public abstract class CustomActivity extends FragmentActivity{
	
	private ActionBarDrawerToggle leftDrawerToggle;
	private int rightDrawerToggleDrawableId;
	private DrawerLayout mDrawerLayout;
	private View mRightDrawerView;

	protected abstract boolean onRightDrawerToggleSelected(MenuItem item);
	protected abstract void setupDrawer();
	protected abstract void setupLeftDrawer();
	protected abstract void setupRightDrawer();
	
	protected void setupActionBar(int leftDrawerToggleDrawableId) {
		ActionBar actionBar = getActionBar();
		if (actionBar == null)
			return;
		
		//mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setLogo(leftDrawerToggleDrawableId);
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_color_blue)));
		
		//Its default system menu graphical icon
		//mActionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}
	
	//TODO DELETE drawableId
	protected ActionBarDrawerToggle getActionBarDrawerToggle(
																	final Activity activity, 
																	final DrawerLayout mDrawerLayout,
																	final View mLeftDrawerView, 
																	final View mRightDrawerView,
																	final LinearLayout mainContentLayout,
																	final int drawerToggleDrawableId,
																	final int rightDrawerToggleDrawableId) {
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		
		this.rightDrawerToggleDrawableId = rightDrawerToggleDrawableId;
		
		ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout,
				drawerToggleDrawableId, 0, 0) {

			// It's for lastTranslate Saved Variation
			private float lastTranslate = 0.0f;

			@Override
			public void onDrawerClosed(View view) {
			}

			@Override
			public void onDrawerOpened(View drawerView) {
			}

			@SuppressLint("NewApi")
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
			} //onDrawerSlide
		}; //new ActionBarDrawerToggle
		
		
		//saveData in Parent Because OptionItemSelected or etc actions
		this.mDrawerLayout = mDrawerLayout;
		this.leftDrawerToggle = actionBarDrawerToggle;
		this.mRightDrawerView = mRightDrawerView;
		
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
			}
			else
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
		//Left Drawer Toggle Clicked
		if (leftDrawerToggle.onOptionsItemSelected(item)) {
			return true;
			
		//Right Drawer Toggle Clicked
		} else {
			return onRightDrawerToggleSelected(item);
		}

		//return super.onOptionsItemSelected(item);		
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
