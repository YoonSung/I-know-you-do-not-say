package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity {
	
	private final String TAG = MainActivity.class.getSimpleName();

	/// The drawer layout
	private DrawerLayout mDrawerLayout;
	
	// drawer
	private View mLeftDrawerView;
	private View mRightDrawerView;
	
	// The drawer toggle
	private ActionBarDrawerToggle mDrawerToggle;

	// Test
	private float lastTranslate = 0.0f;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
	    setupDrawer();
	    setupActionBar();
	}

	private void setupActionBar() {
		ActionBar mActionBar = getActionBar();
		if (mActionBar == null)
			return;
		
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayUseLogoEnabled(true);
		mActionBar.setLogo(R.drawable.ic_menu);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_bg));
		
		//Its default system menu graphical icon
		//mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
	}

	private void setupDrawer() {
		
		//test
		final LinearLayout frame = (LinearLayout) findViewById(R.id.test);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
		
		mLeftDrawerView = (View) findViewById(R.id.drawer_main_left);
		mRightDrawerView = (View) findViewById(R.id.drawer_main_right);
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_menu, R.string.drawer_main_leftToggle_open,
				R.string.drawer_main_leftToggle_close) {
			@Override
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(getResources().getString(R.string.drawer_main_leftToggle_close));
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(getResources().getString(R.string.drawer_main_leftToggle_open));
			}
			
			@SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset) {
				
				if (drawerView.getId() == R.id.drawer_main_right) {
					slideOffset *= -1;
				}
				
                float moveFactor = (mDrawerLayout.getWidth() * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
                {
                    frame.setTranslationX(moveFactor);
                }
                else
                {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    frame.startAnimation(anim);

                    lastTranslate = moveFactor;
                }
            }
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.closeDrawers();
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
			mDrawerLayout.openDrawer(mRightDrawerView);
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
