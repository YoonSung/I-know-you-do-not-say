package gaongil.controller;

import gaongil.domain.User;
import gaongil.repository.UserRepository;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;

	// Validation Apply TODO
	@RequestMapping(value = "", method = RequestMethod.POST)
	public @ResponseBody String register(String regId, User user) {
		log.info("regId : {}", regId);
		log.info("User : {}", user.toString());
		
		if (user.getRegId() == null)
			return "fail";

		// DELETE TODO
		user.setPhoneNumber("010" + new Random().nextInt(8));
		user.setNickname("User " + new Random().nextInt(100));

		try {
			userRepository.save(user);
			return "success";

		} catch (Exception e) {
			log.error("Register Request User : {}, Error : {}", user.toString(), e.getMessage());
			return "error";
		}
	}
}
