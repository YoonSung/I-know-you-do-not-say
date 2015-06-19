package gaongil.controller;

import gaongil.domain.Member;
import gaongil.domain.User;
import gaongil.security.SecurityRememberMeService;
import gaongil.security.UserTokenGenerator;
import gaongil.service.UserService;
import gaongil.support.web.resolver.argument.LoginMember;
import gaongil.support.web.resolver.argument.Response;
import gaongil.support.web.resolver.argument.ResponseApplicationCode;
import gaongil.support.web.status.ApplicationCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityRememberMeService securityRememberMeService;

	@RequestMapping("")
	public String home() {
		log.info("/ : ROOT Request");
		return "Server Operation Success!";
	}
	
	@RequestMapping("/test")
	public Member test(@LoginMember Member member, @Response ResponseApplicationCode responseCode ) {
		responseCode.set(ApplicationCode.CREATE_NEWDATA);
		return member;
	}

	@RequestMapping("/user")
	public void userLogin(@Response ResponseApplicationCode code,  HttpServletRequest request,  HttpServletResponse response) {

		User user=  new User();
		user.setPhoneNumber("01099258547");
		user.setRegId("testRegId");
		user.setPid(2L);
		user.setUuid("testUuid");

		if (user.canRegistable()) {
			User createdUser = userService.create(user);
			log.debug("regId : {}, phoneNumber : {}", user.getRegId(), user.getPhoneNumber());

			//TODO Add Retry Template
			securityRememberMeService.setTokenToUser(request, response, new UserTokenGenerator(createdUser.getPid(), createdUser.getUuid()));

			code.set(ApplicationCode.CREATE_NEWDATA);
		} else {
			code.set(ApplicationCode.UNEXPECTED);
		}
	}

	@RequestMapping("/error")
	public String error() {
		throw new IllegalArgumentException("Username is required.");
	}
}