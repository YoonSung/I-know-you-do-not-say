package gaongil.support.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * {@link LoginUser} Inject LoginUser Instance in controller method parameter with @LoginUser
 */
public class LoginUserHandlerMethodArgument implements HandlerMethodArgumentResolver {
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		//LoginUser loginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class);
		
		//TODO Get CurrentLogin User and return 
		
		//if (loginUserAnnotation.required() && )
		
		return null;
	}

}
