package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.Group;
import gaongil.safereturnhome.support.Constant;

import java.io.File;
import java.util.ArrayList;

import com.soundcloud.android.crop.Crop;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	/// The drawer layout
	private DrawerLayout mDrawerLayout;
	
	// drawer
	private View mRightDrawerView;
	private View mLeftDrawerView;
	
	// The drawer toggle
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
	    test();
	    init();
	    setupDrawer();
	    setupActionBar();
	    
	    //TODO
	    //setupUserInformation();
	    //setupSensorInfo();
	    
	    setupGroupInfo();
	}

	private void init() {
		Button btnAddGroup = (Button) findViewById(R.id.main_btn_addgroup);
		
		//Test Start
		resultView = (ImageView) findViewById(R.id.main_img_test);
		btnTest2 = (Button) findViewById(R.id.main_btn_test2);
		//Test end
		btnTest2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Test Start YOONSUNG 2014. 11. 24
				//TODO DELTE
				//
				resultView.setImageDrawable(null);
				Crop.pickImage(MainActivity.this);
				//Test End YOONSUNG 2014. 11. 24
			}
		});
		
		btnAddGroup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(MainActivity.this, GroupActivity.class));
				startActivity(new Intent(MainActivity.this, ContactsActivity.class));
			}
		});
	}

	//Test Start YOONSUNG 2014. 11. 24
	ImageView resultView;
	Button btnTest2;
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
        if (resultCode == RESULT_OK) {
            resultView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //Test End YOONSUNG 2014. 11. 24

	
	private void setupGroupInfo() {
		
		//TODO GetData From DB.
		//TODO DELETE Initialize Declation. Test GroupData
		ArrayList<Group> groupList = new ArrayList<Group>();
		
		//If Group is Null, Create View for adding new group 
		if (groupList.size() == 0) {
			
		}
		
	}

	private void test() {
		Button btnMoveChatRoom = (Button) findViewById(R.id.main_btn_test1);
		btnMoveChatRoom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ChatActivity.class));
			}
		});
	}

	private void setupActionBar() {
		ActionBar mActionBar = getActionBar();
		if (mActionBar == null)
			return;
		
		//mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayUseLogoEnabled(true);
		mActionBar.setLogo(R.drawable.ic_menu);
		mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_color_blue)));
		
		//Its default system menu graphical icon
		//mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
	}

	private void setupDrawer() {
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
		
		mLeftDrawerView = (View) findViewById(R.id.drawer_main_left);
		mRightDrawerView = (View) findViewById(R.id.drawer_main_right);
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_menu, 0, 0) {
			
			//It's for lastTranslate Saved Variation
			private float lastTranslate = 0.0f;
			LinearLayout mainContentLayout = (LinearLayout) findViewById(R.id.main_content_layout);
			
			@Override
			public void onDrawerClosed(View view) {
			}

			@Override
			public void onDrawerOpened(View drawerView) {
			}
			
			@SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset) {
				
				//Its Drawer action Conflict Prevent Code
				if (drawerView == mLeftDrawerView) {
					mDrawerLayout.closeDrawer(mRightDrawerView);
				} else {
					mDrawerLayout.closeDrawer(mLeftDrawerView);
				}
				
				if (drawerView.getId() == R.id.drawer_main_right) {
					//if rightDrawer Action, Opposite Direction Set
					slideOffset *= -1;
				}
				
                float moveFactor = (mDrawerLayout.getWidth() * Constant.DRAWER_SLIDE_WIDTH_RATE* slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
                {
                    mainContentLayout.setTranslationX(moveFactor);
                }
                else
                {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setFillAfter(true);
                    mainContentLayout.startAnimation(anim);

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
			if (mDrawerLayout.isDrawerOpen(mRightDrawerView)) {
				mDrawerLayout.closeDrawer(mRightDrawerView);
			} else {
				mDrawerLayout.openDrawer(mRightDrawerView);
			}
			
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
