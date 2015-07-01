package controller;

import gaongil.domain.User;
import gaongil.dto.UserDTO;
import gaongil.security.SecurityRememberMeService;
import gaongil.security.UserTokenGenerator;
import gaongil.service.UserService;
import gaongil.support.web.resolver.argument.Response;
import gaongil.support.web.resolver.argument.ResponseApplicationCode;
import gaongil.support.web.status.ApplicationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityRememberMeService securityRememberMeService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String create(@RequestBody UserDTO userForm, @Response ResponseApplicationCode code) {

		log.debug("UserForm : {}", userForm.toString());

		if (userForm!= null && userForm.canRegistable()) {
			User createdUser = userService.create(userForm);
			log.debug("regId : {}, phoneNumber : {}", userForm.getRegId(), userForm.getPhoneNumber());

			//TODO Add Retry Template
			securityRememberMeService.setTokenToUser(new UserTokenGenerator(createdUser.getId(), createdUser.getUuid()));
			code.set(ApplicationCode.CREATE_NEWDATA);

		} else {
			code.set(ApplicationCode.UNEXPECTED);
		}

		return "";
	}
}