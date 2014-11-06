package gaongil.controller;

import java.util.Random;

import gaongil.dao.UserDao;
import gaongil.domain.User;

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
	UserDao userDao;

	// Validation Apply TODO
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody boolean register(User user) {

		if (user.getRegistrationId() == null)
			return false;

		// DELETE TODO
		user.setPhoneNumber("010" + new Random().nextInt(8));
		user.setNickname("User " + new Random().nextInt(100));

		try {
			userDao.create(user);
			return true;

		} catch (Exception e) {
			log.error("Register Request User : {}, Error : {}", user.toString(), e.getMessage());
		}

		return false;
	}
}
