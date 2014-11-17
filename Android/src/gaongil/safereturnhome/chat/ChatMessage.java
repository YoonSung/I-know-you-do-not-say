package gaongil.safereturnhome.chat;

public class ChatMessage {

	private int type;
	private String name = "정윤성";
	private String msg;
	private String timeFormat;
	private String dayFormat;
	
	public ChatMessage(int type, String msg, String timeFormat) {
		this.type = type;
		this.msg = msg;
		this.timeFormat = timeFormat;
	}

	public ChatMessage(int type, String dayFormat) {
		this.type = type;
		this.dayFormat = dayFormat;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return msg;
	}
	
	public String getTimeFormat() {
		return timeFormat;
	}
	
	public String getDayFormat() {
		return dayFormat;
	}
}
