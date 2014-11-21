package gaongil.safereturnhome.model;

import gaongil.safereturnhome.R;

public enum MessageType {
	
	NORMAL(1, R.drawable.chat_blue_bubble, R.drawable.chat_gray_bubble),
	ANNOUNCE(2, R.drawable.chat_blue_bubble, R.drawable.chat_gray_bubble),
	WARN(3, R.drawable.chat_blue_bubble, R.drawable.chat_gray_bubble),
	URGENT(4, R.drawable.chat_blue_bubble, R.drawable.chat_gray_bubble);
	
	private final int type;
	private final int receiveResourceId;
	private final int sendResource;
	
	MessageType(int type, int sendResource, int receiveResource) {
		this.type = type;
		this.receiveResourceId = receiveResource;
		this.sendResource = sendResource;
	}
	
	int getResourceId(boolean isReceiveMessage) {
		return isReceiveMessage == true ? receiveResourceId : sendResource;
	}
	
	public int intValue() {
		return this.type;
	}
}
