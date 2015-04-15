package gaongil.controller;

import gaongil.domain.User;
import gaongil.repository.UserRepository;
import gaongil.support.exception.WrongParameterException;
import gaongil.support.web.resolver.argument.Response;
import gaongil.support.web.resolver.argument.ResponseApplicationCode;
import gaongil.support.web.status.ApplicationCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void register(User user, @Response ResponseApplicationCode code) {
		log.debug("regId : {}, phoneNumber : {}", user.getRegId(), user.getPhoneNumber());
		
		if (StringUtils.isEmpty(user.getRegId()) || StringUtils.isEmpty(user.getPhoneNumber()))
			throw new WrongParameterException();

		userRepository.save(user);
		code.set(ApplicationCode.CREATE_NEWDATA);
	}
}
