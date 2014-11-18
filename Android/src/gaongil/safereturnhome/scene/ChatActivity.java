package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.Message;
import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
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
	
	// Message List
	private ArrayList<Message> mMessageList;
	
	// Chat Custom Adapter.
	private ChatAdapter mChatAdapter;
	
	// EditText to compose the messages
	private EditText mEditText;
	
	// Control Keyboard Panel
	private InputMethodManager inputMethodManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_chat);
	    
	    init();
	    loadMessageList();
	}

	private void init() {
		 // Chatting ListView Setting
	    ListView list = (ListView)findViewById(R.id.chat_list);
	    mChatAdapter = new ChatAdapter();
	    
	    list.setAdapter(mChatAdapter);
	    list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
	    list.setStackFromBottom(true);
	    
	    // Chat Input Box Init
	    mEditText = (EditText) findViewById(R.id.chat_btn_send);		
	}

	private void loadMessageList() {
		// TODO getDataFrom Server or Local DB
	}

	private void sendMessage() {
		
		if (mEditText.length() == 0)
			return;
		
		inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		
		String message = mEditText.getText().toString();
		//TODO Create Message Instance
		//TODO Post Message, and Send Success, then add chatList
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
	private class ChatAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return mMessageList.size();
		}

		@Override
		public Message getItem(int index)
		{
			return mMessageList.get(index);
		}

		@Override
		public long getItemId(int index)
		{
			return index;
		}

		@Override
		public View getView(int position, View view, ViewGroup viewGroup)
		{
			Message message = getItem(position);
			
			//TODO Type check and mapping proper XML Layout
			//TODO Input data to layout component

			return view;
		}
	}
	/*
	 * Custom ChatAdapter
	 ************************************************************************/
}
