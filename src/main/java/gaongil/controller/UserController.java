package gaongil.controller;

import gaongil.domain.User;
import gaongil.service.UserService;
import gaongil.support.web.resolver.argument.Response;
import gaongil.support.web.resolver.argument.ResponseApplicationCode;
import gaongil.support.web.status.ApplicationCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	UserService userService;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void create(User user, @Response ResponseApplicationCode code) {

		if (user.canRegistable()) {
			userService.create(user);
			log.debug("regId : {}, phoneNumber : {}", user.getRegId(), user.getPhoneNumber());
			code.set(ApplicationCode.CREATE_NEWDATA);

		} else {
			code.set(ApplicationCode.UNEXPECTED);
		}
	}
}
