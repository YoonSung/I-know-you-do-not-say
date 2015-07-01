package gaongil.service;

import gaongil.domain.User;
import gaongil.dto.UserDTO;
import gaongil.repository.UserRepository;
import gaongil.support.exception.WrongParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public User create(UserDTO userDTO) {
		if (StringUtils.isEmpty(userDTO.getRegId()) || StringUtils.isEmpty(userDTO.getPhoneNumber()))
			throw new WrongParameterException();

		return userRepository.save(userDTO.getDomain());
	}


	public User findById(Long id) {
		if (id == null)
			throw new WrongParameterException();

		return userRepository.findOne(id);
	}
}
