package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

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
	
	private EditText mEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_chat);
	    
	    mEditText = (EditText) findViewById(R.id.chat_btn_send);
	    
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

}
