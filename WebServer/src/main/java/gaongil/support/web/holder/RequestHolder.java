package gaongil.support.web.holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gaongil.support.web.status.ApplicationCode;

public class RequestHolder {
	
	private static final Logger log = LoggerFactory.getLogger(RequestHolder.class);
	
	public void saveApplicationCode(ApplicationCode status) {
		log.debug("saved value : {}", status.intValue());
		RequestHolderUtil.setAttribute(RequestHolderKey.APPLICATION_CODE, status.intValue());
	}
	
	public ApplicationCode getApplicationCode() {
		Object value = RequestHolderUtil.getAttribute(RequestHolderKey.APPLICATION_CODE);
		
		if ( null != value) {
			log.debug("RequestHolder Application Code value : {}", value);
			return ApplicationCode.getApplicationCode((int)value);
		}
		return null;
	}
	
}
