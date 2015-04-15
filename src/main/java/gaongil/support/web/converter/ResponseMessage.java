package gaongil.support.web.converter;

import gaongil.support.web.status.ApplicationCode;

/**
 * {@link CustomMappingJackson2HttpMessageConverter}
 * ResponseBody Message Data
 */
public class ResponseMessage {
	
	//application code
	private int code = ApplicationCode.OK.intValue();
	
	//response message
	private String message = ApplicationCode.OK.description();
	
	//response data
	private Object data;
	
	public ResponseMessage(ApplicationCode appCode, Object data) {
		
		this.data = data;

		if (appCode==null)
			return;
		
		this.code = appCode.intValue();
		this.message = appCode.description();
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}
}
