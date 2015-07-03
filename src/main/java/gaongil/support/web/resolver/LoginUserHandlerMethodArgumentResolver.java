package gaongil.support.web.resolver;

import gaongil.domain.Member;
import gaongil.domain.User;
import gaongil.security.LoginRequiredException;
import gaongil.service.UserService;
import gaongil.support.web.resolver.argument.LoginMember;
import gaongil.support.web.resolver.argument.LoginUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * {@link LoginUser} Inject LoginUser Instance in acceptance_test method parameter with @LoginUser
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private static final Logger log = LoggerFactory.getLogger(LoginMemberHandlerMethodArgumentResolver.class);

	@Autowired
	private UserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {

		LoginUser loginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class);

		User loginUser = userService.getCurrentLoginUser();

		log.debug("@LoginUser Injection Success : {}", loginUser != null);

		if (loginUserAnnotation.required() && loginUser == null)
			throw new LoginRequiredException();

		return loginUser;
	}

}
