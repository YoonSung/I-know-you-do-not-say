package gaongil.support.web.status;

import gaongil.support.exception.NotFoundException;

public enum ApplicationCode {
	OK 						(200, "ok"),
	CREATE 				(201, "create"),
	NOT_FOUND 		(400, "not found", new NotFoundException()),
	UNEXPECTED		(500, "unexpected")
	
	//TODO add code
	;
	
	private int intValue;
	private String description;
	private Exception exception;
	
	
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
	
	private ApplicationCode(int code, String description, RuntimeException exception) {
		this.intValue= code;
		this.description = description;
		this.exception = exception;
	}
	
	public static ApplicationCode getApplicationCode(int value) {
		return getApplicationCodeFromIntValue(value);
	}

	private static ApplicationCode getApplicationCodeFromIntValue(int value) {
		for (ApplicationCode code : ApplicationCode.values()) {
			if (value == code.intValue)
				return code;
		}
		return null;
	}

	public static ApplicationCode getApplicationCode(Exception exception) {
		return getApplicationCodeFromException(exception);
	}
	
	private static ApplicationCode getApplicationCodeFromException(Exception exception) {
		
		for (ApplicationCode code : ApplicationCode.values()) {
			Exception ex = code.exception;
			
			if (ex != null && exception.getClass() == ex.getClass())
				return code;
		}
		
		return UNEXPECTED;
	}
}
