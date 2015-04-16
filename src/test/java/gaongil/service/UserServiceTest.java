package gaongil.service;

import static org.mockito.Mockito.when;
import gaongil.config.DBConfig;
import gaongil.domain.User;
import gaongil.repository.UserRepository;
import gaongil.support.exception.WrongParameterException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class UserServiceTest {

	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserService userService;

	@Test
	public void 사용자생성() {
		
		//GIVEN
		String regId = "testRegId";
		String phoneNumber = "01012341234";
		
		User user = new User();
		user.setRegId(regId);
		user.setPhoneNumber(phoneNumber);
		
		//WHEN
		when(userService.create(user)).thenReturn(user);
		
		userService.create(user);
	}
	
	@Test(expected = WrongParameterException.class)
	public void 잘못된데이터로_사용자생성() {
		String regId = "";
		String phoneNumber = "01012341234";
		
		User user = new User();
		user.setRegId(regId);
		user.setPhoneNumber(phoneNumber);
		
		userService.create(user);
	}

}
