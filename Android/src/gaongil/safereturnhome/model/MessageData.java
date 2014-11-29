package gaongil.safereturnhome.model;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.exception.InvalidMessageException;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.RoundedAvatarDrawable;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageData {
	
	private int groupId;
	private int writerId;
	private String content;
	private Date date;
	private MessageType type;
	private boolean isReceived;
	
	private SimpleDateFormat writtenTimeFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
	
	public MessageData(int groupId, int writerId, String content, Date date,
			MessageType type, boolean isReceived) {
		this.groupId = groupId;
		this.writerId = writerId;
		this.content = content;
		this.date = date;
		this.type = type;
		this.isReceived = isReceived;
	}
	
	//getImageResourceId
	public View getChatView(Context context, LayoutInflater layoutInflater) throws InvalidMessageException {
		if (this.type == null)
			throw new InvalidMessageException();
		
		int layoutId = isReceived ? R.layout.chat_message_rcv : R.layout.chat_message_sent;
        View view = layoutInflater.inflate (layoutId, null);
        
        //Test Image Load Start
        //TODO connect MessageData Resource
        //TODO getRoundedImage Method is to be static
        BitmapDrawable bImage = (BitmapDrawable) context.getResources().getDrawable(R.drawable.test_profile);
        RoundedAvatarDrawable rondedAvatarImg = new RoundedAvatarDrawable(bImage.getBitmap());
        //Test Image Load End
        
        ImageView profile = (ImageView) view.findViewById(R.id.message_img_profile);  
        profile.setImageDrawable(new RoundedAvatarDrawable(rondedAvatarImg.getBitmap()));
        
        // message set
        TextView content = (TextView) view.findViewById(R.id.message_content);
        content.setText(this.content);
        
        // writtenTime set
        TextView receivedTime = (TextView) view.findViewById(R.id.message_time);
        receivedTime.setText(writtenTimeFormat.format(this.date));
        
        // bubbleImage set
        LinearLayout messageBubble = (LinearLayout) view.findViewById(R.id.message_linearlayout);
        messageBubble.setBackgroundResource(this.type.getResourceId(this.isReceived));
        
		return view;
	}
	
	public void getTimeLineView(Context context, LayoutInflater layoutInflater) throws InvalidMessageException {
		if (this.type == null)
			throw new InvalidMessageException();
		
		View view = layoutInflater.inflate (R.layout.timeline_list_row, null);
		
        //Test Image Load Start
        //TODO connect MessageData Resource
        //TODO getRoundedImage Method is to be static
        BitmapDrawable bImage = (BitmapDrawable) context.getResources().getDrawable(R.drawable.test_profile);
        RoundedAvatarDrawable rondedAvatarImg = new RoundedAvatarDrawable(bImage.getBitmap());
        //Test Image Load End
        
        
        ImageView profile = (ImageView) view.findViewById(R.id.drawer_main_right_img_profile);  
        profile.setImageDrawable(new RoundedAvatarDrawable(rondedAvatarImg.getBitmap()));
        
        // message set
        TextView message = (TextView) view.findViewById(R.id.drawer_main_right_txt_message);
        message.setText(this.content);
        
        // writtenTime set
        TextView receivedTime = (TextView) view.findViewById(R.id.drawer_main_right_txt_receivedtime);
        receivedTime.setText(writtenTimeFormat.format(this.date));
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
