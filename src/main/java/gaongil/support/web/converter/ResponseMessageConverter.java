package gaongil.support.web.converter;

import gaongil.support.web.holder.RequestHolder;
import gaongil.support.web.status.ApplicationCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;

public class ResponseMessageConverter {

	private static final Logger log = LoggerFactory.getLogger(ResponseMessageConverter.class);

	@Autowired
	RequestHolder requestHolder;

	Object getJsonObject(Object object, HttpOutputMessage outputMessage) {
		
		ApplicationCode code = requestHolder.getApplicationCode();
		log.debug("response code : {}", code);
		return new ResponseMessage(code, object);
	}
}