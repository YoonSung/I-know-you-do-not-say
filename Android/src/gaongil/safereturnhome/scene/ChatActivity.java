package gaongil.safereturnhome.scene;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.chat.ChatMessage;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends FragmentActivity implements OnClickListener {
	public static Handler mHandler = new Handler();
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
	
	private MessageAdapter mMessageAdapter;
	private ListView mMessageList;
	private SimpleDateFormat mDateFormat;
	private SimpleDateFormat mTimeFormat;

	private EditText edtMsgBox;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_chat);
	    
	    
	    
	}

	public void init() {
		//Initialize variables start
	    Button btnMsgSend = (Button)findViewById(R.id.chat_btn_send);
	    btnMsgSend.setOnClickListener(this);
	    
	    //test start
	    Button btnSendTest= (Button)findViewById(R.id.chatroom_btnSendTest);
	    btnSendTest.setOnClickListener(this);
	    
	    Button btnReceiveTest = (Button)findViewById(R.id.chatroom_btnReceiveTest);
	    btnReceiveTest.setOnClickListener(this);
	    
	    Button btnDateTest= (Button)findViewById(R.id.chatroom_btnDate);
	    btnDateTest.setOnClickListener(this);
	    //test end
	    
	    
	    edtMsgBox = (EditText)findViewById(R.id.chat_edttext);
	    mMessageList = (ListView)findViewById(R.id.chat_list);
	    mMessageList.setDivider(null);
	    
	    
	    ArrayList<ChatMessage> msgArray = new ArrayList<ChatMessage>();
	    mMessageAdapter = new MessageAdapter(msgArray);
	    mMessageList.setAdapter(mMessageAdapter);
	    
	    mDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
	    mTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
	    //Initialize variables end
	}
	
	@Override
	public void onClick(View v) {
		
		ChatMessage msgData = null;
		
		switch (v.getId()) {
		
		//type 0 is user send, type 1 is user receive, type 2 is date display 
		case R.id.chat_btn_send:
			msgData = new ChatMessage(0, edtMsgBox.getText().toString(), mTimeFormat.format(new Date()));
			break;
			
		case R.id.chatroom_btnSendTest:
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(1000);
						//Common.sendHttp("/message/create", edtMsgBox.getText().toString());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			msgData = new ChatMessage(0, edtMsgBox.getText().toString(), mTimeFormat.format(new Date()));
			edtMsgBox.setText("");
			break;
		case R.id.chatroom_btnReceiveTest:
			msgData = new ChatMessage(1, edtMsgBox.getText().toString(), mTimeFormat.format(new Date()));
			break;
		case R.id.chatroom_btnDate:
			msgData = new ChatMessage(2, mDateFormat.format(new Date()));
			break;
		}
		
		//add instance to adapter
		mMessageAdapter.add(msgData);
		
		//scroll focus add area
		mMessageList.smoothScrollToPosition(mMessageAdapter.getCount());
	}
	
	
	//inner class adapter 
	public class MessageAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		private ArrayList<ChatMessage> msgList;
		
		public MessageAdapter(ArrayList<ChatMessage> msgList) {
			this.msgList = msgList; 
			inflater = (LayoutInflater) getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		}

		public void add(ChatMessage msgData) {
			msgList.add(msgData);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return msgList.size();
		}

		@Override
		public ChatMessage getItem(int position) {
			return msgList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return msgList.get(position).getType();
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			
			int type = getItemViewType(position);
			
			//convertview is current view instance. 
			if (convertView == null) {
				switch (type) {
					case 0:
						view = inflater.inflate(R.layout.chat_item0, null);
						break;
					case 1:
						view = inflater.inflate(R.layout.chat_item1, null);
						break;
					case 2:
						view = inflater.inflate(R.layout.chat_item2, null);
						break;
				}
			} else {
				view = convertView;
			}
			
			ChatMessage msgInstance = msgList.get(position);
			
			//date is not null
			if (msgInstance != null) {
				if (type == 0) {
					
					String message = msgInstance.getMessage(); 
					String time = msgInstance.getTimeFormat();
					
					TextView txtMsgSend, txtSendTime;
					txtMsgSend = (TextView)view.findViewById(R.id.chatroom_txtSendMsg);
					txtSendTime = (TextView)view.findViewById(R.id.chatroom_txtSendTime);
					
					if(message != null)
					txtMsgSend.setText(message);
					txtSendTime.setText(time);
				}
				
				if (type == 1) {
					TextView txtName, txtMsgReceive, txtReceiveTime;
					txtName = (TextView)view.findViewById(R.id.chatroom_txtName);
					txtMsgReceive = (TextView)view.findViewById(R.id.chatroom_txtReceiveMsg);
					txtReceiveTime = (TextView)view.findViewById(R.id.chatroom_txtReceiveTime);
					
					txtName.setText(msgInstance.getName());
					txtMsgReceive.setText(msgInstance.getMessage());
					txtReceiveTime.setText(msgInstance.getTimeFormat());
				}
				
				if (type == 2) {
					TextView txtDate = (TextView)view.findViewById(R.id.chatroom_txtDate);
					
					txtDate.setText(msgInstance.getDayFormat());
				}
			}
			
			return view;
		}
	}

}
