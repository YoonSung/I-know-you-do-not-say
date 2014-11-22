package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.exception.InvalidMessageException;
import gaongil.safereturnhome.model.Message;
import gaongil.safereturnhome.model.MessageType;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatActivity extends FragmentActivity implements OnClickListener {
	
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
	
	private final String TAG = ChatActivity.class.getSimpleName();
	
	// Message List
	private ArrayList<Message> mMessageList;
	
	// Chat Custom Adapter.
	private ChatAdapter mChatAdapter;
	
	// EditText to compose the messages
	private EditText mEditText;
	
	// Button to submit new message
	private Button mBtnSend;
	
	// Control Keyboard Panel
	private InputMethodManager inputMethodManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_chat);
	    
	    loadMessageList();
	    init();
	}

	private void init() {
		 // Chatting ListView Setting
	    ListView list = (ListView)findViewById(R.id.chat_list);
	    mChatAdapter = new ChatAdapter();
	    
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
		mMessageList = new ArrayList<Message>();
		
		mMessageList.add(new Message(
				1, 1, "test", new Date(),  MessageType.NORMAL, true 
		));
	}

	private void sendMessage() {
		
		if (mEditText.length() == 0)
			return;
		
		inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		
		//TODO getGroupId & UserId & MessageType & etc..
		Message newMessage = new Message(
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

	/************************************************************************
	 * Custom ChatAdapter
	 */
	private class ChatAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mMessageList.size();
		}

		@Override
		public Message getItem(int index) {
			return mMessageList.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int position, View view, ViewGroup viewGroup) {
			Message message = getItem(position);
			
			// Type check and mapping proper XML Layout
			// Input data to layout component
			try {
				return message.getView(ChatActivity.this);
				
			} catch (InvalidMessageException e) {
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
				
				return null;
			}
		}
	}
	/*
	 * Custom ChatAdapter
	 ************************************************************************/
}
