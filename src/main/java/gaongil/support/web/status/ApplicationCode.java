package gaongil.support.web.status;

import gaongil.support.exception.NotFoundException;
import gaongil.support.exception.WithPermissionException;
import gaongil.support.exception.WrongParameterException;

public enum ApplicationCode {
	OK 							(200, "요청성공"),
	CREATE_NEWDATA 				(201, "새로운 데이터를 생성했습니다"),
	WRONG_PARAMETER 			(400, "잘못된 Parameter 요청입니다", new WrongParameterException()),
	PERMISSION_DENIED			(403, "권한이 없습니다", new WithPermissionException()),
	NOT_FOUND 					(404, "요청자원을 찾지 못했습니다", new NotFoundException()),
	UNEXPECTED					(500, "예기치 못한 에러가 발생했습니다")

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
	
	ApplicationCode(int code, String description) {
		this.intValue = code;
		this.description = description;
	}
	
	ApplicationCode(int code, String description, RuntimeException exception) {
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
