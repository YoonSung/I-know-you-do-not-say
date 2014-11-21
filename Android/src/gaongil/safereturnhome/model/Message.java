package gaongil.safereturnhome.model;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.exception.InvalidMessageException;
import gaongil.safereturnhome.support.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Message {
	
	private int groupId;
	private int writerId;
	private String content;
	private Date date;
	private MessageType type;
	private boolean isReceived;
	
	private SimpleDateFormat writtenTimeFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
	
	public Message(int groupId, int writerId, String content, Date date,
			MessageType type, boolean isReceived) {
		this.groupId = groupId;
		this.writerId = writerId;
		this.content = content;
		this.date = date;
		this.type = type;
		this.isReceived = isReceived;
	}
	
	//getImageResourceId
	public View getView(Context context) throws InvalidMessageException {
		if (this.type == null)
			throw new InvalidMessageException();
		
		int layoutId = isReceived ? R.layout.chat_message_rcv : R.layout.chat_message_sent;
        LayoutInflater li = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate (layoutId, null);
        
        //TODO Get User Profile Image
        
        // message set
        TextView content = (TextView) view.findViewById(R.id.message_content);
        content.setText(this.content);
        
        // writtenTime set
        TextView date = (TextView) view.findViewById(R.id.message_time);
        date.setText(writtenTimeFormat.format(this.date));
        
        // bubbleImage set
        LinearLayout profile = (LinearLayout) view.findViewById(R.id.message_linearlayout);
        profile.setBackgroundResource(this.type.getResourceId(this.isReceived));
        
		return view;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public int getWriterId() {
		return writerId;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getContent() {
		return content;
	}
	
	public boolean isReceived() {
		return isReceived;
	}
}
