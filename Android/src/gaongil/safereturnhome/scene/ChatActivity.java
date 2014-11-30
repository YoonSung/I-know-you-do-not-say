package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.MessageData;
import gaongil.safereturnhome.model.MessageType;
import gaongil.safereturnhome.support.ChatAdapter;

import java.util.ArrayList;
import java.util.Date;

import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends CustomActivity implements OnClickListener {
	
	/************************************************************************
	 * Forground Check
	 */
	private static boolean VISIBILITY = false;
	public static boolean isVisible() {
		return ChatActivity.VISIBILITY;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ChatActivity.VISIBILITY = true;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		ChatActivity.VISIBILITY = false;
	}
	/*
	 * Forground Check
	 *************************************************************************/
	
	/**
	 * MainContents
	 */
	// Message List
	private ArrayList<MessageData> mMessageList;
	// Chat Custom Adapter.
	private ChatAdapter mChatAdapter;
	// EditText to compose the messages
	private EditText mEditText;
	// Button to submit new message
	private Button mBtnSend;
	// Control Keyboard Panel
	private InputMethodManager inputMethodManager;
	private ActionBarDrawerToggle mDrawerToggle;
	
	/**
	 * The drawer layout
	 */
	private DrawerLayout mDrawerLayout;

	/**
	 * Left drawer
	 */
	private View mLeftDrawerView;

	/**
	 * Right drawer
	 */
	private View mRightDrawerView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_chat);
	    loadMessageList();
	    init();
	    
	    setupActionBar("test");
	    setupDrawer();
	    setupLeftDrawer();
	    setupRightDrawer();
	}

	private void setupActionBar(String groupName) {
		super.setupActionBar(R.drawable.ic_location);
		centerActionBarTitle(groupName);
	}
	
	private void centerActionBarTitle(String groupName) {

		ActionBar actionBar = getActionBar();
		if (actionBar == null)
			return;
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(groupName);
		
        int titleId = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        }
        else
        {
            // This is the id is from your app's generated R class when ActionBarActivity is used 
            // for SupportActionBar
            titleId = R.id.action_bar_title;
        }

        // Final check for non-zero invalid id
        if (titleId > 0)
        {
            TextView titleTextView = (TextView) findViewById(titleId);
            DisplayMetrics metrics = getResources().getDisplayMetrics();

            // Fetch layout parameters of titleTextView (LinearLayout.LayoutParams : Info from HierarchyViewer)
            LinearLayout.LayoutParams txvPars = (android.widget.LinearLayout.LayoutParams) titleTextView.getLayoutParams();
            txvPars.gravity = Gravity.CENTER_HORIZONTAL;
            txvPars.width = metrics.widthPixels;
            titleTextView.setLayoutParams(txvPars);

            titleTextView.setGravity(Gravity.CENTER);
        }
    }

	private void init() {
		 // Chatting ListView Setting
	    ListView list = (ListView)findViewById(R.id.chat_list);
	    mChatAdapter = new ChatAdapter(ChatActivity.this, mMessageList);
	    
	    list.setAdapter(mChatAdapter);
	    list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
	    list.setStackFromBottom(true);
	    
	    // Chat Input Box Init
	    mEditText = (EditText) findViewById(R.id.chat_edt);
	    
	    // Submit Button init
	    mBtnSend = (Button) findViewById(R.id.chat_btn_send);
	    mBtnSend.setOnClickListener(this);
	    
	    // inputMethodManager init
	    inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	private void loadMessageList() {
		
		// TODO getDataFrom Server or Local DB
		mMessageList = new ArrayList<MessageData>();
		
		mMessageList.add(new MessageData(
				1, 1, "test", new Date(),  MessageType.NORMAL, true 
		));
	}

	private void sendMessage() {
		
		if (mEditText.length() == 0)
			return;
		
		inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		
		//TODO getGroupId & UserId & MessageType & etc..
		MessageData newMessage = new MessageData(
				1, 
				1, 
				mEditText.getText().toString(), 
				new Date(), 
				MessageType.NORMAL, 
				false
		);
		
		//TODO Post Message, and Send Success, then add chatList
		mMessageList.add(newMessage);
		
		mChatAdapter.notifyDataSetChanged();
		mEditText.setText(null);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chat_btn_send:
			sendMessage();
			break;
		}
	}

	@Override
	protected boolean onRightDrawerToggleSelected(MenuItem item) {
		return super.defaultRightDrawerToggleSelected();
	}

	@Override
	protected void setupDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_chat_layout);
		mLeftDrawerView = (View) findViewById(R.id.drawer_chat_left);
		mRightDrawerView = (View) findViewById(R.id.drawer_chat_right);
		LinearLayout mainContentLayout = (LinearLayout) findViewById(R.id.chat_content_layout);
		
		mDrawerToggle = super.getActionBarDrawerToggle(
				this, 
				mDrawerLayout, 
				mLeftDrawerView, 
				mRightDrawerView, 
				mainContentLayout, 
				R.drawable.ic_location,
				R.menu.chat
		);
		
		super.setDrawerLayoutOptions(mDrawerLayout, mDrawerToggle);
		
	}

	@Override
	protected void setupLeftDrawer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setupRightDrawer() {
		// TODO Auto-generated method stub
		
	}
}
