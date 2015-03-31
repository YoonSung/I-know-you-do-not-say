package gaongil.support.web.status;

public enum ApplicationCode {
	OK (200, "ok"),
	CREATE (201, "create"),
	NOT_FOUND (400, "not found")
	
	//TODO add code
	;
	
	private int intValue;
	private String description;
	
	public String description() {
		return this.description;
	}
	
	public int intValue() {
		return this.intValue;
	}
	
	private ApplicationCode(int code, String description) {
		this.intValue = code;
		this.description = description;
	}
	
	public static ApplicationCode getApplicationCode(int value) {
		return getApplicationCodeFromIntValue(value);
	}

	private static ApplicationCode getApplicationCodeFromIntValue(int value) {
		for (ApplicationCode code : ApplicationCode.values()) {
			if (value == code.intValue)
				return code;
		}
		return NOT_FOUND;
	}
}
