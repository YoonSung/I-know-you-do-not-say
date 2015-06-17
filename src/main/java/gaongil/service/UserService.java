package gaongil.service;

import gaongil.domain.User;
import gaongil.repository.UserRepository;
import gaongil.support.exception.WrongParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public User create(User user) {
		if (StringUtils.isEmpty(user.getRegId()) || StringUtils.isEmpty(user.getPhoneNumber()))
			throw new WrongParameterException();

		return userRepository.save(user);
	}


	public User findById(Long id) {
		if (id == null)
			throw new WrongParameterException();

		return userRepository.findOne(id);
	}
}
