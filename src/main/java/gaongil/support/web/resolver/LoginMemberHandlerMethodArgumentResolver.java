package gaongil.support.web.resolver;

import gaongil.domain.Member;
import gaongil.security.LoginRequiredException;
import gaongil.service.MemberService;
import gaongil.support.web.resolver.argument.LoginMember;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * {@link LoginMember} Inject LoginUser Instance in acceptance_test method parameter with @LoginMember
 */
public class LoginMemberHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	private static final Logger log = LoggerFactory.getLogger(LoginMemberHandlerMethodArgumentResolver.class);
	
	@Autowired
	private MemberService memberService;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginMember.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {

		LoginMember loginMemberAnnotation = parameter.getParameterAnnotation(LoginMember.class);
		
		Member loginMember = memberService.getCurrentLoginMember();
		
		log.debug("@LoginMember Injection Success : {}", loginMember != null);
		
		if (loginMemberAnnotation.required() && loginMember == null)
			throw new LoginRequiredException();
		
		return loginMember;
	}

}
