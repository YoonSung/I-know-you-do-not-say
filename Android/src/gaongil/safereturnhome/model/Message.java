package gaongil.safereturnhome.model;

import gaongil.safereturnhome.exception.InvalidMessageTypeException;

import java.sql.Date;

public class Message {
	
	private int groupId;
	private int writerId;
	private String content;
	private Date date;
	private MessageType type;
	private boolean isReceived;
	
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
	public int getBubbleResourceId() {
		
		if (this.type == null)
			throw new InvalidMessageTypeException();
		
		return this.type.getResourceId(isReceived);
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
