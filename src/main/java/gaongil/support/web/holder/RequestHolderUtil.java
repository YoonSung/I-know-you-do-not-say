package gaongil.support.web.holder;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 
 * @author yoon
 * save data in thread per request.
 * It is thread safe and useful 
 * It used to data transferring between Controller to View Logic
 */
class RequestHolderUtil {
	
	 /**
     * get Attribute value from RequestContextHolder
     *
     * @param RequestHolderKey specific key
     * @return Object value
     */
    static Object getAttribute(RequestHolderKey key) {
        return RequestContextHolder.getRequestAttributes().getAttribute(key.name(), RequestAttributes.SCOPE_REQUEST);
    }
 
    /**
     * set Attribute Value to RequestContextHolder
     *
     * @param RequestHolderKey specific key
     * @param Object  value
     * @return void
     */
    static void setAttribute(RequestHolderKey key, Object object) {
        RequestContextHolder.getRequestAttributes().setAttribute(key.name(), object, RequestAttributes.SCOPE_REQUEST);
    }
 
    /**
     * remove Attribute Value from RequestContextHolder
     *
     * @param RequestHolderKey specific key
     * @return void
     */
    static void removeAttribute(RequestHolderKey key) {
        RequestContextHolder.getRequestAttributes().removeAttribute(key.name(), RequestAttributes.SCOPE_REQUEST);
    }
  
}
