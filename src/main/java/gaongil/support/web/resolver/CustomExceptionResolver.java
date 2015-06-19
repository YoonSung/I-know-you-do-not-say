package gaongil.support.web.resolver;

import gaongil.support.web.converter.CustomMappingJackson2HttpMessageConverter;
import gaongil.support.web.holder.RequestHolder;
import gaongil.support.web.status.ApplicationCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

public class CustomExceptionResolver extends AbstractHandlerExceptionResolver {

	@Autowired
	private RequestHolder holder;

	@Autowired
	private CustomMappingJackson2HttpMessageConverter customResolver;

	private static final Logger log = LoggerFactory.getLogger(CustomExceptionResolver.class);
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		log.debug("doResolveException");
		exception.printStackTrace();
		
		holder.saveApplicationCode(ApplicationCode.getApplicationCode(exception));
		
		ServletWebRequest webRequest = new ServletWebRequest(request, response);
		try {
			customResolver.write("", MediaType.APPLICATION_JSON, new ServletServerHttpResponse(webRequest.getResponse()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return empty model and view to short circuit the iteration and to let
        //Spring know that we've rendered the view ourselves:
		return new ModelAndView();
	}
}
