package gaongil.safereturnhome.model;

public enum MessageType {
	
	NORMAL(1),
	ANNOUNCE(2),
	WARN(3),
	URGENT(4);
	
	private final int type;
	
	public MessageType valueOf(int i) {
		return this;
	}
	
	MessageType(int type) {
		this.type = type;
	}
	
	public int intValue() {
		return this.type;
	}
}
