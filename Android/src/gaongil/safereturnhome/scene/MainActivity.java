package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends FragmentActivity {
	
	/// The drawer layout
	private DrawerLayout mDrawerLayout;
	
	// drawer
	private View mLeftDrawerView;
	private View mRightDrawerView;
	
	// The drawer toggle
	private ActionBar mActionBar;
	private ActionBarDrawerToggle mDrawerToggle;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
	    setupDrawer();
	    setupActionBar();
	}

	private void setupActionBar() {
		mActionBar = getActionBar();
		if (mActionBar == null)
			return;
		
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayUseLogoEnabled(true);
		//mActionBar.setLogo(R.drawable.ic_drawer);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_bg));
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
	}

	private void setupDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
		
		mLeftDrawerView = (View) findViewById(R.id.drawer_main_left);
		mRightDrawerView = (View) findViewById(R.id.drawer_main_right);
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_main_leftToggle_open,
				R.string.drawer_main_leftToggle_close) {
			@Override
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(getResources().getString(R.string.drawer_main_leftToggle_close));
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(getResources().getString(R.string.drawer_main_leftToggle_open));
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
		if (mDrawerToggle.onOptionsItemSelected(item)) {
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
