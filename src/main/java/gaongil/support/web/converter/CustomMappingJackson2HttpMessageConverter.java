package gaongil.support.web.converter;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	private static final Logger log = LoggerFactory.getLogger(CustomMappingJackson2HttpMessageConverter.class);

	@Autowired
	ResponseMessageConverter responseMessageConverter;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator generator = this.objectMapper.getFactory().createGenerator(outputMessage.getBody(), encoding);
		try {
			object = responseMessageConverter.getJsonObject(object, outputMessage);
			this.objectMapper.writeValue(generator, object);
			
			generator.flush();
		} catch (JsonProcessingException ex) {
			log.error("message object : {}", object);
			throw new HttpMessageNotWritableException("Could not write content: " + ex.getMessage(), ex);
		}
	}
	
	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return super.readInternal(clazz, inputMessage);
	}

}
