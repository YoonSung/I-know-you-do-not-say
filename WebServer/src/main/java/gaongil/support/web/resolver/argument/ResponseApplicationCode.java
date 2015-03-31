package gaongil.support.web.resolver.argument;

import org.springframework.beans.factory.annotation.Autowired;

import gaongil.support.web.holder.RequestHolder;
import gaongil.support.web.status.ApplicationCode;

public class ResponseApplicationCode {

	@Autowired
	private RequestHolder requestHolder;
	
	public void set(ApplicationCode code) {
		requestHolder.saveApplicationCode(code);
	}

	public ApplicationCode get() {
		return requestHolder.getApplicationCode();
	}
	
}